package org.anasoid.impexia.core.settings;

import static org.junit.jupiter.api.Assertions.*;

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
 * Date :   13-Feb-2025
 */
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyInjectorTest {

  private Properties properties;

  @BeforeEach
  public void setup() {
    // Initialize the Properties object and add test data
    properties = new Properties();
    properties.setProperty("app.config.timeout", "30");
    properties.setProperty("app.config.url", "http://example.com");
    properties.setProperty("app.config.apiKey", "12345");
    properties.setProperty("app.config.additionalProperty", "SomeParentValue");
  }

  @Test
  public void testInjectProperties() throws IllegalAccessException {
    // Create an instance of the subclass (SubConfig)
    SubConfig subConfig = new SubConfig();

    // Inject properties into the subConfig object
    PropertyInjector.injectProperties(subConfig, properties);

    // Verify the properties injected into SubConfig class fields
    assertEquals(30, subConfig.getTimeout());
    assertEquals("http://example.com", subConfig.getUrl());
    assertEquals("12345", subConfig.getApiKey());

    // Verify the properties injected into SuperConfig class fields (parent class)
    assertEquals("SomeParentValue", subConfig.getAdditionalProperty());
  }

  @Test
  public void testInjectPropertiesWithMissingProperties() throws IllegalAccessException {
    // Create an instance of the subclass (SubConfig)
    SubConfig subConfig = new SubConfig();

    // Set a Properties object that misses some properties
    Properties incompleteProperties = new Properties();
    incompleteProperties.setProperty("app.config.timeout", "45");

    // Inject properties into the subConfig object
    PropertyInjector.injectProperties(subConfig, incompleteProperties);

    // Verify that only the available properties were injected
    assertEquals(45, subConfig.getTimeout());
    assertEquals(null, subConfig.getUrl()); // Missing property should remain null
    assertEquals(null, subConfig.getApiKey()); // Missing property should remain null
    assertEquals(null, subConfig.getAdditionalProperty()); // Missing property in parent class
  }

  @Getter
  class SuperConfig {

    @PropertyKey(key = "app.config.additionalProperty")
    private String additionalProperty;
  }

  @Getter
  class SubConfig extends SuperConfig {

    @PropertyKey(key = "app.config.timeout")
    private int timeout;

    @PropertyKey(key = "app.config.url")
    private String url;

    @PropertyKey(key = "app.config.apiKey")
    private String apiKey;
  }
}
