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
 * Date :   27-Nov-2020
 */

package org.anasoid.impexia.csv.writer;

import com.opencsv.CSVParserWriter;
import com.opencsv.ICSVWriter;
import java.io.IOException;
import java.io.Writer;
import org.anasoid.impexia.core.data.IDataWriter;
import org.anasoid.impexia.csv.opencsv.writer.OpenCsvWriterParserBuilder;

public class CsvDataWriter implements IDataWriter {

  private final ICSVWriter csvWriter;
  private final ConfigCsvWriter configCsv;
  private final Writer writer;
  private boolean canCallHeader = true;

  /**
   * default constructor.
   *
   * @param writer writer output
   * @param configCsv config csv
   * @throws IOException if there is any problem to read file
   */
  public CsvDataWriter(Writer writer, ConfigCsvWriter configCsv) throws IOException {
    this.configCsv = configCsv;
    this.writer = writer;
    csvWriter = getCsvWriter();
  }

  private ICSVWriter getCsvWriter() {

    return new CSVParserWriter(
        writer,
        new OpenCsvWriterParserBuilder()
            .withSeparator(configCsv.getSeparatorChar())
            .withEscapeChar(configCsv.getEscapeChar())
            .withQuoteChar(configCsv.getQuoteChar())
            .build(),
        configCsv.getLineEnd());
  }

  @Override
  public void writeHeader(String... header) throws IOException {
    if (!canCallHeader) {
      throw new IllegalStateException(
          "Can't write heade as already caled or a first line is inserted");
    }
    canCallHeader = false;
    writeNext(header, false);
  }

  @Override
  public void writeLine(String... nextLine) throws IOException {
    canCallHeader = false;
    writeNext(nextLine, configCsv.isApplyQuotesToAll());
  }

  void writeNext(String[] nextLine, boolean applyQuotesToAll) throws IOException {
    csvWriter.writeNext(nextLine, applyQuotesToAll);
    if (csvWriter.checkError()) {
      throw csvWriter.getException();
    }
  }

  @Override
  public void close() throws IOException {
    csvWriter.close();
  }
}
