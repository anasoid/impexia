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

import static org.anasoid.impexia.core.ParserConstants.PARENTHESES_START;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexMapping;
import org.anasoid.impexia.meta.header.ImpexMapping.ImpexMappingBuilder;

public final class MappingParser {

  private MappingParser() {}

  protected static List<ImpexMapping> parse(String rawMapping) {
    List<ImpexMapping> result = new ArrayList<>();

    List<String> mappingList = HeaderRawExtractor.splitMapping(rawMapping);
    for (String mapping : mappingList) {
      List<String> extract = extractFieldFromMapping(mapping);
      ImpexMappingBuilder impexMappingBuilder;
      if (extract.get(1) != null) {
        impexMappingBuilder =
            ImpexMapping.builder(extract.get(0).trim()).mappings(parse(extract.get(1)));

      } else {
        impexMappingBuilder = ImpexMapping.builder(extract.get(0).trim());
      }
      result.add(impexMappingBuilder.build());
    }

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
