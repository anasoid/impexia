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

public class ConfigCsvWriter {

  private final char separatorChar;

  private final char quoteChar;
  private final char escapeChar;
  private final boolean applyQuotesToAll;

  private final String lineEnd;

  /**
   * Default constructor.
   *
   * @see ConfigCsvWriterBuilder
   */
  ConfigCsvWriter(
      char separatorChar,
      char quoteChar,
      char escapeChar,
      boolean applyQuotesToAll,
      String lineEnd) {
    this.separatorChar = separatorChar;
    this.quoteChar = quoteChar;
    this.escapeChar = escapeChar;
    this.applyQuotesToAll = applyQuotesToAll;
    this.lineEnd = lineEnd;
  }

  /** The delimiter to use for separating entries. */
  public char getSeparatorChar() {
    return separatorChar;
  }

  /** The character to use for quoted elements. default (") */
  public char getQuoteChar() {
    return quoteChar;
  }

  /** The character to use for escaping a separator or quote default (\\). */
  public char getEscapeChar() {
    return escapeChar;
  }

  /** lineEnd String to append at end of data (either "\n" or "\r\n"). */
  public String getLineEnd() {
    return lineEnd;
  }

  public boolean isApplyQuotesToAll() {
    return applyQuotesToAll;
  }
}
