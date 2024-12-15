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
import org.anasoid.impexia.meta.transformer.Listener;
import org.anasoid.impexia.meta.transformer.Translator;

/**
 * Modifier Manager Register Known list of modifier, modifier also can have scope, GLOBAL for global
 * modifier , same modifier can be restricted to scope like JPA, SQL ...
 */
public final class ModifierManager {

  private static final String DEFAULT_SCOPE = "GLOBAL";

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
    register(
        Modifier.builder(ModifierEnum.BATCHMODE, DEFAULT_SCOPE)
            .mode(Mode.IMPORT)
            .level(Level.TYPE)
            .values(Modifier.BOOLEAN_VALUES));
    // listner
    register(
        Modifier.builder(ModifierEnum.LISTNER, DEFAULT_SCOPE)
            .mode(Mode.IMPORT)
            .level(Level.TYPE)
            .clazz(Listener.class));

    // errorHandler
    register(
        Modifier.builder(ModifierEnum.ERRORHANDLER, DEFAULT_SCOPE)
            .mode(Mode.IMPORT)
            .level(Level.TYPE)
            .clazz(ErrorHandler.class));
    // cellDecorator
    register(
        Modifier.builder(ModifierEnum.CELLDECORATOR, DEFAULT_SCOPE)
            .level(Level.FIELD)
            .clazz(Decorator.class));

    // translator
    register(
        Modifier.builder(ModifierEnum.TRANSLATOR, DEFAULT_SCOPE)
            .level(Level.FIELD)
            .clazz(Translator.class));
    // collection-delimiter
    register(
        Modifier.builder(ModifierEnum.COLLECTIONDELIMITER, DEFAULT_SCOPE)
            .level(Level.FIELD)
            .groupType(GroupType.COLLECTION)
            .groupType(GroupType.MAP));

    // key2value-delimiter
    register(
        Modifier.builder(ModifierEnum.KEY2VALUEDELIMITER, DEFAULT_SCOPE)
            .level(Level.FIELD)
            .groupType(GroupType.COLLECTION)
            .groupType(GroupType.MAP));

    // numberformat
    register(
        Modifier.builder(ModifierEnum.NUMBERFORMAT, DEFAULT_SCOPE)
            .level(Level.FIELD)
            .basicType(BasicType.NUMBER));

    // dateformat
    register(
        Modifier.builder(ModifierEnum.DATEFORMAT, DEFAULT_SCOPE)
            .level(Level.FIELD)
            .basicType(BasicType.DATE));

    // path-delimiter
    register(Modifier.builder(ModifierEnum.PATHDELIMITER, DEFAULT_SCOPE).level(Level.FIELD));

    // unique
    register(
        Modifier.builder(ModifierEnum.UNIQUE, DEFAULT_SCOPE)
            .level(Level.FIELD)
            .values(Modifier.BOOLEAN_VALUES)
            .needMapping(true));

    // mandatory
    register(
        Modifier.builder(ModifierEnum.MANDATORY, DEFAULT_SCOPE)
            .mode(Mode.IMPORT)
            .level(Level.FIELD)
            .values(Modifier.BOOLEAN_VALUES));

    // ignorenull
    register(
        Modifier.builder(ModifierEnum.IGNORE_NULL, DEFAULT_SCOPE)
            .mode(Mode.IMPORT)
            .level(Level.FIELD)
            .values(Modifier.BOOLEAN_VALUES));

    // mode
    register(
        Modifier.builder(ModifierEnum.MODE, DEFAULT_SCOPE)
            .mode(Mode.IMPORT)
            .level(Level.FIELD)
            .groupType(GroupType.COLLECTION)
            .groupType(GroupType.MAP)
            .value("append")
            .value("remove"));

    // virtual
    register(
        Modifier.builder(ModifierEnum.VIRTUAL, DEFAULT_SCOPE)
            .mode(Mode.IMPORT)
            .level(Level.FIELD)
            .values(Modifier.BOOLEAN_VALUES));

    // default
    register(
        Modifier.builder(ModifierEnum.DEFAULT, DEFAULT_SCOPE).mode(Mode.IMPORT).level(Level.FIELD));
  }

  private static void register(Modifier.ModifierBuilder builder) {
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
