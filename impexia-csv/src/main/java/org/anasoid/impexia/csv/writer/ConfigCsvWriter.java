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

import com.opencsv.ICSVParser;
import com.opencsv.ICSVWriter;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** Config Csv Writer. */
@Getter
@SuperBuilder
@SuppressWarnings({"PMD.ImmutableField"})
public class ConfigCsvWriter {
  @Default private char separatorChar = ';';
  @Default private char quoteChar = ICSVParser.DEFAULT_QUOTE_CHARACTER;
  @Default private char escapeChar = ICSVParser.DEFAULT_ESCAPE_CHARACTER;
  private boolean applyQuotesToAll;
  @Default private String lineEnd = ICSVWriter.DEFAULT_LINE_END;
}
