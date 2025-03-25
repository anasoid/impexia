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

/** Impex Modifier description container. */
@SuperBuilder
@ToString
public class ImpexModifier {

  @Getter private String key;
  @Getter private String value;

  @Setter(AccessLevel.PROTECTED)
  @Getter
  @ToString.Exclude
  private ImpexAttribute attribute;

  @Setter(AccessLevel.PROTECTED)
  @Getter
  @ToString.Exclude
  private ImpexHeader header;

  public static ImpexModifier.ImpexModifierBuilder builder(String key) {
    return new ImpexModifier.ImpexModifierBuilderImpl().key(key);
  }

  public static ImpexModifier.ImpexModifierBuilder builder(String key, String value) {
    return builder(key).value(value);
  }
}
