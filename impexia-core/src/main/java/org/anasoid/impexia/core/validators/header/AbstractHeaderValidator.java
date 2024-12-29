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

package org.anasoid.impexia.core.validators.header;

import java.util.List;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.exceptions.header.ImpexHeaderException;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.anasoid.impexia.meta.modifier.ModifierDescriptor;
import org.anasoid.impexia.meta.modifier.ModifierDescriptorManager;

/** Validate Impex header. */
public abstract class AbstractHeaderValidator implements HeaderValidator {

  @Override
  public void validate(ImpexHeader header, Mode mode) throws ImpexHeaderException {

    validateHeader(header, mode);
    List<ImpexModifier> headerModifiers = header.getModifiers();
    for (ImpexModifier modifier : headerModifiers) {
      validateModifier(modifier, mode);
    }

    List<ImpexAttribute> attributes = header.getAttributes();
    for (ImpexAttribute attribute : attributes) {
      validateAttribute(attribute, mode);
      for (ImpexModifier modifier : attribute.getModifiers()) {
        validateModifier(modifier, mode);
      }
    }
  }

  /**
   * Validate attribute.
   *
   * @param attribute attribute
   * @param mode mode
   * @return true, function do a check or not.
   * @throws ImpexHeaderException throw exception if header not valid.
   */
  protected abstract boolean validateAttribute(ImpexAttribute attribute, Mode mode)
      throws ImpexHeaderException;

  /**
   * Validate Modifier.
   *
   * @param modifier modifier
   * @param mode mode
   * @return true, function do a check or not.
   * @throws ImpexHeaderException throw exception if header not valid.
   */
  protected abstract boolean validateModifier(ImpexModifier modifier, Mode mode)
      throws ImpexHeaderException;

  /**
   * Validate Header.
   *
   * @param header header
   * @param mode mode
   * @return true, function do a check or not.
   * @throws ImpexHeaderException throw exception if header not valid.
   */
  protected abstract boolean validateHeader(ImpexHeader header, Mode mode)
      throws ImpexHeaderException;

  protected ModifierDescriptor getModifier(ImpexModifier modifier) {
    return ModifierDescriptorManager.getInstance().getValueByCode(modifier.getKey());
  }
}
