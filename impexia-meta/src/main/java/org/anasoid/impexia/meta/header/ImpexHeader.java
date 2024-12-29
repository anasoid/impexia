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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.anasoid.impexia.meta.modifier.ModifierDescriptor;

/** Impex Header description container. */
@SuperBuilder
@Getter
@ToString
public class ImpexHeader {

  protected String type;
  protected ImpexAction action;
  @Singular protected List<ImpexModifier> modifiers;
  @Singular protected List<ImpexAttribute> attributes;

  public static ImpexHeader.ImpexHeaderBuilder builder(String type, ImpexAction action) {
    return new ImpexHeader.ImpexHeaderBuilderImpl().action(action).type(type);
  }

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  @Default
  protected Map<String, Objects> meta = new HashMap<>();

  private static final class ImpexHeaderBuilderImpl
      extends ImpexHeaderBuilder<ImpexHeader, ImpexHeaderBuilderImpl> {

    public ImpexHeader build() {
      ImpexHeader resul = new ImpexHeader(this);
      resul.getModifiers().stream().forEach(m -> m.setHeader(resul));
      resul.getAttributes().stream().forEach(m -> m.setParent(resul));
      return resul;
    }
  }

  List<ImpexAttribute> getAttributesByModifier(ModifierDescriptor modifierDescriptor) {
    String modifierStr = modifierDescriptor.getCode();
    return attributes.stream()
        .filter(atr -> atr.getModifiers().stream().anyMatch(m -> m.key.equals(modifierStr)))
        .toList();
  }
}
