/*
 * Copyright 2020-2025 the original author or authors.
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
 * Date :   23-Mar-2025
 */

package org.anasoid.impexia.core.utils;

import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnum;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnumGlobal;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;

public final class ImpexModifierUtils {

  private ImpexModifierUtils() {}

  public static boolean isBatchMode(ImpexHeader impexHeader) {
    return Boolean.valueOf(valueOf(impexHeader, ModifierDescriptorEnumGlobal.BATCHMODE));
  }

  public static String valueOf(ImpexHeader impexHeader, ModifierDescriptorEnum modifierDescriptor) {
    ImpexModifier result =
        impexHeader.getModifiers().stream()
            .filter(m -> m.getKey().equalsIgnoreCase(modifierDescriptor.toString()))
            .findFirst()
            .orElse(null);

    return result == null ? null : result.getValue();
  }

  public static String valueOf(
      ImpexAttribute impexAttribute, ModifierDescriptorEnum modifierDescriptor) {
    ImpexModifier result =
        impexAttribute.getModifiers().stream()
            .filter(m -> m.getKey().equalsIgnoreCase(modifierDescriptor.toString()))
            .findFirst()
            .orElse(null);

    return result == null ? null : result.getValue();
  }
}
