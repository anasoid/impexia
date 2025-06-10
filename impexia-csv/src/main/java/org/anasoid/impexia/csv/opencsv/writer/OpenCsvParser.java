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
 * Date :   29-Nov-2020
 */

package org.anasoid.impexia.csv.opencsv.writer;

import com.opencsv.CSVParser;
import com.opencsv.CustomOpenCsvParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.util.Locale;

/**
 * Copy of {@link CSVParser} to be overridableenable as constructor was protected from openCSV 5.3.
 *
 * @see CSVParser
 */
public abstract class OpenCsvParser extends CustomOpenCsvParser {

  protected OpenCsvParser(
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
}
