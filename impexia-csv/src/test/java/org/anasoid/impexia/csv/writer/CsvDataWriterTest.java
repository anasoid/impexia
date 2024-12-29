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
 * Date :   28-Nov-2020
 */

package org.anasoid.impexia.csv.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CsvDataWriterTest {

  @Test
  void testSimpleWrite() throws IOException {
    StringWriter writer = new StringWriter();
    CsvDataWriter csvDataWriter = new CsvDataWriter(writer, ConfigCsvWriter.builder().build());
    csvDataWriter.writeHeader("INSERT_UPDATE Product", "code");
    csvDataWriter.writeLine(null, "1");
    csvDataWriter.writeLine(null, "a\"b");
    csvDataWriter.writeLine(null, "a\nb");
    csvDataWriter.writeLine(null, ";");
    csvDataWriter.close();
    Assertions.assertEquals(
        """
            INSERT_UPDATE Product;code
            ;1
            ;a""b
            ;"a
            b"
            ;";"
            """,
        writer.toString());
  }

  @Test
  void testWriteheaderTwiceError() throws IOException {
    StringWriter writer = new StringWriter();
    CsvDataWriter csvDataWriter = new CsvDataWriter(writer, ConfigCsvWriter.builder().build());
    csvDataWriter.writeHeader("INSERT_UPDATE Product", "code");
    try {
      csvDataWriter.writeHeader("INSERT_UPDATE Product", "code");
      Assertions.fail("Insert Header twice");
    } catch (java.lang.IllegalStateException e) {
      //
    }
    csvDataWriter.close();
  }

  @Test
  void testWriteHeaderAfterLineError() throws IOException {
    StringWriter writer = new StringWriter();
    CsvDataWriter csvDataWriter = new CsvDataWriter(writer, ConfigCsvWriter.builder().build());
    csvDataWriter.writeLine(null, "1");
    try {
      csvDataWriter.writeHeader("INSERT_UPDATE Product", "code");
      Assertions.fail("Insert Header twice");
    } catch (java.lang.IllegalStateException e) {
      //
    }
    csvDataWriter.close();
  }

  @Test
  void testSimpleWriteCustomQuote() throws IOException {
    StringWriter writer = new StringWriter();
    CsvDataWriter csvDataWriter =
        new CsvDataWriter(
            writer,
            ConfigCsvWriter.builder()
                .escapeChar('!')
                .lineEnd("\r\n")
                .quoteChar('\'')
                .separatorChar(',')
                .build());
    csvDataWriter.writeHeader("INSERT_UPDATE Product", "code");
    csvDataWriter.writeLine(null, "1");
    csvDataWriter.writeLine(null, "a\"b");
    csvDataWriter.writeLine(null, "a\nb");
    csvDataWriter.writeLine(null, ",");
    csvDataWriter.close();
    Assertions.assertEquals(
        "INSERT_UPDATE Product,code\r\n" + ",1\r\n" + ",a\"b\r\n" + ",'a\nb'\r\n" + ",','\r\n",
        writer.toString());
  }

  @Test
  void testWriteApplyQuoteToAll() throws IOException {
    StringWriter writer = new StringWriter();
    CsvDataWriter csvDataWriter =
        new CsvDataWriter(writer, ConfigCsvWriter.builder().applyQuotesToAll(true).build());
    csvDataWriter.writeHeader("INSERT_UPDATE Product", "code");
    csvDataWriter.writeLine(null, "1");
    csvDataWriter.writeLine(null, "a\"b");
    csvDataWriter.writeLine(null, "a\nb");
    csvDataWriter.writeLine(null, ";");
    csvDataWriter.close();
    Assertions.assertEquals(
        "INSERT_UPDATE Product;code\n" + ";\"1\"\n" + ";\"a\"\"b\"\n" + ";\"a\nb\"\n" + ";\";\"\n",
        writer.toString());
  }

  @Test
  void testWriteIoexception() throws IOException {
    Writer writer = new BufferedWriter(new StringWriter());
    CsvDataWriter csvDataWriter = new CsvDataWriter(writer, ConfigCsvWriter.builder().build());
    writer.close();
    try {
      csvDataWriter.writeHeader("INSERT_UPDATE Product", "code"); // SONAR

      Assertions.fail("IOexception ");
    } catch (IOException e) {
      //
    }
  }
}
