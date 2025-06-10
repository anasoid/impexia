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

import java.util.Locale;
import java.util.Map;
import org.anasoid.impexia.core.internal.api.register.RegisterModifierManager;
import org.anasoid.impexia.meta.Scope;

/**
 * Modifier Manager Register Known list of modifier, modifier also can have scope, GLOBAL for global
 * modifier , same modifier can be restricted to scope like JPA, SQL ...
 */
public final class ModifierDescriptorManager {

  private final RegisterModifierManager registerModifierManager;

  private ModifierDescriptorManager() {
    registerModifierManager = RegisterModifierManager.getInstance();
  }

  /**
   * get Instance of ModifierManager.
   *
   * @return singleton instance Of modifier Manager.
   */
  public static ModifierDescriptorManager getInstance() {
    return LazyHolder.INSTANCE;
  }

  /**
   * Get enum value by code.
   *
   * @param code code
   * @return enum value
   * @throws IllegalArgumentException throw if no modifier found by code
   */
  public ModifierDescriptor getValueByCode(String code) {

    return registerModifierManager.getValueByCode(code.toLowerCase(Locale.ROOT));
  }

  /**
   * Get map modifier value by scope.
   *
   * @param scope code
   * @return enum value
   * @throws IllegalArgumentException throw if no scope found by code
   */
  public Map<String, ModifierDescriptor> getValuesByScope(Scope scope) {
    return registerModifierManager.getValuesByScope(scope);
  }

  private static final class LazyHolder {

    static final ModifierDescriptorManager INSTANCE = new ModifierDescriptorManager();
  }
}
