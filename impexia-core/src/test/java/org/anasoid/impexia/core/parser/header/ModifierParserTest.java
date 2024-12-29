package org.anasoid.impexia.core.parser.header;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

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

class ModifierParserTest {

  @ParameterizedTest
  @CsvFileSource(resources = "/parser/testParseModifierSuccess.csv")
  void testParseModifierSuccess(String mapping, String result) throws InvalidHeaderFormatException {

    List<ImpexModifier> resList = ModifierParser.parse(mapping);
    assertThat(toStringImpexModifier(resList)).isEqualTo(result);
  }

  @ParameterizedTest
  @ValueSource(strings = {"[key=value", "[key=val\"ue]", "[ =value]", "[ke8y=value]"})
  void testParseModifierError(String mapping) {

    Assertions.assertThrows(
        InvalidHeaderFormatException.class, () -> ModifierParser.parse(mapping));
  }

  private String toStringImpexModifier(List<ImpexModifier> list) {
    StringBuffer sb = new StringBuffer();
    Iterator<ImpexModifier> iterator = list.iterator();
    while (iterator.hasNext()) {
      sb.append(iterator.next());
      if (iterator.hasNext()) {
        sb.append("|");
      }
    }
    return sb.toString();
  }
}
