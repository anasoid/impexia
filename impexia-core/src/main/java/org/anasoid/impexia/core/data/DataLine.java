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
import lombok.Getter;

/** DTO for line. */
public class DataLine implements Serializable {

  static final long serialVersionUID = -338751699312454948L;

  @Getter private final String[] record;

  /** -- GETTER -- get line number. */
  @Getter private final long lineNumber;

  /** -- GETTER -- record number. */
  @Getter private final long recordNumber;

  /** -- GETTER -- is first line. */
  @Getter private final long recordCount;

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
}
