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

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.Scope;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.anasoid.impexia.meta.transformer.ImpexHandler;

/** Modifiers container. */
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true)
public class ModifierDescriptor {

  @Getter private final Set<ImpexAction> actions;
  @Getter private final Class<?> clazz;

  /**
   * Modifier Manager Register Known list of modifier, modifier also can have scope, GLOBAL for
   * global same modifier can be restricted to scope like JPA, SQL.
   */
  @Getter @ToString.Include private String code;

  @Getter @ToString.Include private final Scope scope;
  @Getter @Singular private Set<Mode> modes;
  @Getter @Singular private Set<Level> levels;
  @Getter @Singular private Set<BasicType> basicTypes;
  @Getter @Singular private Set<GroupType> groupTypes;
  @Getter @Singular private Set<String> values;

  public static ModifierDescriptor.ModifierDescriptorBuilder builder(ModifierDescriptorEnum code) {
    return new ModifierDescriptor.ModifierDescriptorBuilderImpl().code(code);
  }

  public static ModifierDescriptor.ModifierDescriptorBuilder builder(
      ModifierDescriptorEnum code, Scope scope) {
    return builder(code).scope(scope);
  }

  public boolean isAcceptCustomAttribute() {
    return clazz != null && ImpexHandler.class.isAssignableFrom(clazz);
  }

  @SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
  public abstract static class ModifierDescriptorBuilder<
      C extends ModifierDescriptor, B extends ModifierDescriptorBuilder<C, B>> {

    public B code(String code) {
      this.code = code;
      return self();
    }

    public B code(ModifierDescriptorEnum code) {
      return code(code.toString().toLowerCase(Locale.ROOT));
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return Objects.equals(code, ((ModifierDescriptor) o).code);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(code);
  }
}
