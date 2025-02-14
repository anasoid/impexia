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

public class PropertyInjector {

  // Method to inject properties into fields from the current class and its superclasses
  public static void injectProperties(Object target, Properties properties)
      throws IllegalAccessException {
    // Start with the class of the target object
    Class<?> currentClass = target.getClass();

    // Loop through all classes in the inheritance hierarchy
    while (currentClass != null) {
      // Get all declared fields in the current class (including private fields)
      Field[] fields = currentClass.getDeclaredFields();

      // Loop through each field in the class
      for (Field field : fields) {
        // Check if the field has the PropertyKey annotation
        if (field.isAnnotationPresent(PropertyKey.class)) {
          // Get the PropertyKey annotation
          PropertyKey propertyKey = field.getAnnotation(PropertyKey.class);

          // Get the key from the annotation
          String key = propertyKey.key();

          // Get the value from the properties object
          String value = properties.getProperty(key);

          if (value != null) {
            // Make the field accessible if it's private
            field.setAccessible(true);

            // Set the value based on the field's type
            if (field.getType() == int.class) {
              field.setInt(target, Integer.parseInt(value)); // Assuming it's an int
            } else if (field.getType() == String.class) {
              field.set(target, value); // If it's a String
            }
            // Add more type checks here for other field types as needed
          }
        }
      }

      // Move to the superclass of the current class
      currentClass = currentClass.getSuperclass();
    }
  }
}
