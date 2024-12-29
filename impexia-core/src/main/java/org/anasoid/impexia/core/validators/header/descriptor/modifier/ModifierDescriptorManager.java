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
 * Date :   09-Dec-2020
 */

package org.anasoid.impexia.core.validators.header.descriptor.modifier;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptor.ModifierDescriptorBuilder;
import org.anasoid.impexia.meta.Scope;

/**
 * Modifier Manager Register Known list of modifier, modifier also can have scope, GLOBAL for global
 * modifier , same modifier can be restricted to scope like JPA, SQL ...
 */
public final class ModifierDescriptorManager {

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private static final Map<String, ModifierDescriptor> MODIFIERS = new HashMap<>();

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private static final Map<String, Map<String, ModifierDescriptor>> MODIFIERS_BY_SCOPE =
      new HashMap<>();

  private ModifierDescriptorManager() {
    init();
  }

  /**
   * get Instance of ModifierManager.
   *
   * @return singleton instance Of modifier Manager.
   */
  public static ModifierDescriptorManager getInstance() {
    return LazyHolder.INSTANCE;
  }

  @SuppressWarnings("PMD.ExcessiveMethodLength")
  private static void init() {
    // batchmode
    register(ModifierDescriptorEnumGlobal.BATCHMODE);
    register(ModifierDescriptorEnumGlobal.LISTENER);
    register(ModifierDescriptorEnumGlobal.ERRORHANDLER);
    register(ModifierDescriptorEnumGlobal.CELLDECORATOR);
    register(ModifierDescriptorEnumGlobal.TRANSLATOR);
    register(ModifierDescriptorEnumGlobal.COLLECTIONDELIMITER);
    register(ModifierDescriptorEnumGlobal.KEY2VALUEDELIMITER);
    register(ModifierDescriptorEnumGlobal.NUMBERFORMAT);
    register(ModifierDescriptorEnumGlobal.DATEFORMAT);
    register(ModifierDescriptorEnumGlobal.PATHDELIMITER);
    register(ModifierDescriptorEnumGlobal.UNIQUE);
    register(ModifierDescriptorEnumGlobal.MANDATORY);
    register(ModifierDescriptorEnumGlobal.IGNORE_NULL);
    register(ModifierDescriptorEnumGlobal.MODE);
    register(ModifierDescriptorEnumGlobal.VIRTUAL);
    register(ModifierDescriptorEnumGlobal.DEFAULT);
  }

  private static void register(ModifierDescriptorEnum modifierDescriptorEnum) {
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

  private static void register(ModifierDescriptor modifierDescriptor) {

    String codeKey = modifierDescriptor.getCode().toLowerCase(Locale.ROOT);
    String scopeKey = modifierDescriptor.getScope().getName();

    if (!MODIFIERS_BY_SCOPE.containsKey(scopeKey)) {
      MODIFIERS_BY_SCOPE.put(scopeKey, new HashMap<>());
    }
    ModifierDescriptor modifierDescriptorOldValue = MODIFIERS.put(codeKey, modifierDescriptor);
    if (modifierDescriptorOldValue != null) {
      throw new IllegalStateException(
          MessageFormat.format(
              "modifier conflict between (({0})) and (({1})) ",
              modifierDescriptorOldValue, modifierDescriptor));
    }

    MODIFIERS_BY_SCOPE.get(scopeKey).put(codeKey, modifierDescriptor);
  }

  /**
   * Get enum value by code.
   *
   * @param code code
   * @return enum value
   * @throws IllegalArgumentException throw if no modifier found by code
   */
  public ModifierDescriptor getValueByCode(String code) {

    ModifierDescriptor modifierDescriptor = MODIFIERS.get(code.toLowerCase(Locale.ROOT));
    return modifierDescriptor;
  }

  /**
   * Get map modifier value by scope.
   *
   * @param scope code
   * @return enum value
   * @throws IllegalArgumentException throw if no scope found by code
   */
  public Map<String, ModifierDescriptor> getValuesByScope(Scope scope) {

    Map<String, ModifierDescriptor> modifiers = MODIFIERS_BY_SCOPE.get(scope.getName());

    if (modifiers != null) {
      return modifiers;
    }

    throw new IllegalArgumentException("Modifier scope (" + scope + ") not found");
  }

  private static final class LazyHolder {

    static final ModifierDescriptorManager INSTANCE = new ModifierDescriptorManager(); // NOPMD
  }
}
