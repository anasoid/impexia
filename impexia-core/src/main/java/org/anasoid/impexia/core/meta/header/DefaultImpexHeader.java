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

import java.util.Collection;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;

public class DefaultImpexHeader extends ImpexHeader {

  /**
   * Constructors.
   *
   * @param type target type.
   * @param action Impex action.
   */
  public DefaultImpexHeader(String type, ImpexAction action) {
    this.type = type;
    this.action = action;
  }

  /**
   * Add modifier.
   *
   * @param modifier header modifier's.
   * @return this.
   */
  public DefaultImpexHeader addModifier(ImpexModifier modifier) {
    this.modifiers.add(modifier);
    ((DefaultImpexModifier) modifier).setHeader(this);
    return this;
  }

  /**
   * Add modifiers.
   *
   * @param modifiers header modifier's.
   * @return this.
   */
  public DefaultImpexHeader addModifier(Collection<ImpexModifier> modifiers) {
    this.modifiers.addAll(modifiers);
    modifiers.stream().forEach(m -> ((DefaultImpexModifier) m).setHeader(this));
    return this;
  }

  /**
   * Add attribute.
   *
   * @param attribute header header's.
   * @return this.
   */
  public DefaultImpexHeader addAttribute(ImpexAttribute attribute) {
    this.attributes.add(attribute);
    ((DefaultImpexAttribute) attribute).setParent(this);
    return this;
  }

  @Override
  public String toString() {
    return "DefaultImpexHeader{"
        + "type='"
        + type
        + '\''
        + ", action="
        + action
        + (!modifiers.isEmpty() ? ", modifier=" + modifiers.toString() : "")
        + ", attributes="
        + attributes
        + '}';
  }
}
