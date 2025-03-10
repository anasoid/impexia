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

package org.anasoid.impexia.core.data.importing;

import java.io.IOException;
import org.anasoid.impexia.core.data.DataLine;
import org.anasoid.impexia.meta.DataFormat;

/** Interface to define Data reader for Impex. */
public interface DataReader {

  /** get next record. */
  DataLine nextRecord() throws IOException;

  /** return total records, -1 if not calculated. */
  int getRecordCount();

  /** skip record. */
  boolean skipRecord() throws IOException;

  DataFormat getDataFormat();
}
