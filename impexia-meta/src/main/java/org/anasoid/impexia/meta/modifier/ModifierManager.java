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

package org.anasoid.impexia.meta.modifier;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.anasoid.impexia.meta.modifier.Modifier.ModifierBuilder;

/**
 * Modifier Manager Register Known list of modifier, modifier also can have scope, GLOBAL for global
 * modifier , same modifier can be restricted to scope like JPA, SQL ...
 */
public final class ModifierManager {

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private static final Map<String, Modifier> MODIFIERS = new HashMap<>();

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private static final Map<String, Map<String, Modifier>> MODIFIERS_BY_SCOPE = new HashMap<>();

  private ModifierManager() {
    init();
  }

  private static final class LazyHolder {

    static final ModifierManager INSTANCE = new ModifierManager(); // NOPMD
  }

  /**
   * get Instance of ModifierManager.
   *
   * @return singleton instance Of modifier Manager.
   */
  public static ModifierManager getInstance() {
    return LazyHolder.INSTANCE;
  }

  @SuppressWarnings("PMD.ExcessiveMethodLength")
  private static void init() {
    // batchmode
    register(ModifierEnumGlobal.BATCHMODE);
    register(ModifierEnumGlobal.LISTENER);
    register(ModifierEnumGlobal.ERRORHANDLER);
    register(ModifierEnumGlobal.CELLDECORATOR);
    register(ModifierEnumGlobal.TRANSLATOR);
    register(ModifierEnumGlobal.COLLECTIONDELIMITER);
    register(ModifierEnumGlobal.KEY2VALUEDELIMITER);
    register(ModifierEnumGlobal.NUMBERFORMAT);
    register(ModifierEnumGlobal.DATEFORMAT);
    register(ModifierEnumGlobal.PATHDELIMITER);
    register(ModifierEnumGlobal.UNIQUE);
    register(ModifierEnumGlobal.MANDATORY);
    register(ModifierEnumGlobal.IGNORE_NULL);
    register(ModifierEnumGlobal.MODE);
    register(ModifierEnumGlobal.VIRTUAL);
    register(ModifierEnumGlobal.DEFAULT);
  }

  private static void register(ModifierEnum modifierEnum) {
    ModifierBuilder builder =
        Modifier.builder(modifierEnum, modifierEnum.getScope())
            .levels(modifierEnum.getLevels())
            .modes(modifierEnum.getModes())
            .basicTypes(modifierEnum.getBasicTypes())
            .groupTypes(modifierEnum.getGroupTypes())
            .actions(modifierEnum.getActions())
            .values(modifierEnum.getValues())
            .clazz(modifierEnum.getClazz())
            .needMapping(modifierEnum.isNeedMapping());

    register(builder.build());
  }

  private static void register(Modifier modifier) {

    String codeKey = modifier.getCode().toLowerCase(Locale.ROOT);
    String scopeKey = modifier.getScope().toLowerCase(Locale.ROOT);
    if (!MODIFIERS_BY_SCOPE.containsKey(scopeKey)) {
      MODIFIERS_BY_SCOPE.put(scopeKey, new HashMap<>());
    }
    Modifier modifierOldValue = MODIFIERS.put(codeKey, modifier);
    if (modifierOldValue != null) {
      throw new IllegalStateException(
          MessageFormat.format(
              "modifier confilict between (({0})) and (({1})) ", modifierOldValue, modifier));
    }

    MODIFIERS_BY_SCOPE.get(scopeKey).put(codeKey, modifier);
  }

  /**
   * Get enum value by code.
   *
   * @param code code
   * @return enum value
   * @throws IllegalArgumentException throw if no modifier found by code
   */
  public Modifier getValueByCode(String code) {

    Modifier modifier = MODIFIERS.get(code.toLowerCase(Locale.ROOT));

    if (modifier != null) {
      return modifier;
    }

    throw new IllegalArgumentException("Modifier (" + code + ") not found");
  }

  /**
   * Get map modifier value by scope.
   *
   * @param scope code
   * @return enum value
   * @throws IllegalArgumentException throw if no scope found by code
   */
  public Map<String, Modifier> getValuesByScope(String scope) {

    Map<String, Modifier> modifiers = MODIFIERS_BY_SCOPE.get(scope.toLowerCase(Locale.ROOT));

    if (modifiers != null) {
      return modifiers;
    }

    throw new IllegalArgumentException("Modifier scope (" + scope + ") not found");
  }
}
