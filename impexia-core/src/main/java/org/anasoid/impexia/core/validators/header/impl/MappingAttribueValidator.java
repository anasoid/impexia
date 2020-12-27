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

import java.text.MessageFormat;
import org.anasoid.impexia.core.validators.header.AttributeValidator;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.exceptions.header.AttributeModifierException;
import org.anasoid.impexia.meta.exceptions.header.ImpexHeaderException;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.modifier.Level;

/** Default header Validator. */
public class MappingAttribueValidator implements AttributeValidator {

  @Override
  public boolean validate(ImpexHeader header, ImpexAttribute attribute, Mode mode, Level level)
      throws ImpexHeaderException {
    boolean needMapping =
        attribute.getModifiers().stream()
            .anyMatch(m -> m.getModifier() != null && m.getModifier().isNeedMapping());
    if (needMapping && attribute.getMappings().isEmpty()) {
      throw new AttributeModifierException(
          MessageFormat.format(
              "Field ({0}) with modifier should have mapping ", attribute.getField()));
    }
    return false;
  }
}
