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
 * Date :   18-Dec-2020
 */

package org.anasoid.impexia.core.validators.header;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.anasoid.impexia.core.validators.header.impl.CustomModifierValidator;
import org.anasoid.impexia.core.validators.header.impl.MappingAttribueValidator;
import org.anasoid.impexia.core.validators.header.impl.RawHeaderValidator;
import org.anasoid.impexia.core.validators.header.impl.UniqueModifierValidator;
import org.anasoid.impexia.core.validators.header.impl.ValueModifierValidator;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.exceptions.header.ImpexHeaderException;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.anasoid.impexia.meta.modifier.Level;

public class HeaderValidatorManager implements HeaderValidator {

  private final Map<Class, HeaderValidator> headerValidators = new HashMap<>(); // NOPMD
  private final Map<Class, AttributeValidator> attributeValidators = new HashMap<>(); // NOPMD
  private final Map<Class, ModifierValidator> modifierHeaderValidators = new HashMap<>(); // NOPMD

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private final Map<Class, ModifierValidator> modifierAttributeValidators = new HashMap<>();

  HeaderValidatorManager() {
    init();
  }

  private void init() {
    register(new RawHeaderValidator());
    register(new UniqueModifierValidator());
    register(new CustomModifierValidator(), Arrays.asList(Level.FIELD, Level.TYPE));
    register(new ValueModifierValidator(), Arrays.asList(Level.FIELD, Level.TYPE));
    register(new MappingAttribueValidator());
  }

  /**
   * Validate header.
   *
   * @param header header
   * @param mode mode
   * @throws ImpexHeaderException throw exception if header not valid.
   */
  @Override
  public void validate(ImpexHeader header, Mode mode) throws ImpexHeaderException {

    for (HeaderValidator validator : headerValidators.values()) {
      validator.validate(header, mode);
    }

    List<ImpexModifier> headerModifiers = header.getModifiers();
    for (ImpexModifier modifier : headerModifiers) {
      for (ModifierValidator modifierValidator : modifierHeaderValidators.values()) {
        modifierValidator.validate(modifier, mode);
      }
    }

    List<ImpexAttribute> attributes = header.getAttributes();
    for (ImpexAttribute attribute : attributes) {
      for (AttributeValidator attributeValidator : attributeValidators.values()) {
        attributeValidator.validate(attribute, mode);
      }

      for (ImpexModifier modifier : attribute.getModifiers()) {
        for (ModifierValidator modifierValidator : modifierAttributeValidators.values()) {
          modifierValidator.validate(modifier, mode);
        }
      }
    }
  }

  private void register(HeaderValidator validator) {

    headerValidators.put(validator.getClass(), validator);
  }

  private void register(AttributeValidator validator) {

    attributeValidators.put(validator.getClass(), validator);
  }

  private void register(ModifierValidator validator, List<Level> levels) {

    if (levels.contains(Level.TYPE)) {
      modifierHeaderValidators.put(validator.getClass(), validator);
    }
    if (levels.contains(Level.FIELD)) {
      modifierAttributeValidators.put(validator.getClass(), validator);
    }
  }
}
