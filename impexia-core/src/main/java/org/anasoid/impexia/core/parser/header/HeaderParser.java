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
 * Date :   30-Nov-2020
 */

package org.anasoid.impexia.core.parser.header;

import static org.anasoid.impexia.core.ParserConstants.PARENTHESES_START;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexMapping;
import org.anasoid.impexia.meta.header.ImpexMapping.ImpexMappingBuilder;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.anasoid.impexia.meta.modifier.Modifier;
import org.anasoid.impexia.meta.modifier.ModifierManager;
import org.apache.commons.lang3.StringUtils;

/** Header parser. */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public final class HeaderParser {

  private HeaderParser() {}

  @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidLiteralsInIfCondition"})
  public static ImpexHeader parseHeaderRow(String... columns) {

    if (columns.length < 2) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format(InvalidHeaderFormatException.ERROR_INVALID, Arrays.asList(columns)));
    }
    ImpexHeader.ImpexHeaderBuilder impexHeaderBuild = null;
    for (int i = 0; i < columns.length; i++) {
      try {
        if (i == 0) {
          AttributeSplit split = HeaderRawExtractor.split(columns[i], true);
          impexHeaderBuild = ImpexHeader.builder(split.getField(), split.getAction());
          if (StringUtils.isNotBlank(split.getModifiers())) {
            impexHeaderBuild.modifiers(parseModifier(split.getModifiers()));
          }
        } else {
          AttributeSplit split = HeaderRawExtractor.split(columns[i], false);
          ImpexAttribute attribute =
              ImpexAttribute.builder(split.getField())
                  .mappings(parseMapping(split.getMappings()))
                  .modifiers(parseModifier(split.getModifiers()))
                  .build();
          impexHeaderBuild.attribute(attribute);
        }
      } catch (Exception e) { // NOPMD
        throw new InvalidHeaderFormatException(
            MessageFormat.format(
                InvalidHeaderFormatException.ERROR_INVALID_AT, Arrays.asList(columns), columns[i]),
            e);
      }
    }

    return impexHeaderBuild != null ? impexHeaderBuild.build() : null;
  }

  @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
  protected static List<ImpexModifier> parseModifier(String rawModifiers) {
    List<ImpexModifier> result = new ArrayList<>();
    List<String> rawModifierList = HeaderRawExtractor.splitModifier(rawModifiers);
    for (String rawModifier : rawModifierList) {
      List<String> extract = extractKeyValueFromModifier(rawModifier);
      String key = extract.get(0);
      Modifier modifier = null;
      try {
        modifier = ModifierManager.getInstance().getValueByCode(key);
      } catch (IllegalArgumentException e) { // NOPMD
        // nothing
      }
      ImpexModifier impexModifier =
          ImpexModifier.builder(key, extract.get(1)).modifier(modifier).build();
      result.add(impexModifier);
    }
    return result;
  }

  @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
  protected static List<ImpexMapping> parseMapping(String rawMapping) {
    List<ImpexMapping> result = new ArrayList<>();

    List<String> mappingList = HeaderRawExtractor.splitMapping(rawMapping);
    for (String mapping : mappingList) {
      List<String> extract = extractFieldFromMapping(mapping);
      ImpexMappingBuilder impexMappingBuilder;
      if (extract.get(1) != null) {
        impexMappingBuilder =
            ImpexMapping.builder(extract.get(0).trim()).mappings(parseMapping(extract.get(1)));

      } else {
        impexMappingBuilder = ImpexMapping.builder(extract.get(0).trim());
      }
      result.add(impexMappingBuilder.build());
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

  private static List<String> extractFieldFromMapping(String mapping) {
    int indexP = mapping.indexOf(PARENTHESES_START);
    String field;
    String subMapping = null;

    if (indexP > -1) {
      field = mapping.substring(0, indexP);
      subMapping = mapping.substring(indexP);
    } else {
      field = mapping;
    }
    if (!HeaderParserUtils.validateField(field)) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format("Header not valid (({0})), Invalid field", field));
    }
    List<String> result = new ArrayList<>();
    result.add(field);
    result.add(subMapping);
    return result;
  }
}
