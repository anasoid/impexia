package org.anasoid.impexia.csv.reader;

import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;
import org.anasoid.impexia.csv.reader.ConfigCsvReader.CsvReaderNullFieldIndicator;

public class ConfigCsvReaderBuilder {

  private String charsetName = "UTF-8";
  private char commentChar = '#';
  private char separatorChar = ';';
  private char quoteChar = ICSVParser.DEFAULT_QUOTE_CHARACTER;
  private char escapeChar = ICSVParser.DEFAULT_ESCAPE_CHARACTER;
  private boolean strictQuotes = ICSVParser.DEFAULT_STRICT_QUOTES;
  private boolean ignoreLeadingWhiteSpace = ICSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE;
  private boolean needTotal;
  private boolean containHeader = true;
  private CsvReaderNullFieldIndicator nullFieldIndicator =
      CsvReaderNullFieldIndicator.EMPTY_SEPARATORS;
  private int skipLines = CSVReader.DEFAULT_SKIP_LINES;

  private int multilineLimit = CSVReader.DEFAULT_MULTILINE_LIMIT;
  private boolean keepCR;

  private boolean verifyReader = CSVReader.DEFAULT_VERIFY_READER;

  /** Charset used by file : default (UTF-8). */
  public ConfigCsvReaderBuilder setCharsetName(String charsetName) {
    this.charsetName = charsetName;
    return this;
  }

  /** char used to start comment line : default (#). */
  public ConfigCsvReaderBuilder setCommentChar(char commentChar) {
    this.commentChar = commentChar;
    return this;
  }

  /** The delimiter to use for separating entries. default (;). */
  public ConfigCsvReaderBuilder setSeparatorChar(char separatorChar) {
    this.separatorChar = separatorChar;
    return this;
  }

  /** The character to use for quoted elements. default (") */
  public ConfigCsvReaderBuilder setQuoteChar(char quoteChar) {
    this.quoteChar = quoteChar;
    return this;
  }

  /** The character to use for escaping a separator or quote. default (\\) */
  public ConfigCsvReaderBuilder setEscapeChar(char escapeChar) {
    this.escapeChar = escapeChar;
    return this;
  }

  /** If true, characters outside the quotes are ignored. default (false). */
  public ConfigCsvReaderBuilder setStrictQuotes(boolean strictQuotes) {
    this.strictQuotes = strictQuotes;
    return this;
  }

  /** If true, white space in front of a quote in a field is ignored. default (false). */
  public ConfigCsvReaderBuilder setIgnoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
    this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
    return this;
  }

  /** True if Total is needed, only to calculate progress in log. default (false). */
  public ConfigCsvReaderBuilder setNeedTotal(boolean needTotal) {
    this.needTotal = needTotal;
    return this;
  }

  /** if file contain header or not. default (true). */
  public ConfigCsvReaderBuilder setContainHeader(boolean containHeader) {
    this.containHeader = containHeader;
    return this;
  }

  /**
   * Determines the handling of null fields.
   *
   * @see CsvReaderNullFieldIndicator
   */
  public ConfigCsvReaderBuilder setNullFieldIndicator(
      CsvReaderNullFieldIndicator nullFieldIndicator) {
    this.nullFieldIndicator = nullFieldIndicator;
    return this;
  }

  /** The number of lines to skip before reading. */
  public ConfigCsvReaderBuilder setSkipLines(int skipLines) {
    this.skipLines = skipLines;
    return this;
  }

  /**
   * set multilineLimit.
   *
   * @param multilineLimit Allow the user to define the limit to the number of lines in a multiline.
   *     Less than one means no limit. Default 0.
   * @return builder.
   */
  public ConfigCsvReaderBuilder setMultilineLimit(int multilineLimit) {
    this.multilineLimit = multilineLimit;
    return this;
  }

  /** True to keep carriage returns in data read, false otherwise. */
  public ConfigCsvReaderBuilder setKeepCR(boolean keepCR) {
    this.keepCR = keepCR;
    return this;
  }

  /**
   * Checks to see if the CSVReader should verify the reader state before reads or not.
   *
   * <p>This should be set to false if you are using some form of asynchronous reader (like readers
   * created by the java.nio.* classes).
   *
   * <p>The default value is true.
   *
   * @param verifyReader True if CSVReader should verify reader before each read, false otherwise.
   * @return {@code this}
   */
  public ConfigCsvReaderBuilder setVerifyReader(boolean verifyReader) {
    this.verifyReader = verifyReader;
    return this;
  }

  /**
   * build Object ConfigCsvReader.
   *
   * @return new ConfigCsvReader
   */
  public ConfigCsvReader build() {
    return new ConfigCsvReader(
        charsetName,
        commentChar,
        separatorChar,
        quoteChar,
        escapeChar,
        strictQuotes,
        ignoreLeadingWhiteSpace,
        needTotal,
        containHeader,
        nullFieldIndicator,
        skipLines,
        multilineLimit,
        keepCR,
        verifyReader);
  }
}
