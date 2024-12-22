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

import static java.util.Set.of;
import static org.anasoid.impexia.meta.Mode.*;
import static org.anasoid.impexia.meta.header.ImpexAction.*;
import static org.anasoid.impexia.meta.modifier.BasicType.*;
import static org.anasoid.impexia.meta.modifier.GroupType.*;
import static org.anasoid.impexia.meta.modifier.Level.*;

import java.util.Set;
import lombok.Getter;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.anasoid.impexia.meta.transformer.CellDecorator;
import org.anasoid.impexia.meta.transformer.ErrorHandler;
import org.anasoid.impexia.meta.transformer.Listener;
import org.anasoid.impexia.meta.transformer.Translator;

/** List of acceptable Modifiers. */
public enum ModifierEnumGlobal implements ModifierEnum {
  BATCHMODE(of(TYPE), of(UPDATE, INSERT_UPDATE), of(IMPORT), Boolean.class),
  LISTENER(of(TYPE), of(), of(IMPORT), Listener.class),
  ERRORHANDLER(of(TYPE), of(), of(IMPORT), ErrorHandler.class),
  CELLDECORATOR(of(FIELD), of(), of(IMPORT, EXPORT), CellDecorator.class),
  TRANSLATOR(of(FIELD), of(), of(IMPORT), Translator.class),
  COLLECTIONDELIMITER(
      of(FIELD, TYPE),
      of(UPDATE, INSERT_UPDATE, INSERT),
      of(),
      String.class,
      of(),
      of(MAP, COLLECTION),
      of()),
  KEY2VALUEDELIMITER(
      of(FIELD, TYPE),
      of(UPDATE, INSERT_UPDATE, INSERT),
      of(),
      String.class,
      of(),
      of(MAP, COLLECTION),
      of()),
  NUMBERFORMAT(of(FIELD, TYPE), of(), of(), String.class, of(NUMBER), of(), of()),
  DATEFORMAT(of(FIELD, TYPE), of(), of(), String.class, of(DATE), of(), of()),
  PATHDELIMITER(of(FIELD, TYPE), of(), of(), String.class),
  UNIQUE(of(FIELD), of(), of(), Boolean.class, of(), of(SINGLE), of(), true),
  MANDATORY(of(FIELD), of(), of(IMPORT), Boolean.class),
  IGNORE_NULL(of(FIELD), of(), of(IMPORT), Boolean.class, of(), of(COLLECTION), of()),
  MODE(
      of(FIELD),
      of(UPDATE, INSERT_UPDATE, INSERT),
      of(IMPORT),
      String.class,
      of(),
      of(MAP, COLLECTION),
      of("append", "remove", "merge")),
  VIRTUAL(of(FIELD), of(UPDATE, INSERT_UPDATE, INSERT), of(IMPORT), Boolean.class),
  DEFAULT(of(FIELD), of(UPDATE, INSERT_UPDATE, INSERT), of(IMPORT), String.class);

  @Getter private final String scope;
  @Getter private final Set<Level> levels;
  @Getter private final Set<ImpexAction> actions;
  @Getter private final Set<Mode> modes;
  @Getter private final Class<?> clazz;

  @Getter private final Set<BasicType> basicTypes;
  @Getter private final Set<GroupType> groupTypes;
  @Getter private final Set<String> values;
  @Getter private final boolean needMapping;

  ModifierEnumGlobal(
      Set<Level> levels,
      Set<ImpexAction> actions,
      Set<Mode> modes,
      Class<?> clazz,
      Set<BasicType> basicTypes,
      Set<GroupType> groupTypes,
      Set<String> values,
      boolean needMapping) {
    this.levels = levels;
    this.actions = actions;
    this.modes = modes;
    this.clazz = clazz;
    this.basicTypes = basicTypes;
    this.groupTypes = groupTypes;
    this.values = values;
    this.needMapping = needMapping;
    this.scope = "GLOBAL";
  }

  ModifierEnumGlobal(
      Set<Level> levels,
      Set<ImpexAction> actions,
      Set<Mode> modes,
      Class<?> clazz,
      Set<BasicType> basicTypes,
      Set<GroupType> groupTypes,
      Set<String> values) {
    this(levels, actions, modes, clazz, basicTypes, groupTypes, values, false);
  }

  ModifierEnumGlobal(Set<Level> levels, Set<ImpexAction> actions, Set<Mode> modes, Class<?> clazz) {
    this(levels, actions, modes, clazz, of(), of(), of(), false);
  }
}
