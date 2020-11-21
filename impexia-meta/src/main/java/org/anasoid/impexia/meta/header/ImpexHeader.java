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

package org.anasoid.impexia.meta.header;

import java.util.ArrayList;
import java.util.List;

/** Impex Header description container. */
@SuppressWarnings("PMD")
public abstract class ImpexHeader {

  protected String type;
  protected ImpexAction action;
  protected List<ImpexModifier> modifiers = new ArrayList<>();
  protected List<ImpexAttribute> attributes = new ArrayList<>();
  protected List<String> rawListners = new ArrayList<>();
  protected String rawErrorHandler;

  public String getType() {
    return type;
  }

  public ImpexAction getAction() {
    return action;
  }

  public List<ImpexModifier> getModifiers() {
    return modifiers;
  }

  public List<ImpexAttribute> getAttributes() {
    return attributes;
  }

  public List<String> getRawListners() {
    return rawListners;
  }

  public String getRawErrorHandler() {
    return rawErrorHandler;
  }
}
