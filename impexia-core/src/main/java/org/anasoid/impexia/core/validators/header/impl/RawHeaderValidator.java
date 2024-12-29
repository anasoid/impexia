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
import org.anasoid.impexia.core.validators.header.AbstractHeaderValidator;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.exceptions.header.ActionException;
import org.anasoid.impexia.meta.exceptions.header.AttributeModifierException;
import org.anasoid.impexia.meta.exceptions.header.ImpexHeaderException;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.anasoid.impexia.meta.modifier.Level;
import org.anasoid.impexia.meta.modifier.ModifierDescriptor;

/** Default header Validator. */
@SuppressWarnings("PMD.LawOfDemeter")
public class RawHeaderValidator extends AbstractHeaderValidator {

  @Override
  protected boolean validateAttribute(ImpexAttribute attribute, Mode mode) {
    return false;
  }

  @SuppressWarnings({"PMD.EmptyCatchBlock"})
  @Override
  protected boolean validateModifier(ImpexModifier impexModifier, Mode mode)
      throws ImpexHeaderException {

    ModifierDescriptor modifierDescriptor = null;
    try {
      modifierDescriptor = getModifier(impexModifier);
    } catch (java.lang.IllegalArgumentException e) {
      // nothing
    }

    if (modifierDescriptor != null) {

      validateModifierLevel(modifierDescriptor, impexModifier);
      validateModifierMode(modifierDescriptor, mode);
    }
    return true;
  }

  @Override
  protected boolean validateHeader(ImpexHeader header, Mode mode) throws ImpexHeaderException {
    validateAction(header);
    return true;
  }

  protected void validateModifierLevel(
      ModifierDescriptor modifierDescriptor, ImpexModifier impexModifier)
      throws ImpexHeaderException {
    Level level;
    if (impexModifier.getAttribute() != null) {
      level = Level.FIELD;
    } else if (impexModifier.getHeader() != null) {
      level = Level.TYPE;
    } else {
      throw new IllegalStateException("ImpexModifier has null level : " + impexModifier);
    }

    if (!modifierDescriptor.getLevels().contains(level)) {
      throw new AttributeModifierException(
          MessageFormat.format(
              "Field ({0}) is not acceptable by Level : {1}", modifierDescriptor.getCode(), level));
    }
  }

  protected void validateModifierMode(ModifierDescriptor modifierDescriptor, Mode mode)
      throws ImpexHeaderException {
    if (!modifierDescriptor.getModes().isEmpty() && !modifierDescriptor.getModes().contains(mode)) {
      throw new AttributeModifierException(
          MessageFormat.format(
              "Field ({0}) is not acceptable by Mode : {1}", modifierDescriptor.getCode(), mode));
    }
  }

  protected void validateAction(ImpexHeader header) throws ActionException {
    if (header.getAction() == null) {
      throw new ActionException();
    }
  }
}
