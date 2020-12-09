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
 * Date :   01-Dec-2020
 */

package org.anasoid.impexia.core.parser;

import java.util.Iterator;
import java.util.List;
import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * HeaderParser test.
 *
 * @see HeaderParser
 */
class HeaderRawExtractorTest {

  @ParameterizedTest
  @CsvSource({
    " INSERT_UPDATE  Product  [unique= true] ,    INSERT_UPDATE,    Product,    [unique= true]",
    " INSERT_UPDATE  Product   ,INSERT_UPDATE , Product,  ''",
    " INSERT_UPDATE  Product  [unique= true] [batchmode=\"false\"] ,"
        + " INSERT_UPDATE , Product , [unique= true] [batchmode=\"false\"]"
  })
  void testSuccessHeader(String condidate, String action, String field, String modifiers)
      throws InvalidHeaderFormatException {
    AttributeSplit split = HeaderRawExtractor.split(condidate, true);
    Assertions.assertEquals(ImpexAction.valueOf(action), split.getAction());
    Assertions.assertEquals(field, split.getField());
    Assertions.assertNull(split.getMappings());
    Assertions.assertEquals(modifiers, split.getModifiers());
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        " INSERT_UPDATE  Product()  [unique= true] [batchmode=\"false\"] ",
        " INSERT_UPDATE  Product df  [unique= true] [batchmode=\"false\"] ",
        " INSERT_UPDATE  Product]  [unique= true] [batchmode=\"false\"] ",
        " INSERT_UPDAT  Product "
      })
  void testHeaderError(String condidate) {
    try {
      HeaderRawExtractor.split(condidate, true);
      Assertions.fail(condidate);
    } catch (InvalidHeaderFormatException e) {
      // success
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'[ unique = true ]','unique = true'",
    "'[unique=true][key=19]','unique=true|key=19'",
    "'[unique=true][ key =\" 1]9]','unique=true|key =\" 1]9'",
  })
  void testSplitModifierSuccess(String mapping, String result) throws InvalidHeaderFormatException {

    List<String> resList = HeaderRawExtractor.splitModifier(mapping);
    Assertions.assertEquals(result, toString(resList));
  }

  @ParameterizedTest
  @ValueSource(strings = {"[id=sd", "id=sd]", "[id=value][  ]"})
  void testSplitModifierError(String modifier) {
    try {
      List<String> resList = HeaderRawExtractor.splitModifier(modifier);
      Assertions.fail(toString(resList));
    } catch (InvalidHeaderFormatException e) {
      // success
    }
  }

  @ParameterizedTest
  @CsvSource({
    "  code (id) [unique = true] , code,  (id),   [unique = true]",
    "  code (id), code,  (id),   ''",
    "  'code (id,catalog(id))[unique = true][unique = true]',"
        + " code,  '(id,catalog(id))',   [unique = true][unique = true]"
  })
  void testSuccessAttribute(String candidate, String field, String mapping, String modifiers)
      throws InvalidHeaderFormatException {
    AttributeSplit split = HeaderRawExtractor.split(candidate, false);
    Assertions.assertNull(split.getAction());
    Assertions.assertEquals(field, split.getField());
    Assertions.assertEquals(mapping, split.getMappings());
    Assertions.assertEquals(modifiers, split.getModifiers());
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        " code name (id) [unique = true] ",
        " code] (id) [unique = true] ",
        " code) (id) [unique = true] ",
        " code) [unique = true] ",
        " code [unique = true] (id)",
        " code [unique = true]",
        " code(id) [unique = true",
        " code(id [unique = true]"
      })
  void testAttributeError(String condidate) {
    try {
      HeaderRawExtractor.split(condidate, false);
      Assertions.fail(condidate);
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

    List<String> resList = HeaderRawExtractor.splitMapping(mapping);
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
      List<String> result = HeaderRawExtractor.splitMapping(mapping);
      Assertions.fail(toString(result));
    } catch (InvalidHeaderFormatException e) {
      // success
    }
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
