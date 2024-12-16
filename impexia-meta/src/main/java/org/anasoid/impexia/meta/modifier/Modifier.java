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
import java.util.Locale;
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
  @Getter @ToString.Include private String code;
  @Getter @Singular private Set<Mode> modes;
  @Getter @Singular private Set<Level> levels;
  @Getter @Singular private Set<BasicType> basicTypes;
  @Getter @Singular private Set<GroupType> groupTypes;
  private final Class<?> clazz;
  @Getter @Singular private Set<String> values;
  @Getter private final boolean needMapping;

  /**
   * Modifier Manager Register Known list of modifier, modifier also can have scope, GLOBAL for
   * global same modifier can be restricted to scope like JPA, SQL.
   */
  @Getter @ToString.Include private final String scope;

  public boolean isAcceptCustomAttribute() {
    return clazz != null;
  }

  public static Modifier.ModifierBuilder builder(ModifierEnum code) {
    return new Modifier.ModifierBuilderImpl().code(code);
  }

  public static Modifier.ModifierBuilder builder(ModifierEnum code, String scope) {
    return builder(code).scope(scope);
  }

  @SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
  public abstract static class ModifierBuilder<
      C extends Modifier, B extends ModifierBuilder<C, B>> {

    public B code(String code) {
      this.code = code;
      return self();
    }

    public B code(ModifierEnum code) {
      return code(code.toString().toLowerCase(Locale.ROOT));
    }
  }
}
