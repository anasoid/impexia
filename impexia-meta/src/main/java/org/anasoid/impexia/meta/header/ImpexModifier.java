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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.anasoid.impexia.meta.modifier.ModifierDescriptor;
import org.anasoid.impexia.meta.modifier.ModifierDescriptorEnum;

/** Impex Modifier description container. */
@SuperBuilder
@ToString
public class ImpexModifier {

  @Getter protected String key;
  @Getter protected String value;
  @ToString.Exclude @Getter protected ModifierDescriptor modifierDescriptor;

  @Setter(AccessLevel.PROTECTED)
  @Getter
  @ToString.Exclude
  protected ImpexAttribute attribute;

  @Setter(AccessLevel.PROTECTED)
  @Getter
  @ToString.Exclude
  protected ImpexHeader header;

  public static ImpexModifier.ImpexModifierBuilder builder(String key) {
    return new ImpexModifier.ImpexModifierBuilderImpl().key(key);
  }

  public static ImpexModifier.ImpexModifierBuilder builder(String key, String value) {
    return builder(key).value(value);
  }

  public static ImpexModifier.ImpexModifierBuilder builder(ModifierDescriptorEnum key) {
    return builder(key.toString());
  }

  public static ImpexModifier.ImpexModifierBuilder builder(
      ModifierDescriptorEnum key, String value) {
    return builder(key).value(value);
  }
}
