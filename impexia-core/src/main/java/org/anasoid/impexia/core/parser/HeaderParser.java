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

package org.anasoid.impexia.core.parser;

import static org.anasoid.impexia.core.ParserConstants.FIELD_MAPPING_SEPARATOR;
import static org.anasoid.impexia.core.ParserConstants.PARENTHESES_END;
import static org.anasoid.impexia.core.ParserConstants.PARENTHESES_START;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.core.meta.header.DefaultImpexAttribute;
import org.anasoid.impexia.core.meta.header.DefaultImpexHeader;
import org.anasoid.impexia.core.meta.header.DefaultImpexMapping;
import org.anasoid.impexia.meta.header.ImpexMapping;
import org.apache.commons.lang3.StringUtils;

/** Header parser. */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public final class HeaderParser {

  private HeaderParser() {}

  protected static DefaultImpexAttribute parseAttribute(AttributeSplit split)
      throws InvalidHeaderFormatException {

    return null;
  }

  protected static DefaultImpexHeader parseAction(AttributeSplit split)
      throws InvalidHeaderFormatException {

    return null;
  }

  @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
  protected static List<ImpexMapping> parseMapping(String rawMapping)
      throws InvalidHeaderFormatException {
    List<ImpexMapping> result = new ArrayList<>();
    DefaultImpexMapping impexMapping;
    List<String> mappingList = splitMapping(rawMapping);
    for (String mapping : mappingList) {
      List<String> extract = extractFieldFromMapping(mapping);
      if (extract.get(1) != null) {
        impexMapping = new DefaultImpexMapping(extract.get(0).trim());
        impexMapping.addAllMapping(parseMapping(extract.get(1)));
      } else {
        impexMapping = new DefaultImpexMapping(extract.get(0).trim());
      }
      result.add(impexMapping);
    }

    return result;
  }

  private static List<String> extractFieldFromMapping(String mapping)
      throws InvalidHeaderFormatException {
    int indexP = mapping.indexOf(PARENTHESES_START);
    String field;
    String subMapping = null;
    if (indexP > -1 && indexP == mapping.length()) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format(InvalidHeaderFormatException.ERROR_INVALID_MAPPING, mapping));
    }
    if (indexP > -1) {
      field = mapping.substring(0, indexP);
      subMapping = mapping.substring(indexP, mapping.length());
    } else {
      field = mapping;
    }
    if (!HeaderParserUtils.validateField(field)) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format("Header not valid (({0})), Invalid field", field));
    }
    List<String> resul = new ArrayList<>();
    resul.add(field);
    resul.add(subMapping);
    return resul;
  }

  @SuppressWarnings("PMD.NPathComplexity")
  protected static List<String> splitMapping(String rawMapping) // NOSONAR
      throws InvalidHeaderFormatException {

    if (StringUtils.isBlank(rawMapping)
        || (rawMapping.charAt(0) != PARENTHESES_START)
        || (rawMapping.charAt(rawMapping.length() - 1) != PARENTHESES_END)) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format(InvalidHeaderFormatException.ERROR_INVALID_MAPPING, rawMapping));
    }
    String cleanMapping = rawMapping.substring(1, rawMapping.length() - 1);

    if (StringUtils.isWhitespace(cleanMapping)) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format(InvalidHeaderFormatException.ERROR_INVALID_MAPPING, rawMapping));
    }
    List<String> result = new ArrayList<>();
    int parentehseNB = 0;

    StringBuilder sb = new StringBuilder();
    boolean foundSplit = false;
    for (char c : cleanMapping.toCharArray()) {

      if (c == PARENTHESES_START) {
        parentehseNB++;
      } else if (c == PARENTHESES_END) {
        parentehseNB--;
      }
      if (parentehseNB < 0) {
        throw new InvalidHeaderFormatException(
            MessageFormat.format("Parenthese not correctly closed in (({0}))", rawMapping));
      }

      if (c == FIELD_MAPPING_SEPARATOR) {
        foundSplit = true;
        if (parentehseNB == 0) {
          String fragment = sb.toString().trim();
          validateFragment(fragment);
          if (fragment.isEmpty()) {
            throw new InvalidHeaderFormatException(
                MessageFormat.format("Invalid header at (({0}))", rawMapping));
          }
          result.add(fragment);
          sb = new StringBuilder(); // NOPMD
        } else {
          sb.append(c);
        }
      } else {
        sb.append(c);
      }
    }
    if (parentehseNB > 0) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format("Parentheses not correctly closed in (({0}))", rawMapping));
    }
    String fragment = sb.toString().trim();
    if (foundSplit) {
      if (!StringUtils.isBlank(fragment)) {
        result.add(fragment);
      } else {
        throw new InvalidHeaderFormatException(
            MessageFormat.format("Invalid header at (({0}))", rawMapping));
      }
    } else {
      result.add(fragment);
    }

    return result;
  }

  private static void validateFragment(String fragment) throws InvalidHeaderFormatException {

    if ((fragment.indexOf(FIELD_MAPPING_SEPARATOR) > -1)
        && (fragment.charAt(fragment.length() - 1) != PARENTHESES_END)) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format("Parenthese not correctly closed in (({0}))", fragment));
    }
  }
}
