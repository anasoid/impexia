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

import java.io.Closeable;
import java.io.IOException;

/** Interface to define Datareader for Impex. */
public interface IDataReader extends Closeable {

  /**
   * get Impex header as String array.
   *
   */
  String[] getHeader();

  /**
   * get next record.
   *
   */
  DataLine nextRecord() throws IOException;

  /**
   * return total records, -1 if not calculated.
   *
   */
  int getRecordCount();

  /** skip record. */
  boolean skipRecord() throws IOException;

  /** get current pass, 1 for first. */
  int getCurrentPass();

  /** Restart for new pass. */
  void restart() throws IOException;
}
