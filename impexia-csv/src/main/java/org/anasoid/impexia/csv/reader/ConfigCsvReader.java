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
 * Date :   23-Nov-2020
 */

package org.anasoid.impexia.csv.reader;

import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.impexia.csv.CsvReaderNullFieldIndicator;

/**
 * Config used to read CSV file new config using builder.
 *
 * @see ConfigCsvReaderBuilder
 */
@SuperBuilder
@Getter
@SuppressWarnings({"PMD.ImmutableField"})
public class ConfigCsvReader {

  @Default private String charsetName = "UTF-8";
  @Default private char commentChar = '#';
  @Default private char separatorChar = ';';
  @Default private char quoteChar = ICSVParser.DEFAULT_QUOTE_CHARACTER;
  @Default private char escapeChar = ICSVParser.DEFAULT_ESCAPE_CHARACTER;
  @Default private boolean strictQuotes = ICSVParser.DEFAULT_STRICT_QUOTES;
  @Default private boolean ignoreLeadingWhiteSpace = ICSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE;

  @Default private boolean containHeader = true;

  @Default
  private CsvReaderNullFieldIndicator nullFieldIndicator =
      CsvReaderNullFieldIndicator.EMPTY_SEPARATORS;

  @Default private int skipLines = CSVReader.DEFAULT_SKIP_LINES;

  @Default private int multilineLimit = CSVReader.DEFAULT_MULTILINE_LIMIT;

  @Default private boolean verifyReader = CSVReader.DEFAULT_VERIFY_READER;
  private boolean needTotal;
  private boolean keepCR;
}
