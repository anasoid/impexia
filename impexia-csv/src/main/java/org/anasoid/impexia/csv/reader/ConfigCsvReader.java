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

import org.anasoid.impexia.csv.CsvReaderNullFieldIndicator;

/**
 * Config used to read CSV file new config using builder.
 *
 * @see ConfigCsvReaderBuilder
 */
public class ConfigCsvReader {

  private final String charsetName;

  private final char commentChar;

  private final char separatorChar;

  private final char quoteChar;
  private final char escapeChar;
  private final boolean strictQuotes;
  private final boolean ignoreLeadingWhiteSpace;
  private final boolean needTotal;
  private final boolean containHeader;
  private final CsvReaderNullFieldIndicator nullFieldIndicator;
  private final int skipLines;
  private final int multilineLimit;
  private final boolean keepCR;
  private final boolean verifyReader;

  /**
   * Default constructor.
   *
   * @see ConfigCsvReaderBuilder
   */
  ConfigCsvReader(
      String charsetName,
      char commentChar,
      char separatorChar,
      char quoteChar,
      char escapeChar,
      boolean strictQuotes,
      boolean ignoreLeadingWhiteSpace,
      boolean needTotal,
      boolean containHeader,
      CsvReaderNullFieldIndicator nullFieldIndicator,
      int skipLines,
      int multilineLimit,
      boolean keepCR,
      boolean verifyReader) {
    this.charsetName = charsetName;
    this.commentChar = commentChar;
    this.separatorChar = separatorChar;
    this.quoteChar = quoteChar;
    this.escapeChar = escapeChar;
    this.strictQuotes = strictQuotes;
    this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
    this.needTotal = needTotal;
    this.containHeader = containHeader;
    this.nullFieldIndicator = nullFieldIndicator;
    this.skipLines = skipLines;
    this.multilineLimit = multilineLimit;
    this.keepCR = keepCR;
    this.verifyReader = verifyReader;
  }

  /** char used to start comment line. */
  public char getCommentChar() {
    return commentChar;
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

  /** If true, characters outside the quotes are ignored default (false). */
  public boolean isStrictQuotes() {
    return strictQuotes;
  }

  /** If true, white space in front of a quote in a field is ignored default (false). */
  public boolean isIgnoreLeadingWhiteSpace() {
    return ignoreLeadingWhiteSpace;
  }

  /**
   * Which field content will be returned as null: EMPTY_SEPARATORS, * EMPTY_QUOTES, BOTH, sNEITHER
   * (default).
   */
  public CsvReaderNullFieldIndicator getNullFieldIndicator() {
    return nullFieldIndicator;
  }

  /** The number of lines to skip before reading. */
  public int getSkipLines() {
    return skipLines;
  }

  /**
   * Allow the user to define the limit to the number of lines in a multiline record. Less than one
   * means no limit.
   */
  public int getMultilineLimit() {
    return multilineLimit;
  }

  /** True to keep carriage returns in data read, false otherwise. */
  public boolean isKeepCR() {
    return keepCR;
  }

  /** verifyReader True to verify reader before each read, false otherwise. */
  public boolean isVerifyReader() {
    return verifyReader;
  }

  /** Encodage used by file : default UTF-8. */
  public String getCharsetName() {
    return charsetName;
  }

  /** True if Total is needed, only to calculate progress in log. */
  public boolean isNeedTotal() {
    return needTotal;
  }

  /** if file contain header or not. */
  public boolean isContainHeader() {
    return containHeader;
  }
}
