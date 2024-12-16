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
import lombok.experimental.SuperBuilder;

/** DTO for line. */
@SuperBuilder
@Getter
public class DataLine implements Serializable {

  static final long serialVersionUID = -338751699312454948L;

  /** get line number. */
  private final long lineNumber;

  /** record number. */
  private final long recordNumber;

  /** is first line. */
  private final long recordCount;

  private final String[] record;
}
