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

import java.text.MessageFormat;
import java.util.List;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.exceptions.header.ActionException;
import org.anasoid.impexia.meta.exceptions.header.AttributeModifierException;
import org.anasoid.impexia.meta.exceptions.header.ImpexHeaderException;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.anasoid.impexia.meta.modifier.Level;
import org.anasoid.impexia.meta.modifier.Modifier;

/** Default header Validator. */
public class RawHeaderValidator extends AbstractHeaderValidator {

  @Override
  protected boolean validateAttribute(
      ImpexHeader header, ImpexAttribute attribute, Mode mode, Level level)
      throws ImpexHeaderException {
    return false;
  }

  @SuppressWarnings("PMD")
  @Override
  protected boolean validateModifier(
      ImpexHeader impexHeader,
      ImpexAttribute impexAttribute,
      ImpexModifier impexModifier,
      Mode mode,
      Level level)
      throws ImpexHeaderException {

    Modifier modifier = null;
    try {
      modifier = getModifier(impexModifier);
    } catch (java.lang.IllegalArgumentException e) {
      // nothing
    }
    validateCustomModifier(impexHeader, impexAttribute, impexModifier, modifier, level);
    if (modifier != null) {
      validateModifierBoolean(impexModifier, modifier);
      validateModifierLevel(modifier, level);
      validateModifierMode(modifier, mode);
    }
    return true;
  }

  @Override
  protected boolean validateHeader(ImpexHeader header, Mode mode) throws ImpexHeaderException {
    validateAction(header);
    return true;
  }

  @SuppressWarnings({"PMD"})
  protected void validateCustomModifier(
      ImpexHeader header,
      ImpexAttribute attribute,
      ImpexModifier impexModifier,
      Modifier modifier,
      Level level)
      throws ImpexHeaderException {
    if (modifier == null) {
      List<ImpexModifier> modifiers = null;
      if (Level.FIELD.equals(level)) {
        modifiers = attribute.getModifiers();
      } else if (Level.TYPE.equals(level)) {
        modifiers = header.getModifiers();
      }
      boolean acceptCustom = false;
      for (ImpexModifier otherModifier : modifiers) {
        try {
          Modifier modifierEnum = getModifier(otherModifier);
          if (modifierEnum.isAcceptCustomAttibute()) {
            acceptCustom = true;
            break;
          }
        } catch (java.lang.IllegalArgumentException e) {
          // nothing
        }
      }

      if (!acceptCustom) {
        throw new AttributeModifierException(
            MessageFormat.format(
                "Field ({0}) is unknown,"
                    + " custom attribute accepted only when Class modifier is used",
                impexModifier.getKey()));
      }
    }
  }

  protected void validateModifierBoolean(ImpexModifier impexModifier, Modifier modifierEnum)
      throws ImpexHeaderException {

    if (modifierEnum.isBoolean()
        && !impexModifier.getValue().equalsIgnoreCase("true")
        && !impexModifier.getValue().equalsIgnoreCase("false")) {
      throw new AttributeModifierException(
          MessageFormat.format("Field ({0}) should be boolean ", impexModifier.getKey()));
    }
  }

  protected void validateModifierLevel(Modifier modifier, Level level) throws ImpexHeaderException {
    if (!modifier.getLevels().contains(level)) {
      throw new AttributeModifierException(
          MessageFormat.format(
              "Field ({0}) is not acceptable by Level : {1}", modifier.getCode(), level));
    }
  }

  protected void validateModifierMode(Modifier modifier, Mode mode) throws ImpexHeaderException {
    if (!modifier.getModes().contains(mode)) {
      throw new AttributeModifierException(
          MessageFormat.format(
              "Field ({0}) is not acceptable by Mode : {1}", modifier.getCode(), mode));
    }
  }

  protected void validateAction(ImpexHeader header) throws ActionException {
    if (header.getAction() == null) {
      throw new ActionException();
    }
  }
}
