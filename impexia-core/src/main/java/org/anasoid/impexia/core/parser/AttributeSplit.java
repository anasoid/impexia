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

package org.anasoid.impexia.core.parser;

import org.anasoid.impexia.meta.header.ImpexAction;

/** Container to split attribute. */
class AttributeSplit {
  private ImpexAction action;
  private String field;
  private String mappings;
  private String modifiers;

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getMappings() {
    return mappings;
  }

  public void setMapping(String mappings) {
    this.mappings = mappings;
  }

  public String getModifiers() {
    return modifiers;
  }

  public void setModifiers(String moifiers) {
    this.modifiers = moifiers;
  }

  public ImpexAction getAction() {
    return action;
  }

  public void setAction(ImpexAction action) {
    this.action = action;
  }
}
