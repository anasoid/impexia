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
 * Date :   03-Nov-2020
 */

package org.anasoid.impexia.core.meta.header;

import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.anasoid.impexia.meta.modifier.Modifier;

/** Default Impex Modifier description container. */
public class DefaultImpexModifier extends ImpexModifier {

  /**
   * constructors.
   *
   * @param key key
   * @param value value
   */
  public DefaultImpexModifier(String key, String value) {
    this.key = key;
    this.value = value;
  }

  /**
   * constructors.
   *
   * @param key key
   * @param value value
   * @param modifier Modifier , null if not standard modifier.
   */
  public DefaultImpexModifier(String key, String value, Modifier modifier) {
    this.key = key;
    this.value = value;
    this.modifier = modifier;
  }

  protected void setAttribute(ImpexAttribute attribute) {
    this.attribute = attribute;
  }

  protected void setHeader(ImpexHeader header) {
    this.header = header;
  }

  @Override
  public String toString() {
    return "DefaultImpexModifier{"
        + "key='"
        + key
        + '\''
        + ", value='"
        + value
        + '\''
        + (modifier != null ? ", modifier=" + modifier.toString() : "")
        + '}';
  }
}
