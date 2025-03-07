package org.anasoid.impexia.core.settings;

/*
 * Copyright 2020-2025 the original author or authors.
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
 * Date :   07-Mar-2025
 */
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import org.anasoid.impexia.core.exceptions.ImpexTechnicalException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SettingsLoaderTest {

  @TempDir Path tempDir;

  private File testPropertiesFile;

  @BeforeEach
  void setUp() throws IOException {
    // Create a test properties file in the temporary directory
    testPropertiesFile = tempDir.resolve("test.properties").toFile();
    Properties properties = new Properties();
    properties.setProperty("test.key", "test.value");
    try (FileWriter writer = new FileWriter(testPropertiesFile)) {
      properties.store(writer, "Test properties");
    }
  }

  @AfterEach
  void tearDown() {
    // The temporary directory and its contents are automatically deleted
  }

  @Test
  void testLoadDefaultProperties() {
    Properties properties = SettingsLoader.loadDefaultProperties();
    assertNotNull(properties);
  }

  @Test
  void testLoadProperties() {
    Properties properties =
        SettingsLoader.loadProperties(List.of(testPropertiesFile.getPath()), true);
    assertNotNull(properties);
    assertEquals("test.value", properties.getProperty("test.key"));
  }

  @Test
  void testLoadOptionalProperties() {
    Properties properties = SettingsLoader.loadOptionalProperties(testPropertiesFile.getPath());
    assertNotNull(properties);
    assertEquals("test.value", properties.getProperty("test.key"));
  }

  @Test
  void testLoadProperties_FileNotFound() {
    assertThrows(
        ImpexTechnicalException.class,
        () -> SettingsLoader.loadProperties(List.of("nonexistent.properties"), true));
  }

  @Test
  void testLoadOptionalProperties_FileNotFound() {
    Properties properties = SettingsLoader.loadOptionalProperties("nonexistent.properties");
    assertNotNull(properties);
    assertTrue(properties.isEmpty());
  }
}
