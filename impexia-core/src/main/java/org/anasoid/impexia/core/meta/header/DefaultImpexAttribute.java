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
import java.util.List;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexMapping;
import org.anasoid.impexia.meta.header.ImpexModifier;

public class DefaultImpexAttribute extends ImpexAttribute {

  public DefaultImpexAttribute(String field, List<ImpexMapping> mappings) {
    this.field = field;
    this.mappings = mappings;
  }

  public DefaultImpexAttribute addModifier(ImpexModifier modifier) {
    this.modifiers.add(modifier);
    return this;
  }

  public DefaultImpexAttribute addModifier(Collection<ImpexModifier> modifiers) {
    this.modifiers.addAll(modifiers);
    return this;
  }

  public void setSpecial(boolean special) {
    this.special = special;
  }

  @Override
  public String toString() {
    return "DefaultImpexAttribute{"
        + "field='"
        + field
        + '\''
        + (!mappings.isEmpty() ? ", mappings=" + mappings.toString() : "")
        + ", special="
        + special
        + (!modifiers.isEmpty() ? ", modifier=" + modifiers.toString() : "")
        + '}';
  }
}
