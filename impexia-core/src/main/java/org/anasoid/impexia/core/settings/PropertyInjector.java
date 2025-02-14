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

package org.anasoid.impexia.core.settings;

import java.lang.reflect.Field;
import java.util.Properties;

public final class PropertyInjector {

  private PropertyInjector() {}

  public static void injectProperties(Object target, Properties properties)
      throws IllegalAccessException {
    Class<?> currentClass = target.getClass();
    while (currentClass != null) {
      processClassFields(target, properties, currentClass);
      currentClass = currentClass.getSuperclass();
    }
  }

  private static void processClassFields(
      Object target, Properties properties, Class<?> currentClass) throws IllegalAccessException {
    Field[] fields = currentClass.getDeclaredFields();
    for (Field field : fields) {
      processField(target, properties, field);
    }
  }

  private static void processField(Object target, Properties properties, Field field)
      throws IllegalAccessException {
    if (field.isAnnotationPresent(PropertyKey.class)) {
      PropertyKey propertyKey = field.getAnnotation(PropertyKey.class);
      String key = propertyKey.key();
      String value = properties.getProperty(key);
      if (value != null) {
        setFieldValue(target, field, value);
      }
    }
  }

  @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
  private static void setFieldValue(Object target, Field field, String value)
      throws IllegalAccessException {
    field.setAccessible(true);
    if (field.getType() == int.class) {
      field.setInt(target, Integer.parseInt(value));
    } else if (field.getType() == String.class) {
      field.set(target, value);
    } else if (field.getType() == boolean.class) {
      field.setBoolean(target, Boolean.parseBoolean(value));
    } else {
      throw new UnsupportedOperationException("Unsupported field type: " + field.getType());
    }
  }
}
