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

import static org.anasoid.impexia.core.ParserConstants.PARENTHESES_START;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.core.meta.header.DefaultImpexAttribute;
import org.anasoid.impexia.core.meta.header.DefaultImpexHeader;
import org.anasoid.impexia.core.meta.header.DefaultImpexMapping;
import org.anasoid.impexia.meta.header.ImpexMapping;

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
    List<String> mappingList = HeaderRawExtractor.splitMapping(rawMapping);
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
}
