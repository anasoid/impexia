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
 * Date :   03-Nov-2020
 */

package org.anasoid.impexia.core.validators.header.impl;

import org.anasoid.impexia.core.validators.header.HeaderValidator;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.exceptions.header.AttributeModifierException;
import org.anasoid.impexia.meta.exceptions.header.ImpexHeaderException;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.modifier.ModifierEnumGlobal;

/** Default header Validator. */
public class UniqueModifierValidator implements HeaderValidator {

  @Override
  public void validate(ImpexHeader header, Mode mode) throws ImpexHeaderException {
    if (Mode.IMPORT.equals(mode)) {
      boolean hasUnique =
          header.getAttributes().stream()
              .anyMatch(
                  att ->
                      att.getModifiers().stream()
                          .anyMatch(
                              m ->
                                  ModifierEnumGlobal.UNIQUE
                                      .toString()
                                      .equalsIgnoreCase(m.getKey())));
      if (!hasUnique) {
        throw new AttributeModifierException("Import should have at least one unique Field ");
      }
    }
  }
}
