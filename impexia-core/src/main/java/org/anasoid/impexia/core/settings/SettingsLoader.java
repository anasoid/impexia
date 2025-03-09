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
 * Date :   12-Jan-2025
 */

package org.anasoid.impexia.core.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.anasoid.impexia.core.exceptions.ImpexTechnicalException;
import org.anasoid.impexia.core.manager.config.AbstractImpexSettings;

@Slf4j
public final class SettingsLoader {

  private static final String SETTINGS_FILE = "impexia.properties";

  private SettingsLoader() {}

  public static <T extends AbstractImpexSettings> T load(T settings) {
    return settings;
  }

  /**
   * Load default properties from the "impexia.properties" file in the classpath.
   *
   * @return Properties object containing the default settings
   */
  @SuppressWarnings("PMD.EmptyCatchBlock")
  public static Properties loadDefaultProperties() {
    Properties properties = new Properties();
    try {
      loadSingleFile(SETTINGS_FILE, properties);
    } catch (IOException e) {
      // Do nothing default config file is optional.
    }
    return properties;
  }

  /**
   * Load properties from multiple files, skipping any files that cannot be found.
   *
   * @param propertyFiles List of property file paths
   * @param failOnError if true, throws exception on file error; if false, skips problematic files
   * @return Properties object containing all loaded properties
   */
  public static Properties loadProperties(
      List<String> propertyFiles, Properties properties, boolean failOnError) {
    for (String propertyFile : propertyFiles) {
      try {
        loadSingleFile(propertyFile, properties);
      } catch (IOException e) {
        if (failOnError) {
          throw new ImpexTechnicalException("Failed to load properties file: " + propertyFile, e);
        }
        // Log the warning but continue processing
        log.debug("Optional config file: {} not found", propertyFile);
      }
    }
    return properties;
  }

  /**
   * Load properties from multiple files, skipping any files that cannot be found.
   *
   * @param propertyFiles List of property file paths
   * @param failOnError if true, throws exception on file error; if false, skips problematic files
   * @return Properties object containing all loaded properties
   */
  public static Properties loadProperties(List<String> propertyFiles, boolean failOnError) {

    return loadProperties(propertyFiles, new Properties(), failOnError);
  }

  /**
   * Load properties from multiple files, treating all files as optional.
   *
   * @param propertyFiles List of property file paths
   * @return Properties object containing all loaded properties
   */
  public static Properties loadOptionalProperties(List<String> propertyFiles) {
    return loadProperties(propertyFiles, false);
  }

  /**
   * Load properties from multiple files using varargs, treating all files as optional.
   *
   * @param propertyFiles variable number of property file paths
   * @return Properties object containing all loaded properties
   */
  public static Properties loadOptionalProperties(String... propertyFiles) {
    return loadProperties(Arrays.asList(propertyFiles), false);
  }

  private static void loadSingleFile(String propertyFile, Properties properties)
      throws IOException {
    // First try classpath
    InputStream inputStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile);

    // If not found in classpath, try filesystem
    if (inputStream == null) {
      File file = new File(propertyFile);
      if (file.exists()) {
        try (InputStream fileInputStream = Files.newInputStream(file.toPath())) {
          properties.load(fileInputStream);
        }
      } else {
        throw new FileNotFoundException("Property file not found: " + propertyFile);
      }
    } else {
      try (InputStream is = inputStream) {
        properties.load(is);
      }
    }
  }
}
