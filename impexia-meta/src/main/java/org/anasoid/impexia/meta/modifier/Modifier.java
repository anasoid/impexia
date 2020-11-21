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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.transformer.Decorator;
import org.anasoid.impexia.meta.transformer.ErrorHandler;
import org.anasoid.impexia.meta.transformer.Listner;
import org.anasoid.impexia.meta.transformer.Translator;

/** List of acceptable Modifiers. */
public enum Modifier {
  BATCHMODE("batchmode", group(Mode.EXPORT), group(Level.TYPE), true),
  LISTNER("listner", group(Mode.IMPORT), group(Level.TYPE), Listner.class),
  ERROR_HANDLER("errorHandler", group(Mode.IMPORT), group(Level.TYPE), ErrorHandler.class),
  CELL_DECORATOR(
      "cellDecorator",
      group(Mode.EXPORT, Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.values()),
      Decorator.class),
  TRANSLATOR(
      "translator",
      group(Mode.EXPORT, Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.values()),
      Translator.class),
  COLLECTION_DELIMITER(
      "collection-delimiter",
      group(Mode.EXPORT, Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.COLLECTION, GroupType.MAP),
      false),
  KEY2VALUE_DELIMITER(
      "key2value-delimiter",
      group(Mode.EXPORT, Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.COLLECTION, GroupType.MAP),
      false),
  NUMBER_FORMAT(
      "numberformat",
      group(Mode.EXPORT, Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.NUMBER),
      group(GroupType.values()),
      false),
  DATE_FORMAT(
      "dateformat",
      group(Mode.EXPORT, Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.DATE),
      group(GroupType.values()),
      false),
  PATH_DELIMITER(
      "path-delimiter",
      group(Mode.EXPORT, Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.values()),
      false),
  UNIQUE(
      "unique",
      group(Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.SINGLE),
      true),
  MANDATORY(
      "mandatory",
      group(Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.values()),
      true),
  IGNORE_NULL(
      "ignorenull",
      group(Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.COMPOSED),
      group(GroupType.values()),
      true),
  MODE(
      "mode",
      group(Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.COLLECTION, GroupType.MAP),
      false),
  VIRTUAL(
      "virtual",
      group(Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.values()),
      true),
  DEFAULT(
      "default",
      group(Mode.EXPORT, Mode.IMPORT),
      group(Level.FIELD),
      group(BasicType.values()),
      group(GroupType.values()),
      false);

  Modifier(
      String code,
      Set<Mode> modes,
      Set<Level> levels,
      Set<BasicType> basicTypes,
      Set<GroupType> groupTypes,
      Boolean isBoolean) {
    this.code = code;
    this.modes = modes;
    this.levels = levels;
    this.basicTypes = basicTypes;
    this.groupTypes = groupTypes;
    this.isBoolean = isBoolean;
  }

  Modifier(String code, Set<Mode> modes, Set<Level> levels, Boolean isBoolean) {
    this.code = code;
    this.modes = modes;
    this.levels = levels;
    this.isBoolean = isBoolean;
  }

  Modifier(String code, Set<Mode> modes, Set<Level> levels, Class<?> clazz) {
    this.code = code;
    this.modes = modes;
    this.levels = levels;
    this.clazz = clazz;
  }

  Modifier(
      String code,
      Set<Mode> modes,
      Set<Level> levels,
      Set<BasicType> basicTypes,
      Set<GroupType> groupTypes,
      Class<?> clazz) {
    this.code = code;
    this.modes = modes;
    this.levels = levels;
    this.basicTypes = basicTypes;
    this.groupTypes = groupTypes;
    this.clazz = clazz;
  }

  private final String code;
  private final Set<Mode> modes;
  private final Set<Level> levels;
  private Set<BasicType> basicTypes;
  private Set<GroupType> groupTypes;
  private Class<?> clazz;
  private boolean isBoolean;

  public String getCode() {
    return code;
  }

  public boolean isBoolean() {
    return isBoolean;
  }

  public Set<Mode> getModes() {
    return modes;
  }

  public Set<GroupType> getGroupTypes() {
    return groupTypes;
  }

  public Set<BasicType> getBasicTypes() {
    return basicTypes;
  }

  public Set<Level> getLevels() {
    return levels;
  }

  public boolean isAcceptCustomAttibute() {
    return clazz != null;
  }

  private static <T> Set<T> group(T... t) {
    return new HashSet<>(Arrays.asList(t));
  }

  /**
   * Get enum value by code.
   *
   * @param code code
   * @return enum value
   * @throws IllegalArgumentException throw if no enum found by code
   */
  public static Modifier valueByCode(String code) {
    Modifier[] modifiers = Modifier.values();
    for (Modifier modifier: modifiers) {
      if (modifier.getCode().equals(code)) {
        return modifier;
      }
    }
    throw new IllegalArgumentException("Modifier (" + code + ") not found");
  }
}
