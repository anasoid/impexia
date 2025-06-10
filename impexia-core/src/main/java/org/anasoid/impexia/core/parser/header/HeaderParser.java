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

import java.text.MessageFormat;
import java.util.Arrays;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.apache.commons.lang3.StringUtils;

/** Header parser. */
public final class HeaderParser {

  private HeaderParser() {}

  @SuppressWarnings({"PMD.AvoidLiteralsInIfCondition"})
  public static ImpexHeader parse(String... columns) {

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
            impexHeaderBuild.modifiers(ModifierParser.parse(split.getModifiers()));
          }
        } else {
          AttributeSplit split = HeaderRawExtractor.split(columns[i], false);
          ImpexAttribute attribute =
              ImpexAttribute.builder(split.getField())
                  .mappings(MappingParser.parse(split.getMappings()))
                  .modifiers(ModifierParser.parse(split.getModifiers()))
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
}
