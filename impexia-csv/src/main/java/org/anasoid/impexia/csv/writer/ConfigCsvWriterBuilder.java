package org.anasoid.impexia.csv.writer;

import com.opencsv.ICSVParser;
import com.opencsv.ICSVWriter;

/**
 * Config Csv Writer Builder.
 */
public class ConfigCsvWriterBuilder {

  private char separatorChar = ';';
  private char quoteChar = ICSVParser.DEFAULT_QUOTE_CHARACTER;
  private char escapeChar = ICSVParser.DEFAULT_ESCAPE_CHARACTER;
  private boolean applyQuotesToAll;
  private String lineEnd = ICSVWriter.DEFAULT_LINE_END;

  /**
   * The delimiter to use for separating entries. default (;).
   */
  public ConfigCsvWriterBuilder setSeparatorChar(char separatorChar) {
    this.separatorChar = separatorChar;
    return this;
  }

  /**
   * The character to use for quoted elements. default (")
   */
  public ConfigCsvWriterBuilder setQuoteChar(char quoteChar) {
    this.quoteChar = quoteChar;
    return this;
  }

  /**
   * The character to use for escaping a separator or quote. default (\\)
   */
  public ConfigCsvWriterBuilder setEscapeChar(char escapeChar) {
    this.escapeChar = escapeChar;
    return this;
  }


  /**
   * true if all values are to be quoted. false applies quotes only to values which contain the
   * separator, escape, quote or new line characters. default (false)
   */
  public ConfigCsvWriterBuilder setApplyQuotesToAll(boolean applyQuotesToAll) {
    this.applyQuotesToAll = applyQuotesToAll;
    return this;
  }

  /**
   * The line feed terminator to use . default (\n).
   */
  public ConfigCsvWriterBuilder setLineEnd(String lineEnd) {
    this.lineEnd = lineEnd;
    return this;
  }

  public ConfigCsvWriter build() {
    return new ConfigCsvWriter(
        separatorChar, quoteChar, escapeChar, applyQuotesToAll, lineEnd);
  }
}
