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
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.transformer.Decorator;
import org.anasoid.impexia.meta.transformer.ErrorHandler;
import org.anasoid.impexia.meta.transformer.Listner;
import org.anasoid.impexia.meta.transformer.Translator;

/**
 * Modifier Manager Register Known list of modifier, moifier also can have scope, GLOBAL for global
 * modifier , same modifier can be restricted to scope like JPA, SQL ...
 */
public class ModifierManager {

  private static final String DEFAULT_SCOPE = "GLOBAL";

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private static final Map<String, Modifier> MODIFIERS = new HashMap<>();

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private static final Map<String, Map<String, Modifier>> MODIFIERS_BY_SCOPE = new HashMap<>();

  private ModifierManager() {
    init();
  }

  private static class LazyHolder {
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
    register(
        new ModifierBuilder(
                ModifierEnum.BATCHMODE.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setModes(Mode.IMPORT)
            .setLevels(Level.TYPE)
            .setValues(Modifier.BOOLEAN_VALUES)
            .build());
    // listner
    register(
        new ModifierBuilder(ModifierEnum.LISTNER.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setModes(Mode.IMPORT)
            .setLevels(Level.TYPE)
            .setClazz(Listner.class)
            .build());
    // errorHandler
    register(
        new ModifierBuilder(
                ModifierEnum.ERRORHANDLER.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setModes(Mode.IMPORT)
            .setLevels(Level.TYPE)
            .setClazz(ErrorHandler.class)
            .build());
    // cellDecorator
    register(
        new ModifierBuilder(
                ModifierEnum.CELLDECORATOR.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setLevels(Level.FIELD)
            .setClazz(Decorator.class)
            .build());
    // translator
    register(
        new ModifierBuilder(
                ModifierEnum.TRANSLATOR.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setLevels(Level.FIELD)
            .setClazz(Translator.class)
            .build());
    // collection-delimiter
    register(
        new ModifierBuilder(
                ModifierEnum.COLLECTIONDELIMITER.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setLevels(Level.FIELD)
            .setGroupTypes(GroupType.COLLECTION, GroupType.MAP)
            .build());
    // key2value-delimiter
    register(
        new ModifierBuilder(
                ModifierEnum.KEY2VALUEDELIMITER.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setLevels(Level.FIELD)
            .setGroupTypes(GroupType.COLLECTION, GroupType.MAP)
            .build());
    // numberformat
    register(
        new ModifierBuilder(
                ModifierEnum.NUMBERFORMAT.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setLevels(Level.FIELD)
            .setBasicTypes(BasicType.NUMBER)
            .build());
    // dateformat
    register(
        new ModifierBuilder(
                ModifierEnum.DATEFORMAT.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setLevels(Level.FIELD)
            .setBasicTypes(BasicType.DATE)
            .build());
    // path-delimiter
    register(
        new ModifierBuilder(
                ModifierEnum.PATHDELIMITER.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setLevels(Level.FIELD)
            .build());
    // unique
    register(
        new ModifierBuilder(ModifierEnum.UNIQUE.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setLevels(Level.FIELD)
            .setValues(Modifier.BOOLEAN_VALUES)
            .build());

    // mandatory
    register(
        new ModifierBuilder(
                ModifierEnum.MANDATORY.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setModes(Mode.IMPORT)
            .setLevels(Level.FIELD)
            .setValues(Modifier.BOOLEAN_VALUES)
            .build());
    // ignorenull
    register(
        new ModifierBuilder(
                ModifierEnum.IGNORE_NULL.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setModes(Mode.IMPORT)
            .setLevels(Level.FIELD)
            .setValues(Modifier.BOOLEAN_VALUES)
            .build());
    // mode
    register(
        new ModifierBuilder(ModifierEnum.MODE.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setModes(Mode.IMPORT)
            .setLevels(Level.FIELD)
            .setGroupTypes(GroupType.COLLECTION, GroupType.MAP)
            .setValues("append", "remove")
            .build());
    // virtual
    register(
        new ModifierBuilder(ModifierEnum.VIRTUAL.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setModes(Mode.IMPORT)
            .setLevels(Level.FIELD)
            .setValues(Modifier.BOOLEAN_VALUES)
            .build());

    // default
    register(
        new ModifierBuilder(ModifierEnum.DEFAULT.toString().toLowerCase(Locale.ROOT), DEFAULT_SCOPE)
            .setModes(Mode.IMPORT)
            .setLevels(Level.FIELD)
            .build());
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
