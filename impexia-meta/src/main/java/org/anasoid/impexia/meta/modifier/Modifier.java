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
 * Date :   07-Nov-2020
 */

package org.anasoid.impexia.meta.modifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.anasoid.impexia.meta.Mode;

/** Modifiers container. */
public class Modifier {

  public static final Set<String> BOOLEAN_VALUES =
      Collections.unmodifiableSet(new HashSet<>(Arrays.asList("true", "false")));
  private final String code;
  private final Set<Mode> modes;
  private final Set<Level> levels;
  private final Set<BasicType> basicTypes;
  private final Set<GroupType> groupTypes;
  private final Class<?> clazz;
  private final Set<String> values;
  private final String scope;

  /**
   * Default constructor.
   *
   * @see ModifierBuilder
   */
  Modifier(
      String code,
      Set<Mode> modes,
      Set<Level> levels,
      Set<BasicType> basicTypes,
      Set<GroupType> groupTypes,
      Class<?> clazz,
      Set<String> values,
      String scope) {
    this.code = code;
    this.modes = modes;
    this.levels = levels;
    this.basicTypes = basicTypes;
    this.groupTypes = groupTypes;
    this.clazz = clazz;
    this.values = values;
    this.scope = scope;
  }

  public String getCode() {
    return code;
  }

  public Set<Mode> getModes() {
    return modes;
  }

  public Set<GroupType> getGroupTypes() {
    return groupTypes;
  }

  public Set<BasicType> getBasicTypes() {
    return basicTypes;
  }

  public Set<Level> getLevels() {
    return levels;
  }

  public boolean isAcceptCustomAttribute() {
    return clazz != null;
  }

  public Set<String> getValues() {
    return values;
  }

  public String getScope() {
    return scope;
  }

  @Override
  public String toString() {
    return "Modifier{" + "code='" + code + '\'' + ", scope='" + scope + '\'' + '}';
  }
}
