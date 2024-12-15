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
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.anasoid.impexia.meta.Mode;

/** Modifiers container. */
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true)
public class Modifier {

  public static final Set<String> BOOLEAN_VALUES =
      Collections.unmodifiableSet(new HashSet<>(Arrays.asList("true", "false")));
  @Getter @ToString.Include private final String code;
  @Getter @Singular private final Set<Mode> modes;
  @Getter @Singular private final Set<Level> levels;
  @Getter @Singular private final Set<BasicType> basicTypes;
  @Getter @Singular private final Set<GroupType> groupTypes;
  private final Class<?> clazz;
  @Getter @Singular private final Set<String> values;
  @Getter private final boolean needMapping;
  @Getter @ToString.Include private final String scope;

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
      boolean needMapping,
      String scope) {
    this.code = code;
    this.modes = modes;
    this.levels = levels;
    this.basicTypes = basicTypes;
    this.groupTypes = groupTypes;
    this.clazz = clazz;
    this.values = values;
    this.needMapping = needMapping;
    this.scope = scope;
  }

  public boolean isAcceptCustomAttribute() {
    return clazz != null;
  }
}
