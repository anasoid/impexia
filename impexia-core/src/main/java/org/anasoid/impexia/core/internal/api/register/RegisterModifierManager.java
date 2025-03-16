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
 * Date :   01-Jan-2025
 */

package org.anasoid.impexia.core.internal.api.register;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.anasoid.impexia.core.register.RegistratorManager;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptor;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptor.ModifierDescriptorBuilder;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnum;
import org.anasoid.impexia.meta.Scope;

public class RegisterModifierManager implements RegistratorManager<ModifierDescriptorEnum> {

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private final Map<String, ModifierDescriptor> registeredModifiers = new HashMap<>();

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private final Map<String, Map<String, ModifierDescriptor>> modifiersByScope = new HashMap<>();

  public static RegisterModifierManager getInstance() {
    return RegisterModifierManager.LazyHolder.INSTANCE;
  }

  private static final class LazyHolder {

    static final RegisterModifierManager INSTANCE = new RegisterModifierManager(); // NOPMD
  }

  @Override
  public void register(ModifierDescriptorEnum modifierDescriptorEnum) {
    ModifierDescriptorBuilder<?, ?> builder =
        ModifierDescriptor.builder(modifierDescriptorEnum, modifierDescriptorEnum.getScope())
            .levels(modifierDescriptorEnum.getLevels())
            .modes(modifierDescriptorEnum.getModes())
            .basicTypes(modifierDescriptorEnum.getBasicTypes())
            .groupTypes(modifierDescriptorEnum.getGroupTypes())
            .actions(modifierDescriptorEnum.getActions())
            .values(modifierDescriptorEnum.getValues())
            .clazz(modifierDescriptorEnum.getClazz());

    register(builder.build());
  }

  private void register(ModifierDescriptor modifierDescriptor) {

    String codeKey = modifierDescriptor.getCode().toLowerCase(Locale.ROOT);
    String scopeKey = modifierDescriptor.getScope().getName();

    modifiersByScope.computeIfAbsent(scopeKey, s -> new HashMap<>());

    ModifierDescriptor modifierDescriptorOldValue =
        registeredModifiers.put(codeKey, modifierDescriptor);
    if (modifierDescriptorOldValue != null) {
      throw new IllegalStateException(
          MessageFormat.format(
              "modifier conflict between (({0})) and (({1})) ",
              modifierDescriptorOldValue, modifierDescriptor));
    }

    modifiersByScope.get(scopeKey).put(codeKey, modifierDescriptor);
  }

  /**
   * Get enum value by code.
   *
   * @param code code
   * @return enum value
   * @throws IllegalArgumentException throw if no modifier found by code
   */
  public ModifierDescriptor getValueByCode(String code) {
    return registeredModifiers.get(code.toLowerCase(Locale.ROOT));
  }

  /**
   * Get map modifier value by scope.
   *
   * @param scope code
   * @return enum value
   * @throws IllegalArgumentException throw if no scope found by code
   */
  public Map<String, ModifierDescriptor> getValuesByScope(Scope scope) {

    Map<String, ModifierDescriptor> modifiers = modifiersByScope.get(scope.getName());

    if (modifiers != null) {
      return modifiers;
    }

    throw new IllegalArgumentException("Modifier scope (" + scope + ") not found");
  }

  void unload() {
    registeredModifiers.clear();
    modifiersByScope.clear();
  }

  public static class UnLoader {

    public void unload() {
      RegisterModifierManager.getInstance().unload();
    }
  }
}
