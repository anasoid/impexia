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
 * Date :   12-Jan-2025
 */

package org.anasoid.impexia.importing.internal.service;

import lombok.AccessLevel;
import lombok.Getter;
import org.anasoid.impexia.core.data.importing.DataReader;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.importing.manager.config.ImportingImpexSettings;
import org.anasoid.impexia.importing.manager.processor.header.ImportHeaderProcessor;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractImpexiaImportingExecutor<
    T extends ImportingImpexSettings, C extends ImportingImpexContext<T>> {
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final ImportHeaderProcessor impexHeaderProcessor;

  @Getter(AccessLevel.PROTECTED)
  private final C context;

  protected AbstractImpexiaImportingExecutor(
      ImportHeaderProcessor impexHeaderProcessor, C context) {
    this.impexHeaderProcessor = impexHeaderProcessor;
    this.context = context;
  }

  void importData(DataReader dataReader) {}
}
