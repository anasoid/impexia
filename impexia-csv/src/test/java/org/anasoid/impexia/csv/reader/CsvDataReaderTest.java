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
 * Date :   22-Nov-2020
 */

package org.anasoid.impexia.csv.reader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.anasoid.impexia.core.data.DataLine;
import org.anasoid.impexia.csv.InvalidCsvFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CsvDataReaderTest {

  @Test
  void loadStandardFile() throws IOException {
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard.impex");
    CsvDataReader dateReader =
        new CsvDataReader(new File(url.getFile()), new ConfigCsvReaderBuilder().build());
    Assertions.assertEquals(4, dateReader.getHeader().length);
    // header
    Assertions.assertEquals("INSERT_UPDATE product", dateReader.getHeader()[0]);
    Assertions.assertEquals("description", dateReader.getHeader()[3]);
    Assertions.assertEquals(dateReader.getRecordCount(), -1);
    Assertions.assertEquals(1, dateReader.getCurrentPass());
    // first line
    DataLine line = dateReader.nextRecord();
    Assertions.assertEquals("1", line.getRecord()[1].trim());
    Assertions.assertEquals(" Toys", line.getRecord()[2]);
    Assertions.assertEquals(4, line.getLineNumber());
    Assertions.assertEquals(1, line.getRecordNumber());
    // second line
    line = dateReader.nextRecord();
    Assertions.assertEquals("2", line.getRecord()[1].trim());
    Assertions.assertEquals("multi\n line", line.getRecord()[2]);
    Assertions.assertEquals(6, line.getLineNumber());
    Assertions.assertEquals(2, line.getRecordNumber());
    // third line
    line = dateReader.nextRecord();
    Assertions.assertEquals("3", line.getRecord()[1].trim());
    Assertions.assertEquals("Processus ", line.getRecord()[2]);
    Assertions.assertNull(line.getRecord()[3]);
    Assertions.assertEquals(8, line.getLineNumber());
    Assertions.assertEquals(3, line.getRecordNumber());
    // end line
    line = dateReader.nextRecord();
    Assertions.assertNull(line);
    dateReader.close();
  }

  @Test
  void loadStandardFileWithTotal() throws IOException {
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard.impex");
    CsvDataReader dateReader =
        new CsvDataReader(
            new File(url.getFile()), new ConfigCsvReaderBuilder().setNeedTotal(true).build());
    Assertions.assertEquals(4, dateReader.getHeader().length);
    // header
    Assertions.assertEquals("INSERT_UPDATE product", dateReader.getHeader()[0]);
    Assertions.assertEquals("description", dateReader.getHeader()[3]);
    Assertions.assertEquals(3, dateReader.getRecordCount());
    Assertions.assertEquals(1, dateReader.getCurrentPass());
    // first line
    DataLine line = dateReader.nextRecord();
    Assertions.assertEquals("1", line.getRecord()[1].trim());
    Assertions.assertEquals(" Toys", line.getRecord()[2]);
    Assertions.assertEquals(4, line.getLineNumber());
    Assertions.assertEquals(1, line.getRecordNumber());

    dateReader.close();
  }

  @Test
  void loadStandardHeaderError() throws IOException {
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard_header_error.impex");
    try {

      new CsvDataReader(new File(url.getFile()), new ConfigCsvReaderBuilder().build());
      Assertions.fail("Header invalid should faild");
    } catch (InvalidCsvFormatException e) {
      // success
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }

  @Test
  void notFoundFile() throws IOException {

    try {

      new CsvDataReader(new File("./notfound.impex"), new ConfigCsvReaderBuilder().build());
      Assertions.fail("Fle nout found");
    } catch (java.io.FileNotFoundException e) {
      // success
    }
  }

  @Test
  void testImpexWithoutHeader() throws IOException {
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard_header_error.impex");

    CsvDataReader dateReader =
        new CsvDataReader(
            new File(url.getFile()),
            new ConfigCsvReaderBuilder().setContainHeader(false).setNeedTotal(true).build());
    Assertions.assertNull(dateReader.getHeader());
    // header
    DataLine line = dateReader.nextRecord();
    Assertions.assertEquals("code[unique = true]", line.getRecord()[1].trim());
    Assertions.assertEquals(2, dateReader.getRecordCount());
    Assertions.assertEquals(1, dateReader.getCurrentPass());
  }

  @Test
  void testImpexHeaderNotFound() throws IOException {
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard.impex");

    try {

      new CsvDataReader(
          new File(url.getFile()),
          new ConfigCsvReaderBuilder().setNeedTotal(true).setSkipLines(100).build());
      Assertions.fail("Header not found");
    } catch (InvalidCsvFormatException e) {
      // success
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }

  @Test
  void testImpexCommentError() throws IOException {
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard_comment_error.impex");

    try {

      new CsvDataReader(new File(url.getFile()), new ConfigCsvReaderBuilder().build());
      Assertions.fail("Header not found");
    } catch (InvalidCsvFormatException e) {
      // success
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }

  @Test
  void loadStandardHeaderErrorWithTotal() throws IOException {
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard_header_error.impex");
    try {

      new CsvDataReader(
          new File(url.getFile()), new ConfigCsvReaderBuilder().setNeedTotal(true).build());
      Assertions.fail("Header invalid should faild");
    } catch (InvalidCsvFormatException e) {
      // success
    } catch (Exception e) {
      Assertions.fail(e);
    }
  }

  @Test
  void loadStandardFileRestartSkip() throws IOException { // NOSONAR
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard.impex");
    CsvDataReader dateReader =
        new CsvDataReader(new File(url.getFile()), new ConfigCsvReaderBuilder().build());
    Assertions.assertEquals(4, dateReader.getHeader().length);
    // header
    Assertions.assertEquals("INSERT_UPDATE product", dateReader.getHeader()[0]);
    Assertions.assertEquals("description", dateReader.getHeader()[3]);
    Assertions.assertEquals(-1, dateReader.getRecordCount());
    Assertions.assertEquals(1, dateReader.getCurrentPass());
    // first line
    DataLine line = dateReader.nextRecord();
    Assertions.assertEquals("1", line.getRecord()[1].trim());
    Assertions.assertEquals(" Toys", line.getRecord()[2]);
    Assertions.assertEquals(4, line.getLineNumber());
    Assertions.assertEquals(1, line.getRecordNumber());
    // second line
    line = dateReader.nextRecord();
    Assertions.assertEquals("2", line.getRecord()[1].trim());
    Assertions.assertEquals("multi\n line", line.getRecord()[2]);
    Assertions.assertEquals(6, line.getLineNumber());
    Assertions.assertEquals(2, line.getRecordNumber());
    // third line
    line = dateReader.nextRecord();
    Assertions.assertEquals("3", line.getRecord()[1].trim());
    Assertions.assertEquals("Processus ", line.getRecord()[2]);
    Assertions.assertEquals(8, line.getLineNumber());
    Assertions.assertEquals(3, line.getRecordNumber());
    // end line
    line = dateReader.nextRecord();
    Assertions.assertNull(line);
    // Restart
    dateReader.restart();
    Assertions.assertEquals(2, dateReader.getCurrentPass());
    // header
    // first line
    line = dateReader.nextRecord();
    Assertions.assertEquals("1", line.getRecord()[1].trim());
    Assertions.assertEquals(4, line.getLineNumber());
    Assertions.assertEquals(1, line.getRecordNumber());
    // second line
    Assertions.assertTrue(dateReader.skipRecord());
    // third line
    line = dateReader.nextRecord();
    Assertions.assertEquals("3", line.getRecord()[1].trim());
    Assertions.assertEquals(8, line.getLineNumber());
    Assertions.assertEquals(3, line.getRecordNumber());
    // end line
    line = dateReader.nextRecord();
    Assertions.assertNull(line);
    Assertions.assertFalse(dateReader.skipRecord());
    dateReader.close();
  }

  @Test
  void loadCommentFile() throws IOException {
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard_comment.impex");
    CsvDataReader dateReader =
        new CsvDataReader(new File(url.getFile()), new ConfigCsvReaderBuilder().build());
    Assertions.assertEquals(4, dateReader.getHeader().length);
    // first line
    DataLine line = dateReader.nextRecord();
    Assertions.assertEquals("1", line.getRecord()[1].trim());
    Assertions.assertEquals(1, line.getRecordNumber());

    dateReader.close();
  }

  @Test
  void loadStandardFileCustom() throws IOException { // NOSONAR
    URL url =
        this.getClass()
            .getClassLoader()
            .getResource("org/anasoid/impexia/csv/reader/standard_custom.impex");
    CsvDataReader dateReader =
        new CsvDataReader(
            new File(url.getFile()),
            new ConfigCsvReaderBuilder()
                .setNeedTotal(true)
                .setCommentChar('|')
                .setSkipLines(1)
                .setSeparatorChar(',')
                .setQuoteChar('\'')
                .build());
    Assertions.assertEquals(4, dateReader.getHeader().length);
    // header
    Assertions.assertEquals("INSERT_UPDATE product", dateReader.getHeader()[0]);
    Assertions.assertEquals("description", dateReader.getHeader()[3]);
    Assertions.assertEquals(3, dateReader.getRecordCount());
    Assertions.assertEquals(1, dateReader.getCurrentPass());
    // first line
    DataLine line = dateReader.nextRecord();
    Assertions.assertEquals("1", line.getRecord()[1].trim());
    Assertions.assertEquals(" Toys", line.getRecord()[2]);
    Assertions.assertEquals(6, line.getLineNumber());
    Assertions.assertEquals(1, line.getRecordNumber());
    // second line
    line = dateReader.nextRecord();
    Assertions.assertEquals("2", line.getRecord()[1].trim());
    Assertions.assertEquals("multi\n line", line.getRecord()[2]);
    Assertions.assertEquals(8, line.getLineNumber());
    Assertions.assertEquals(2, line.getRecordNumber());
    // third line
    line = dateReader.nextRecord();
    Assertions.assertEquals("3", line.getRecord()[1].trim());
    Assertions.assertEquals("Processus ", line.getRecord()[2]);
    Assertions.assertEquals(10, line.getLineNumber());
    Assertions.assertEquals(3, line.getRecordNumber());
    // end line
    line = dateReader.nextRecord();
    Assertions.assertNull(line);
    // Restart
    dateReader.restart();
    Assertions.assertEquals(2, dateReader.getCurrentPass());
    // header
    // first line
    line = dateReader.nextRecord();
    Assertions.assertEquals("1", line.getRecord()[1].trim());
    Assertions.assertEquals(6, line.getLineNumber());
    Assertions.assertEquals(1, line.getRecordNumber());
    // second line
    Assertions.assertTrue(dateReader.skipRecord());
    // third line
    line = dateReader.nextRecord();
    Assertions.assertEquals("3", line.getRecord()[1].trim());
    Assertions.assertEquals(10, line.getLineNumber());
    Assertions.assertEquals(3, line.getRecordNumber());
    // end line
    line = dateReader.nextRecord();
    Assertions.assertNull(line);
    Assertions.assertFalse(dateReader.skipRecord());
    dateReader.close();
  }
}
