package org.anasoid.impexia.core.data;

/**
 * Data Line Builder.
 */
public class DataLineBuilder {

  private String[] record;
  private long lineNumber;
  private long recordNumber = -1;
  private long recordCount;

  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  public DataLineBuilder setRecord(String... record) {
    this.record = record;
    return this;
  }

  public DataLineBuilder setLineNumber(long lineNumber) {
    this.lineNumber = lineNumber;
    return this;
  }

  public DataLineBuilder setRecordNumber(long recordNumber) {
    this.recordNumber = recordNumber;
    return this;
  }

  public DataLineBuilder setRecordCount(long recordCount) {
    this.recordCount = recordCount;
    return this;
  }

  public DataLine build() {
    return new DataLine(record, lineNumber, recordNumber, recordCount);
  }
}
