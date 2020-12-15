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
 * Date :   02-Dec-2020
 */

package org.anasoid.impexia.core.parser;

import static org.anasoid.impexia.core.ParserConstants.BRACKET_END;
import static org.anasoid.impexia.core.ParserConstants.BRACKET_START;
import static org.anasoid.impexia.core.ParserConstants.FIELD_MAPPING_SEPARATOR;
import static org.anasoid.impexia.core.ParserConstants.PARENTHESES_END;
import static org.anasoid.impexia.core.ParserConstants.PARENTHESES_START;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** First level of parsing header. Extract raw field, mapping, modifiers as String. */
@SuppressWarnings("PMD.GodClass")
final class HeaderRawExtractor {

  private static final Logger LOGGER = LoggerFactory.getLogger(HeaderRawExtractor.class);

  private HeaderRawExtractor() {}

  protected static AttributeSplit split(String candidate, boolean header)
      throws InvalidHeaderFormatException {
    AttributeSplit split = new AttributeSplit();
    Validate.notNull(candidate);
    String candidateTrim = candidate.trim();
    // check header
    if (header) {
      String candidateCheck =
          candidateTrim.substring(0, Math.min(candidateTrim.length(), 20)).toUpperCase();
      for (ImpexAction impexAction : ImpexAction.values()) {
        if (candidateCheck.startsWith(impexAction.toString() + " ")) {
          split.setAction(impexAction);
          break;
        }
      }
      if (split.getAction() == null) {
        throw new InvalidHeaderFormatException(
            MessageFormat.format(
                "Header not valid (({0})) should start with Action", candidateTrim));
      }
      candidateTrim = candidateTrim.substring(split.getAction().toString().length()).trim();
    }
    String field = extractRawField(candidateTrim, header);
    split.setField(field.trim());
    candidateTrim = candidateTrim.substring(field.length());

    if (!header) {
      String mapping = extractRawMapping(candidateTrim);
      split.setMapping(mapping.trim());
      candidateTrim = candidateTrim.substring(mapping.length());
    }
    split.setModifiers(extractRawModifiers(candidateTrim).trim());
    LOGGER.debug("Split: {}->{}", candidate, split);
    return split;
  }

  private static String extractRawMapping(String candidate) throws InvalidHeaderFormatException {
    String mapping;
    if (candidate.indexOf('[') < 0) {
      mapping = candidate;
    } else if (candidate.indexOf('[') == 0) {
      return "";
    } else {
      mapping = candidate.substring(0, Math.min(candidate.indexOf('['), candidate.length()));
    }
    String mappingClean = mapping.trim();
    if (mappingClean.isEmpty()
        || (mappingClean.indexOf(']') < 0
            && (mappingClean.charAt(0) == '(')
            && (mappingClean.charAt(mappingClean.length() - 1) == ')'))) {
      return mapping;
    }
    throw new InvalidHeaderFormatException(
        MessageFormat.format("Header not valid (({0})), Invalid modifiers", candidate));
  }

  private static String extractRawModifiers(String candidate) throws InvalidHeaderFormatException {
    String candidateClean = candidate.trim();
    if (candidateClean.isEmpty()
        || ((candidateClean.charAt(0) == '[')
            && (candidateClean.charAt(candidateClean.length() - 1) == ']'))) {
      return candidate;
    }
    throw new InvalidHeaderFormatException(
        MessageFormat.format("Header not valid (({0})), Invalid modifiers", candidate));
  }

  private static String extractRawField(String candidate, boolean header)
      throws InvalidHeaderFormatException {
    int indexBracket = candidate.indexOf('[');
    int indexParentheses = candidate.indexOf('(');
    String field;
    if (header) {
      // header don't contain ( only as parameter of modifier
      if (indexParentheses >= 0 && indexParentheses < indexBracket) {
        throw new InvalidHeaderFormatException(
            MessageFormat.format(
                "Header not valid (({0})), incorrect position of '(' ", candidate));
      }
      if (indexBracket > 0) {
        field = candidate.substring(0, indexBracket);
      } else {
        field = candidate;
      }
    } else {

      if (indexParentheses > 0
          && (((indexParentheses < indexBracket) && indexBracket > 0) || indexBracket == -1)) {

        field = candidate.substring(0, indexParentheses);
      } else if (indexBracket > 0) {
        field = candidate.substring(0, indexBracket);
      } else {
        field = candidate;
      }
    }
    if (!HeaderParserUtils.validateField(field)) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format("Header not valid (({0})) ", candidate));
    }
    return field;
  }

  @SuppressWarnings("PMD.NPathComplexity")
  protected static List<String> splitMapping(String rawMapping) // NOSONAR
      throws InvalidHeaderFormatException {

    if (StringUtils.isBlank(rawMapping)) {
      return new ArrayList<>();
    }
    if ((rawMapping.charAt(0) != PARENTHESES_START)
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
          if (StringUtils.isBlank(fragment)) {
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

  @SuppressWarnings("PMD.NPathComplexity")
  protected static List<String> splitModifier(String rawModifiers) // NOSONAR
      throws InvalidHeaderFormatException {
    if (StringUtils.isBlank(rawModifiers)) {
      return new ArrayList<>();
    }
    if ((rawModifiers.charAt(0) != BRACKET_START)
        || (rawModifiers.charAt(rawModifiers.length() - 1) != BRACKET_END)) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format(InvalidHeaderFormatException.ERROR_INVALID_MODIFIER, rawModifiers));
    }

    String cleanModifiers = rawModifiers.replaceAll("\\]\\s*\\[", "][");

    List<String> result = new ArrayList<>();

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < cleanModifiers.length() - 1; i++) {
      char current = cleanModifiers.charAt(i);
      char next = cleanModifiers.charAt(i + 1);
      if ((current == BRACKET_END && next == BRACKET_START)
          || (i == cleanModifiers.length() - 2 && next == BRACKET_END)) {
        sb.append(current);
        if (i == cleanModifiers.length() - 2) {
          sb.append(next);
        }
        String fragment = sb.toString();
        fragment = fragment.substring(1, fragment.length() - 1).trim();
        if (StringUtils.isBlank(fragment)
            || fragment.indexOf('=') <= 0
            || fragment.indexOf('=') == fragment.length() - 1) {
          throw new InvalidHeaderFormatException(
              MessageFormat.format(
                  InvalidHeaderFormatException.ERROR_INVALID_MODIFIER, rawModifiers));
        }
        result.add(fragment);
        sb = new StringBuilder(); // NOPMD

      } else {
        sb.append(current);
      }
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
