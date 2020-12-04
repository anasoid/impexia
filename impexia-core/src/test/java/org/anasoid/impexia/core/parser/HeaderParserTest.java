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
 * Date :   03-Dec-2020
 */

package org.anasoid.impexia.core.parser;

import java.util.Iterator;
import java.util.List;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * HeaderParser Test.
 *
 * @see HeaderParser
 */
class HeaderParserTest {

  @ParameterizedTest
  @CsvSource({
    "'[ unique = true ]','unique = true'",
    "'[unique=true][key=19]','unique=true|key=19'",
    "'[unique=true][ key =\" 1]9]','unique=true|key =\" 1]9'",
  })
  void testSplitModifierSuccess(String mapping, String result) throws InvalidHeaderFormatException {

    List<String> resList = HeaderParser.splitModifier(mapping);
    Assertions.assertEquals(result, toString(resList));
  }

  @ParameterizedTest
  @ValueSource(strings = {"[id=sd", "id=sd]", "[id=value][  ]"})
  void testSplitModifierError(String modifier) {
    try {
      List<String> resList = HeaderParser.splitModifier(modifier);
      ;
      Assertions.fail(toString(resList));
    } catch (InvalidHeaderFormatException e) {
      // success
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'(id ,name )','DefaultImpexMapping{field=''id''}|DefaultImpexMapping{field=''name''}'",
    "'(catalog (id,type) , version )',"
        + "'DefaultImpexMapping{field=''catalog'', mappings=[DefaultImpexMapping{field=''id''},"
        + " DefaultImpexMapping{field=''type''}]}|DefaultImpexMapping{field=''version''}'",
    "'( catalog ( id ,type ), version,domaine (id, subid(id1,id2)) )',"
        + "'DefaultImpexMapping{field=''catalog'', mappings=[DefaultImpexMapping{field=''id''}, "
        + "DefaultImpexMapping{field=''type''}]}|"
        + "DefaultImpexMapping{field=''version''}|"
        + "DefaultImpexMapping{field=''domaine'', mappings=[DefaultImpexMapping{field=''id''},"
        + " DefaultImpexMapping{field=''subid'',"
        + " mappings=[DefaultImpexMapping{field=''id1''}, DefaultImpexMapping{field=''id2''}]}]}'",
    "'(id1(id11(id111)))','DefaultImpexMapping{field=''id1'',"
        + " mappings=[DefaultImpexMapping{field=''id11'',"
        + " mappings=[DefaultImpexMapping{field=''id111''}]}]}'"
  })
  void testParseMappingSuccess(String mapping, String result) throws InvalidHeaderFormatException {

    List<ImpexMapping> resList = HeaderParser.parseMapping(mapping);
    Assertions.assertEquals(result, toStringImpexMapping(resList));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "(id,name",
        "id,name)",
        "((id,name)",
        "(id,name))",
        "(id,name),",
        "(id1,id2(id21,)),",
        "(id1,(id21,id22)),",
        "(id1,id2((id21,id22))),",
        "(catalog (id,type)x, version )",
        "( )",
        "(id11,  )",
        "(id11, ,id12 )",
        " ",
        "\t"
      })
  void testParseMappingError(String mapping) {
    try {
      List<ImpexMapping> resList = HeaderParser.parseMapping(mapping);
      Assertions.fail(toStringImpexMapping(resList));
    } catch (InvalidHeaderFormatException e) {
      // success
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'(id,name)','id|name'",
    "'(catalog (id,type) , version )','catalog (id,type)|version'",
    "'(catalog (id,type), version,domaine (id,subid(id1,id2)) )',"
        + "'catalog (id,type)|version|domaine (id,subid(id1,id2))'",
    "'(id1(id11(id111)))','id1(id11(id111))'"
  })
  void testSplitMappingSuccess(String mapping, String result) throws InvalidHeaderFormatException {

    List<String> resList = HeaderParser.splitMapping(mapping);
    Assertions.assertEquals(result, toString(resList));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "(id,name",
        "id,name)",
        "((id,name)",
        "(id,name))",
        "(id,name),",
        "(id1,id2(id21,)),",
        "(id1,(id21,id22)),",
        "(id1,id2((id21,id22))),",
        "(catalog (id,type)x, version )",
        "( )",
        "(id11,  )",
        "(id11, ,id12 )",
        " ",
        "\t",
      })
  void testSplitMappingError(String mapping) {
    try {
      List<String> result = HeaderParser.splitMapping(mapping);
      Assertions.fail(toString(result));
    } catch (InvalidHeaderFormatException e) {
      // success
    }
  }

  private String toStringImpexMapping(List<ImpexMapping> list) {
    StringBuffer sb = new StringBuffer();
    Iterator<ImpexMapping> iterator = list.iterator();
    while (iterator.hasNext()) {
      sb.append(iterator.next());
      if (iterator.hasNext()) {
        sb.append("|");
      }
    }
    return sb.toString();
  }

  private String toString(List<String> list) {
    StringBuffer sb = new StringBuffer();
    Iterator<String> iterator = list.iterator();
    while (iterator.hasNext()) {
      sb.append(iterator.next());
      if (iterator.hasNext()) {
        sb.append("|");
      }
    }
    return sb.toString();
  }
}
