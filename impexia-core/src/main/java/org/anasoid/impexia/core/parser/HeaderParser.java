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

import java.text.MessageFormat;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.apache.commons.lang3.Validate;

/** Header parser. */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class HeaderParser {

  private static final char[] RESERVER_FIELD_CHARS = {']', '[', '(', ')', ' '};

  private HeaderParser() {}

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
    String field = extractField(candidateTrim, header);
    split.setField(field.trim());
    candidateTrim = candidateTrim.substring(field.length());

    if (!header) {
      String mapping = extractMapping(candidateTrim);
      split.setMapping(mapping.trim());
      candidateTrim = candidateTrim.substring(mapping.length());
    }
    split.setModifiers(extractModifiers(candidateTrim).trim());
    return split;
  }

  protected static String extractMapping(String candidate) {
    if (candidate.indexOf('[') < 0) {
      return candidate;
    }
    return candidate.substring(0, Math.min(candidate.indexOf('['), candidate.length()));
  }

  protected static String extractModifiers(String candidate) throws InvalidHeaderFormatException {
    String candidateClean = candidate.trim();
    if (candidateClean.isEmpty()
        || ((candidateClean.charAt(0) == '[')
            && (candidateClean.charAt(candidateClean.length() - 1) == ']'))) {
      return candidate;
    }
    throw new InvalidHeaderFormatException(
        MessageFormat.format("Header not valid (({0})), Invalid modifiers", candidate));
  }

  protected static String extractField(String candidate, boolean header)
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
      // attribute should have mapping start with ( before modifier
      if (indexParentheses < 0) {
        throw new InvalidHeaderFormatException(
            MessageFormat.format(
                "Header not valid (({0})), mapping not found on field ", candidate));
      }
      // attribute should have mapping start with ( before modifier
      if (indexParentheses > indexBracket && indexBracket >= 0) {
        throw new InvalidHeaderFormatException(
            MessageFormat.format(
                "Header not valid (({0})), incorrect position of '[' ", candidate));
      }

      field = candidate.substring(0, indexParentheses);
    }
    if (!validateField(field)) {
      throw new InvalidHeaderFormatException(
          MessageFormat.format("Header not valid (({0})) ", candidate));
    }
    return field;
  }

  protected static boolean validateField(String field) {
    String cleanField = field.trim();
    for (char c : RESERVER_FIELD_CHARS) {
      if (cleanField.indexOf(c) >= 0) {
        return false;
      }
    }
    return true;
  }

  protected static class AttributeSplit {
    private ImpexAction action;
    private String field;
    private String mappings;
    private String modifiers;

    public String getField() {
      return field;
    }

    public void setField(String field) {
      this.field = field;
    }

    public String getMappings() {
      return mappings;
    }

    public void setMapping(String mappings) {
      this.mappings = mappings;
    }

    public String getModifiers() {
      return modifiers;
    }

    public void setModifiers(String moifiers) {
      this.modifiers = moifiers;
    }

    public ImpexAction getAction() {
      return action;
    }

    public void setAction(ImpexAction action) {
      this.action = action;
    }
  }
}
