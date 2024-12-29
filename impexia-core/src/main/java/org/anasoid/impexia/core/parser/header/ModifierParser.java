/*
 * Copyright 2020-2024 the original author or authors.
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
 * Date :   29-Dec-2024
 */

package org.anasoid.impexia.core.parser.header;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.anasoid.impexia.meta.modifier.ModifierDescriptor;
import org.anasoid.impexia.meta.modifier.ModifierDescriptorManager;
import org.apache.commons.lang3.StringUtils;

public final class ModifierParser {

  private ModifierParser() {}

  @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
  protected static List<ImpexModifier> parse(String rawModifiers) {
    List<ImpexModifier> result = new ArrayList<>();
    List<String> rawModifierList = HeaderRawExtractor.splitModifier(rawModifiers);
    for (String rawModifier : rawModifierList) {
      List<String> extract = extractKeyValueFromModifier(rawModifier);
      String key = extract.get(0);
      ModifierDescriptor modifierDescriptor = null;
      try {
        modifierDescriptor = ModifierDescriptorManager.getInstance().getValueByCode(key);
      } catch (IllegalArgumentException e) { // NOPMD
        // nothing
      }
      ImpexModifier impexModifier =
          ImpexModifier.builder(key, extract.get(1)).modifierDescriptor(modifierDescriptor).build();
      result.add(impexModifier);
    }
    return result;
  }

  private static List<String> extractKeyValueFromModifier(String modifier) {

    String cleanModifier = modifier;
    String key = cleanModifier.substring(0, cleanModifier.indexOf('=')).trim();
    String value = cleanModifier.substring(cleanModifier.indexOf('=') + 1).trim();

    if ((value.indexOf('"') > -1)
        && ((value.charAt(0) != '"') || (value.charAt(value.length() - 1) != '"'))) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format(InvalidHeaderFormatException.ERROR_INVALID_MODIFIER, modifier));
    }
    if ((value.indexOf('"') > -1)) {
      value = value.substring(1, value.length() - 1).trim();
    }
    if (!HeaderParserUtils.validateKey(key) || StringUtils.isBlank(value)) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format(InvalidHeaderFormatException.ERROR_INVALID_MODIFIER, modifier));
    }
    List<String> result = new ArrayList<>();
    result.add(key);
    result.add(value);
    return result;
  }
}
