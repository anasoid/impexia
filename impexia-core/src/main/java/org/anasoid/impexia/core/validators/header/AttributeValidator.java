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
 * Date :   14-Nov-2020
 */

package org.anasoid.impexia.core.validators.header;

import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.exceptions.header.ImpexHeaderException;
import org.anasoid.impexia.meta.header.ImpexAttribute;

/** Header validator interface. */
public interface AttributeValidator {

  /**
   * Validate attribute.
   *
   * @param attribute attribute
   * @param mode mode
   * @return true, function do a check or not.
   * @throws ImpexHeaderException throw exception if header not valid.
   */
  boolean validate(ImpexAttribute attribute, Mode mode) throws ImpexHeaderException;
}
