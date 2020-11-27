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
 * Date :   22-Nov-2020
 */

package org.anasoid.impexia.core.data;

import java.io.Serializable;

/** DTO for line. */
public class DataLine implements Serializable {

  static final long serialVersionUID = -338751699312454948L;

  private final String[] record;
  private final long lineNumber;
  private final long recordNumber;
  private final long recordCount;

  /**
   * default constructor.
   *
   * @param record Data record.
   * @param lineNumber line number in file
   * @param recordNumber recordNumber
   * @param recordCount Total of records, -1 if not calculated.
   */
  @SuppressWarnings("PMD.ArrayIsStoredDirectly")
  DataLine(String[] record, long lineNumber, long recordNumber, long recordCount) {
    this.record = record;
    this.lineNumber = lineNumber;
    this.recordNumber = recordNumber;
    this.recordCount = recordCount;
  }

  /**
   * get row Data.
   *
   * @return row as array.
   */
  @SuppressWarnings("PMD.MethodReturnsInternalArray")
  public String[] getRecord() {
    return record;
  }

  /** get line number. */
  public long getLineNumber() {
    return lineNumber;
  }

  /** record number. */
  public long getRecordNumber() {
    return recordNumber;
  }

  /** is first line. */
  public long getRecordCount() {
    return recordCount;
  }
}
