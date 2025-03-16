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

package org.anasoid.impexia.core.register;

import org.anasoid.impexia.core.internal.api.register.RegisterModifierManager;
import org.anasoid.impexia.core.internal.api.register.RegisterModifierManager.UnLoader;
import org.anasoid.impexia.core.internal.spi.register.RegistratorAgent;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnum;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnumGlobal;

public final class ModifierRegistratorAgent implements RegistratorAgent<ModifierDescriptorEnum> {

  private final RegisterModifierManager registerModifierManager;

  @SuppressWarnings("PMD.AvoidUsingVolatile")
  private volatile boolean loaded;

  private ModifierRegistratorAgent() {
    registerModifierManager = RegisterModifierManager.getInstance();
  }

  public static ModifierRegistratorAgent getInstance() {
    return ModifierRegistratorAgent.LazyHolder.INSTANCE;
  }

  private static final class LazyHolder {

    static final ModifierRegistratorAgent INSTANCE = new ModifierRegistratorAgent(); // NOPMD
  }

  @Override
  public void load() {
    if (!loaded) {
      register();
    }
  }

  @Override
  public void unLoad() {
    // Nothing to do
  }

  @Override
  public void unLoadForce() {
    UnLoader unLoader = new UnLoader();
    unLoader.unload();
  }

  @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
  private synchronized void register() {
    if (!loaded) {
      registerModifierManager.register(ModifierDescriptorEnumGlobal.BATCHMODE);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.LISTENER);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.ERRORHANDLER);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.CELLDECORATOR);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.TRANSLATOR);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.COLLECTIONDELIMITER);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.KEY2VALUEDELIMITER);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.NUMBERFORMAT);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.DATEFORMAT);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.PATHDELIMITER);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.UNIQUE);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.MANDATORY);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.IGNORE_NULL);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.MODE);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.VIRTUAL);
      registerModifierManager.register(ModifierDescriptorEnumGlobal.DEFAULT);
      loaded = true;
    }
  }
}
