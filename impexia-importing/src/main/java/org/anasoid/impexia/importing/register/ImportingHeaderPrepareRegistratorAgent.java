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

package org.anasoid.impexia.importing.register;

import org.anasoid.impexia.core.internal.spi.register.RegistratorAgent;
import org.anasoid.impexia.importing.internal.api.register.HeaderPrepareRegister;
import org.anasoid.impexia.importing.internal.api.register.HeaderPrepareRegister.UnLoader;

public final class ImportingHeaderPrepareRegistratorAgent implements RegistratorAgent {

  @SuppressWarnings("PMD.UnusedPrivateField")
  private final HeaderPrepareRegister headerPrepareManager;

  @SuppressWarnings("PMD.AvoidUsingVolatile")
  private volatile boolean loaded;

  private ImportingHeaderPrepareRegistratorAgent() {
    headerPrepareManager = HeaderPrepareRegister.getInstance();
  }

  public static ImportingHeaderPrepareRegistratorAgent getInstance() {
    return ImportingHeaderPrepareRegistratorAgent.LazyHolder.INSTANCE;
  }

  private static final class LazyHolder {

    static final ImportingHeaderPrepareRegistratorAgent INSTANCE =
        new ImportingHeaderPrepareRegistratorAgent(); // NOPMD
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
      // headerPrepareManager.register(ModifierDescriptorEnumGlobal.BATCHMODE);

      loaded = true;
    }
  }
}
