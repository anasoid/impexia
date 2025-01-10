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

package org.anasoid.impexia.core.validators.header.descriptor.modifier;

import static java.util.Collections.EMPTY_SET;
import static java.util.Set.of;
import static org.anasoid.impexia.core.validators.header.descriptor.modifier.BasicType.*;
import static org.anasoid.impexia.core.validators.header.descriptor.modifier.GroupType.*;
import static org.anasoid.impexia.core.validators.header.descriptor.modifier.Level.*;
import static org.anasoid.impexia.meta.Mode.*;
import static org.anasoid.impexia.meta.header.ImpexAction.*;
import static org.anasoid.impexia.meta.scope.ScopeEnum.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.anasoid.impexia.meta.DataFormat;
import org.anasoid.impexia.meta.Mode;
import org.anasoid.impexia.meta.Scope;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.anasoid.impexia.meta.spi.handler.CellDecorator;
import org.anasoid.impexia.meta.spi.handler.ErrorHandler;
import org.anasoid.impexia.meta.spi.handler.Listener;
import org.anasoid.impexia.meta.spi.handler.Translator;

/** List of acceptable Modifiers. */
public enum ModifierDescriptorEnumGlobal implements ModifierDescriptorEnum {
  BATCHMODE(of(TYPE), of(UPDATE, INSERT_UPDATE), of(IMPORT), Boolean.class, GLOBAL),
  LISTENER(of(TYPE), EMPTY_SET, of(IMPORT), Listener.class, GLOBAL),
  ERRORHANDLER(of(TYPE), EMPTY_SET, of(IMPORT), ErrorHandler.class, GLOBAL),
  CELLDECORATOR(of(FIELD), EMPTY_SET, of(IMPORT, EXPORT), CellDecorator.class, GLOBAL),
  TRANSLATOR(of(FIELD), EMPTY_SET, of(IMPORT), Translator.class, GLOBAL),
  COLLECTIONDELIMITER(
      of(FIELD, TYPE),
      of(UPDATE, INSERT_UPDATE, INSERT),
      EMPTY_SET,
      String.class,
      EMPTY_SET,
      of(MAP, COLLECTION),
      EMPTY_SET,
      ORM),
  KEY2VALUEDELIMITER(
      of(FIELD, TYPE),
      of(UPDATE, INSERT_UPDATE, INSERT),
      EMPTY_SET,
      String.class,
      EMPTY_SET,
      of(MAP, COLLECTION),
      EMPTY_SET,
      ORM),
  NUMBERFORMAT(
      of(FIELD, TYPE),
      EMPTY_SET,
      EMPTY_SET,
      String.class,
      of(NUMBER),
      EMPTY_SET,
      EMPTY_SET,
      GLOBAL),
  DATEFORMAT(
      of(FIELD, TYPE), EMPTY_SET, EMPTY_SET, String.class, of(DATE), EMPTY_SET, EMPTY_SET, GLOBAL),
  PATHDELIMITER(of(FIELD, TYPE), EMPTY_SET, EMPTY_SET, String.class, GLOBAL),
  UNIQUE(of(FIELD), EMPTY_SET, EMPTY_SET, Boolean.class, EMPTY_SET, of(SINGLE), EMPTY_SET, GLOBAL),
  MANDATORY(of(FIELD), EMPTY_SET, of(IMPORT), Boolean.class, GLOBAL),
  IGNORE_NULL(
      of(FIELD),
      EMPTY_SET,
      of(IMPORT),
      Boolean.class,
      EMPTY_SET,
      of(COLLECTION),
      EMPTY_SET,
      GLOBAL),
  MODE(
      of(FIELD),
      of(UPDATE, INSERT_UPDATE, INSERT),
      of(IMPORT),
      String.class,
      EMPTY_SET,
      of(MAP, COLLECTION),
      of("append", "remove", "merge"),
      ORM),
  VIRTUAL(of(FIELD), of(UPDATE, INSERT_UPDATE, INSERT), of(IMPORT), Boolean.class, GLOBAL),
  DEFAULT(of(FIELD), of(UPDATE, INSERT_UPDATE, INSERT), of(IMPORT), String.class, GLOBAL);

  @Getter private final Scope scope;
  @Getter private final Set<Level> levels;
  @Getter private final Set<ImpexAction> actions;
  @Getter private final Set<DataFormat> dataFormats;
  @Getter private final Set<Mode> modes;
  @Getter private final Class<?> clazz;

  @Getter private final Set<BasicType> basicTypes;
  @Getter private final Set<GroupType> groupTypes;
  @Getter private final Set<String> values;

  ModifierDescriptorEnumGlobal(
      Set<Level> levels,
      Set<ImpexAction> actions,
      Set<Mode> modes,
      Set<DataFormat> dataFormats,
      Class<?> clazz,
      Set<BasicType> basicTypes,
      Set<GroupType> groupTypes,
      Set<String> values,
      Scope scope) {
    this.levels = levels;
    this.actions = actions;
    this.modes = modes;
    this.dataFormats = dataFormats;
    this.clazz = clazz;
    this.basicTypes = basicTypes;
    this.groupTypes = groupTypes;
    this.values = values;
    this.scope = scope;
  }

  ModifierDescriptorEnumGlobal(
      Set<Level> levels,
      Set<ImpexAction> actions,
      Set<Mode> modes,
      Class<?> clazz,
      Set<BasicType> basicTypes,
      Set<GroupType> groupTypes,
      Set<String> values,
      Scope scope) {
    this.levels = levels;
    this.actions = actions;
    this.modes = modes;
    this.dataFormats = Arrays.stream(DataFormat.values()).collect(Collectors.toSet());
    this.clazz = clazz;
    this.basicTypes = basicTypes;
    this.groupTypes = groupTypes;
    this.values = values;
    this.scope = scope;
  }

  ModifierDescriptorEnumGlobal(
      Set<Level> levels, Set<ImpexAction> actions, Set<Mode> modes, Class<?> clazz, Scope scope) {
    this(levels, actions, modes, clazz, EMPTY_SET, EMPTY_SET, EMPTY_SET, scope);
  }

  ModifierDescriptorEnumGlobal(
      Set<Level> levels,
      Set<ImpexAction> actions,
      Set<Mode> modes,
      Set<DataFormat> dataFormats,
      Class<?> clazz,
      Scope scope) {
    this(levels, actions, modes, dataFormats, clazz, EMPTY_SET, EMPTY_SET, EMPTY_SET, scope);
  }
}
