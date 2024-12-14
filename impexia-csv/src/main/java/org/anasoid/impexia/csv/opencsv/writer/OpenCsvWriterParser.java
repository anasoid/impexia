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

import com.opencsv.CSVParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/**
 * Custom CSVParser to correct bug : 1- Writing null can be only writed as null or "", need to have.
 * empty separator Code from openCSV 5.3
 *
 * @see CSVParser
 */
public class OpenCsvWriterParser extends OpenCsvParser {

  /**
   * Constructor.
   */
  public OpenCsvWriterParser(
      char separator,
      char quotechar,
      char escape,
      boolean strictQuotes,
      boolean ignoreLeadingWhiteSpace,
      boolean ignoreQuotations,
      CSVReaderNullFieldIndicator nullFieldIndicator,
      Locale errorLocale) {
    super(
        separator,
        quotechar,
        escape,
        strictQuotes,
        ignoreLeadingWhiteSpace,
        ignoreQuotations,
        nullFieldIndicator,
        errorLocale);
  }

  @Override
  protected String convertToCsvValue(String value, boolean applyQuotestoAll) {
    String testValue = value;
    StringBuilder builder =
        new StringBuilder(testValue == null ? MAX_SIZE_FOR_EMPTY_FIELD : (testValue.length() * 2));
    boolean containsQuoteChar = StringUtils.contains(testValue, getQuotechar());
    boolean containsEscapeChar = StringUtils.contains(testValue, getEscape());
    boolean containsSeparatorChar = StringUtils.contains(testValue, getSeparator());
    boolean surroundWithQuotes =
        applyQuotestoAll || isSurroundWithQuotes(value, containsSeparatorChar);

    String convertedString = // NOSONAR
        !containsQuoteChar
            ? testValue
            : testValue.replaceAll(// NOSONAR
                Character.toString(getQuotechar()),
                Character.toString(getQuotechar()) + getQuotechar());
    convertedString =
        !containsEscapeChar
            ? convertedString
            : convertedString.replace(// NOSONAR
                Character.toString(getEscape()),
                Character.toString(getEscape()) + getEscape());
    if (value != null) {
      if (surroundWithQuotes) {
        builder.append(getQuotechar());
      }

      builder.append(convertedString);

      if (surroundWithQuotes) {
        builder.append(getQuotechar());
      }
    }
    return builder.toString();
  }
}
