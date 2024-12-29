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

package org.anasoid.impexia.core.parser.header;

import static org.assertj.core.api.Assertions.assertThat;

import org.anasoid.impexia.core.exceptions.InvalidHeaderFormatException;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * HeaderParser Test.
 *
 * @see HeaderParser
 */
class HeaderParserTest {

  @ParameterizedTest
  @CsvFileSource(resources = "/parser/testParseHeaderSuccess.csv")
  void testParseHeaderSuccess(String header, String result) throws InvalidHeaderFormatException {
    String[] columns = header.split(";");
    ImpexHeader impexHeader = HeaderParser.parse(columns);
    assertThat(impexHeader).hasToString(result);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "insert_update product [batchmode=true]",
        "insert_update product [batchmode=true];insert_update product [batchmode=true]",
        "insert_update product [batchmode=true];code[unique=true](id)",
      })
  void testParseHeaderError(String header) {

    String[] columns = header.split(";");
    Assertions.assertThrows(InvalidHeaderFormatException.class, () -> HeaderParser.parse(columns));
  }
}
