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
import org.anasoid.impexia.csv.writer.ConfigCsvWriter;

/**
 * Custom CSVParser to correct bug : 1- Writing null can be only writed as null or "", need to have.
 * empty separator Code from openCSV 5.3
 *
 * @see CSVParser
 */
public class OpenCsvWriterParser extends OpenCsvParser {

  private OpenCsvWriterParser(OpenCsvWriterConfig config) {
    super(
        config.getSeparator(),
        config.getQuoteChar(),
        config.getEscapeChar(),
        config.isStrictQuotes(),
        config.isIgnoreLeadingWhiteSpace(),
        config.isIgnoreQuotations(),
        config.getNullFieldIndicator(),
        config.getErrorLocale());
  }

  public OpenCsvWriterParser(ConfigCsvWriter config) {
    this(
        OpenCsvWriterConfig.builder()
            .quoteChar(config.getQuoteChar())
            .separator(config.getSeparatorChar())
            .escapeChar(config.getEscapeChar())
            .build());
  }

  @Override
  protected String convertToCsvValue(String value, boolean applyQuotestoAll) {
    if (value == null) {
      return "";
    } else {
      return super.convertToCsvValue(value, applyQuotestoAll);
    }
  }
}
