/*
 * Copyright 2020-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author : anas
 * Date :   22-Nov-2020
 */

package org.anasoid.impexia.csv.reader;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import org.anasoid.impexia.core.data.DataLine;
import org.anasoid.impexia.core.data.DataLineBuilder;
import org.anasoid.impexia.core.data.IDataReader;
import org.anasoid.impexia.csv.InvalidCsvFormatException;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvDataReader implements IDataReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(CsvDataReader.class);

  private File file;
  private CSVReader csvReader;
  private final ConfigCsvReader configCsv;
  private String[] header;
  private boolean hasNext = true;

  private int recordNumber;
  private int recordCount = -1;
  private int currentPass = 1;
  private long lineNumber;
  public static final char BLANK_CHARACTER = ' ';

  /**
   * default constructor.
   *
   * @param file csv file
   * @param configCsv config csv
   * @throws IOException if there is any problem to read file
   */
  public CsvDataReader(File file, ConfigCsvReader configCsv) throws IOException {
    this.configCsv = configCsv;
    if (configCsv.isNeedTotal()) {
      calculateTotal(file);
    }
    this.file = file;
    csvReader = getCsvReader();
    try {
      initializeHeader();
    } catch (InvalidCsvFormatException e) {
      close();
      throw e;
    }
  }

  private final void calculateTotal(File file) throws IOException {
    this.file = file;
    csvReader = getCsvReader();
    try {
      initializeHeader();
      String[] record = readRecord(false);
      while (record != null) {
        record = readRecord(false);
      }
      recordCount = recordNumber;
      LOGGER.debug("Calculate total record ({}) of file : {}", recordCount, file);
      initialize();
    } catch (InvalidCsvFormatException e) {
      close();
    }
  }

  @SuppressWarnings({"PMD.CloseResource", "PMD.AvoidFileStream"})
  private CSVReader getCsvReader() throws IOException {
    Reader reader = new InputStreamReader(new FileInputStream(file), configCsv.getCharsetName());
    CSVReader internalCsvReader = null;
    try {
      internalCsvReader =
          new CSVReaderBuilder(reader)
              .withSkipLines(configCsv.getSkipLines())
              .withCSVParser(
                  new CSVParserBuilder()
                      .withSeparator(configCsv.getSeparatorChar())
                      .withEscapeChar(configCsv.getEscapeChar())
                      .withQuoteChar(configCsv.getQuoteChar())
                      .withStrictQuotes(configCsv.isStrictQuotes())
                      .withIgnoreLeadingWhiteSpace(configCsv.isIgnoreLeadingWhiteSpace())
                      .withFieldAsNull(
                          CSVReaderNullFieldIndicator.valueOf(
                              configCsv.getNullFieldIndicator().toString()))
                      .build())
              .withKeepCarriageReturn(configCsv.isKeepCR())
              .withVerifyReader(configCsv.isVerifyReader())
              .withMultilineLimit(configCsv.getMultilineLimit())
              .build();
      LOGGER.debug("Open CSV Reader of file : {}", file);
    } finally {
      if (internalCsvReader == null) {
        reader.close();
      }
    }
    return internalCsvReader;
  }

  private void initializeHeader() throws IOException {

    if (configCsv.isContainHeader()) {
      if (header != null) {
        this.readRecord(true);
      } else {
        header = this.readRecord(true);

        if (header == null) {
          throw new InvalidCsvFormatException(
              MessageFormat.format("Header not found one file : {0}", file.getAbsolutePath()));
        }
        LOGGER.debug("Read header for file : {}", file);
      }
      boolean found = false;
      String action = header[0].toUpperCase();
      for (ImpexAction impexAction : ImpexAction.values()) {
        if (action.startsWith(impexAction.toString())) {
          found = true;
          break;
        }
      }
      if (!found) {
        throw new InvalidCsvFormatException(
            MessageFormat.format(
                "Header not valid (({0})) one file : {1}", header, file.getAbsolutePath()));
      }
    }
  }

  private String[] readLine() throws IOException {
    return csvReader.readNextSilently();
  }

  private String[] readRecord(boolean isHeader) throws IOException {
    String[] line;
    while (hasNext) {
      lineNumber = csvReader.getLinesRead() + 1;
      line = readLine();
      if (line == null) {
        hasNext = false;
        return null;
      }
      if (!isCommetOrEmpty(line)) {
        if (!isHeader) {
          recordNumber++;
        }
        return line;
      }
    }
    return null;
  }

  private boolean isCommetOrEmpty(String... line) {
    if (line.length == 0 || line[0] == null || line[0].length() == 0) {
      return true;
    } else {
      boolean iscomment = false;
      for (int i = 0; i < line[0].length(); i++) {
        if (line[0].charAt(i) == configCsv.getCommentChar()) {
          iscomment = true;
          break;
        } else if (line[0].charAt(i) != BLANK_CHARACTER) {
          break;
        }
      }
      return iscomment;
    }
  }

  @Override
  @SuppressWarnings("PMD.MethodReturnsInternalArray")
  public String[] getHeader() {
    return header;
  }

  @Override
  public DataLine nextRecord() throws IOException {

    String[] record = readRecord(false);
    if (record == null) {
      return null;
    }
    return new DataLineBuilder()
        .setRecord(record)
        .setRecordCount(getRecordCount())
        .setRecordNumber(recordNumber)
        .setLineNumber(lineNumber)
        .build();
  }

  @Override
  public int getRecordCount() {
    return recordCount;
  }

  @Override
  public boolean skipRecord() throws IOException {

    String[] line = readRecord(false);
    return (line != null);
  }

  @Override
  public int getCurrentPass() {
    return currentPass;
  }

  @Override
  public void restart() throws IOException {
    LOGGER.debug("Restart for new pass : {}", file);
    initialize();
    currentPass++;
    close();
    csvReader = getCsvReader();
    initializeHeader();
  }

  private void initialize() {
    hasNext = true;
    lineNumber = 0;
    recordNumber = 0;
  }

  @Override
  public final void close() throws IOException {
    if (csvReader != null) {
      csvReader.close();
    }
  }
}
