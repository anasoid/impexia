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
import org.anasoid.impexia.core.meta.header.DefaultImpexHeader;
import org.anasoid.impexia.meta.header.ImpexMapping;
import org.anasoid.impexia.meta.header.ImpexModifier;
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
      "insert_update product [batchmode=true]; code (id) [unique=true],"
          + " 'DefaultImpexHeader{type=''product'', action=INSERT_UPDATE,"
          + " modifier=[DefaultImpexModifier"
          + "{key=''batchmode'', value=''true'',"
          + " modifier=Modifier{code=''batchmode'', scope=''GLOBAL''}}],"
          + " attributes=[DefaultImpexAttribute"
          + "{field=''code'', mappings=[DefaultImpexMapping{field=''id''}],"
          + " special=false, modifier="
          + "[DefaultImpexModifier{key=''unique'', value=''true'',"
          + " modifier=Modifier{code=''unique'', scope=''GLOBAL''}}]}]}'",
      "insert_update product [batchmode=true]; code (id) [unique=true];catalogvesion (id);"
          + " name;date [dateformat=\'yyyy-MM-dd\'],"
          + "'DefaultImpexHeader{type=''product'', action=INSERT_UPDATE,"
          + " modifier=[DefaultImpexModifier{key=''batchmode'', value=''true'', "
          + "modifier=Modifier{code=''batchmode'', scope=''GLOBAL''}}],"
          + " attributes=[DefaultImpexAttribute{field=''code'',"
          + " mappings=[DefaultImpexMapping{field=''id''}],"
          + " special=false, modifier=[DefaultImpexModifier{key=''unique'', value=''true'',"
          + " modifier=Modifier{code=''unique'', scope=''GLOBAL''}}]},"
          + " DefaultImpexAttribute{field=''catalogvesion'',"
          + " mappings=[DefaultImpexMapping{field=''id''}],"
          + " special=false}, DefaultImpexAttribute{field=''name'', special=false},"
          + " DefaultImpexAttribute{field=''date'',"
          + " special=false, modifier=[DefaultImpexModifier{key=''dateformat'', "
          + "value=''''yyyy-MM-dd'''',"
          + " modifier=Modifier{code=''dateformat'', scope=''GLOBAL''}}]}]}'"
  })
  void testParseHeaderSuccess(String header, String result) throws InvalidHeaderFormatException {
    String[] columns = header.split(";");
    DefaultImpexHeader impexHeader = HeaderParser.parseHeaderRow(columns);
    Assertions.assertEquals(result, impexHeader.toString());
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
          "insert_update product [batchmode=true]",
          "insert_update product [batchmode=true];insert_update product [batchmode=true]",
          "insert_update product [batchmode=true];code[unique=true](id)",
      })
  void testParseHeaderError(String header) {
    try {
      String[] columns = header.split(";");
      DefaultImpexHeader impexHeader = HeaderParser.parseHeaderRow(columns);
      Assertions.fail(impexHeader.toString());
    } catch (InvalidHeaderFormatException e) {
      // success
    }
  }

  @ParameterizedTest
  @CsvSource({
      "'[ unique = true ]','DefaultImpexModifier{key=''unique'', value=''true'',"
          + " modifier=Modifier{code=''unique'', scope=''GLOBAL''}}'",
      "'[unique=true][key=19]',"
          + "'DefaultImpexModifier{key=''unique'', value=''true'',"
          + " modifier=Modifier{code=''unique'', scope=''GLOBAL''}}|"
          + "DefaultImpexModifier{key=''key'', value=''19''}'",
      "'[unique=true ] [ key =\" 1]9\"]',"
          + "'DefaultImpexModifier{key=''unique'', value=''true'',"
          + " modifier=Modifier{code=''unique'', scope=''GLOBAL''}}|"
          + "DefaultImpexModifier{key=''key'', value=''1]9''}'",
      "'[ key = \"tr\"ue\" ]','DefaultImpexModifier{key=''key'', value=''tr\"ue''}'",
  })
  void testParseModifierSuccess(String mapping, String result) throws InvalidHeaderFormatException {

    List<ImpexModifier> resList = HeaderParser.parseModifier(mapping);
    Assertions.assertEquals(result, toStringImpexModifier(resList));
  }

  @ParameterizedTest
  @ValueSource(strings = {"[key=value", "[key=val\"ue]", "[ =value]", "[ke8y=value]"})
  void testParseModifierError(String mapping) {
    try {
      List<ImpexModifier> resList = HeaderParser.parseModifier(mapping);
      Assertions.fail(toStringImpexModifier(resList));
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
          + " mappings=[DefaultImpexMapping{field=''id1''}, "
          + "DefaultImpexMapping{field=''id2''}]}]}'",
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
          "(id1,id 2(id21))",
          "(id1,id[g(id21))",
          "(id1,((id21)))",
          "((id1))",
          "(id1",
          "(id1,(id21,id22)),",
          "(id1,(id21,id22)",
          "(id1,id2((id21,id22))),",
          "(catalog (id,type)x, version )",
          "( )",
          "(id11,  )",
          "(id11, ,id12 )"
      })
  void testParseMappingError(String mapping) {
    try {
      List<ImpexMapping> resList = HeaderParser.parseMapping(mapping);
      Assertions.fail(toStringImpexMapping(resList));
    } catch (InvalidHeaderFormatException e) {
      // success
    }
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
}
