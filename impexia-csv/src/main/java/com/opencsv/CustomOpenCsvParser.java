/*
 * Copyright 2020-2024 the original author or authors.
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
 * Date :   21-Dec-2024
 */

package com.opencsv;

import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.util.Locale;

@SuppressWarnings("PMD")
public abstract class CustomOpenCsvParser extends CSVParser {

  protected CustomOpenCsvParser(
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
