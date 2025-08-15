/*
 * Copyright 2020-2024 the original author or authors.
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
 * Date :   22-Dec-2024
 */

package org.anasoid.impexia.importing.internal.service;

import java.util.List;
import java.util.Properties;
import org.anasoid.impexia.core.data.importing.DataReader;
import org.anasoid.impexia.core.data.importing.HeaderReader;
import org.anasoid.impexia.core.internal.spi.factory.AbstractBaseImportingFactory;
import org.anasoid.impexia.core.internal.spi.service.AbstractImpexiaService;
import org.anasoid.impexia.core.settings.Customizer;
import org.anasoid.impexia.core.settings.PropertyInjector;
import org.anasoid.impexia.core.settings.SettingsLoader;
import org.anasoid.impexia.importing.internal.spi.factory.AbstractImportingFactory;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.importing.manager.config.ImportingImpexSettings;
import org.anasoid.impexia.importing.manager.processor.header.ImportHeaderProcessor;
import org.anasoid.impexia.meta.Scope;

public abstract class AbstractImpexiaImportingService<
        T extends AbstractImpexiaImportingExecutor,
        S extends ImportingImpexSettings,
        B extends ImportingImpexSettings.ImportingImpexSettingsBuilder<S, ?>,
        F extends ImportingImpexContext<S>>
    extends AbstractImpexiaService implements ImpexiaImportingService<T, S, B> {

  protected AbstractImpexiaImportingService(AbstractBaseImportingFactory importingFactory) {
    super(importingFactory);
  }

  void importData(HeaderReader headerReader, DataReader dataReader) {
    //
  }

  @Override
  public T getExecutor(
      HeaderReader headerReader, Customizer<B> settingsCustomizer, List<String> configs) {

    Properties properties = SettingsLoader.loadProperties(configs, getDefaultProperties(), true);
    S settings = getRawSettings();
    PropertyInjector.injectProperties(settings, properties);
    B settingsBuilder = getSettingsBuilder(settings);
    settingsCustomizer.customize(settingsBuilder);
    return getExecutor(headerReader, settingsBuilder.build());
  }

  @Override
  public T getExecutor(HeaderReader headerReader, S settings) {
    F context = createContext(settings);
    String[] headerRaw = headerReader.getHeader();
    ImportHeaderProcessor importHeaderProcessor = prepare(headerRaw, context);
    return getInternalExecutor(importHeaderProcessor, context);
  }

  @Override
  public AbstractImportingFactory getImportingFactory() {
    return (AbstractImportingFactory) super.getImportingFactory();
  }

  ImportHeaderProcessor prepare(String[] headerRecords, ImportingImpexContext<?> context) {

    HeaderImportingHelper headerImportingHelper = new HeaderImportingHelper(getImportingFactory());
    return headerImportingHelper.prepare(headerRecords, context);
  }

  protected abstract Scope getScope();

  /** initial context with settings. */
  protected abstract F createContext(S settings);

  public abstract T getInternalExecutor(ImportHeaderProcessor impexHeaderProcessor, F context);

  /** new setting empty. */
  protected abstract S getRawSettings();

  /** builder from settings. */
  protected abstract B getSettingsBuilder(S settings);
}
