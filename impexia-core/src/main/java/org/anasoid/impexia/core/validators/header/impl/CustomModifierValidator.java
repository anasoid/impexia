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
import java.util.List;
import org.anasoid.impexia.core.validators.header.ModifierValidator;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.exceptions.header.AttributeModifierException;
import org.anasoid.impexia.meta.exceptions.header.ImpexHeaderException;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.anasoid.impexia.meta.modifier.Modifier;
import org.anasoid.impexia.meta.modifier.ModifierManager;

/**
 * Default header Validator.
 */
public class CustomModifierValidator implements ModifierValidator {

  @Override
  @SuppressWarnings({"PMD.EmptyCatchBlock"})
  public boolean validate(ImpexModifier impexModifier, Mode mode) throws ImpexHeaderException {

    Modifier modifier = null;
    try {
      modifier = ModifierManager.getInstance().getValueByCode(impexModifier.getKey());
    } catch (java.lang.IllegalArgumentException e) {
      // nothing
    }
    validateCustomModifier(impexModifier, modifier);
    return false;
  }

  @SuppressWarnings({"PMD.EmptyCatchBlock", "PMD.CognitiveComplexity"})
  protected void validateCustomModifier(ImpexModifier impexModifier, Modifier modifier)
      throws ImpexHeaderException {
    if (modifier == null) {
      List<ImpexModifier> modifiers;

      if (impexModifier.getAttribute() != null) {
        modifiers = impexModifier.getAttribute().getModifiers();
      } else if (impexModifier.getHeader() != null) {
        modifiers = impexModifier.getHeader().getModifiers();
      } else {
        throw new IllegalStateException("ImpexModifier has null level : " + impexModifier);
      }
      boolean acceptCustom = false;
      for (ImpexModifier other : modifiers) {
        try {
          Modifier modifierEnum = ModifierManager.getInstance().getValueByCode(other.getKey());
          if (modifierEnum.isAcceptCustomAttribute()) {
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
}