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
 * Date :   28-Nov-2020
 */

package org.anasoid.impexia.csv.opencsv.writer;

import com.opencsv.ICSVParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.util.Locale;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@SuppressWarnings({"PMD.ImmutableField"})
public class OpenCsvWriterConfig {

  @Default private char separator = ICSVParser.DEFAULT_SEPARATOR;
  @Default private char quoteChar = ICSVParser.DEFAULT_QUOTE_CHARACTER;
  @Default private char escapeChar = ICSVParser.DEFAULT_ESCAPE_CHARACTER;
  @Default private boolean strictQuotes = ICSVParser.DEFAULT_STRICT_QUOTES;
  @Default private boolean ignoreLeadingWhiteSpace = ICSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE;
  @Default private boolean ignoreQuotations = ICSVParser.DEFAULT_IGNORE_QUOTATIONS;

  @Default
  private CSVReaderNullFieldIndicator nullFieldIndicator = CSVReaderNullFieldIndicator.NEITHER;

  @Default private Locale errorLocale = Locale.getDefault();

  public OpenCsvWriterParser buildCsvWriter() {

    return new OpenCsvWriterParser(
        separator,
        quoteChar,
        escapeChar,
        strictQuotes,
        ignoreLeadingWhiteSpace,
        ignoreQuotations,
        nullFieldIndicator,
        errorLocale);
  }
}
