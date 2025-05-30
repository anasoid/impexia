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
 * Date :   02-Dec-2020
 */

package org.anasoid.impexia.core.parser.header;

import org.apache.commons.lang3.StringUtils;

/** HeaderParser Utils. */
final class HeaderParserUtils {

  private static final char[] RESERVER_FIELD_CHARS = {']', '[', '(', ')', ' '};

  private HeaderParserUtils() {}

  /**
   * validate field chars.
   *
   * @param field field to check.
   * @return true if valid.
   */
  protected static boolean validateField(String field) {
    String cleanField = field.trim();
    if (StringUtils.isBlank(field)) {
      return false;
    }
    for (char c : RESERVER_FIELD_CHARS) {
      if (cleanField.indexOf(c) >= 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * validate key.
   *
   * @param key field to check.
   * @return true if valid.
   */
  protected static boolean validateKey(String key) {

    return StringUtils.isAlpha(key) && StringUtils.isNotBlank(key);
  }
}
