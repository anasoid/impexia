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

import org.anasoid.impexia.core.data.importing.DataReader;
import org.anasoid.impexia.meta.header.ImpexHeader;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractImpexiaImportingExecutor {
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final ImpexHeader impexHeader;

  protected AbstractImpexiaImportingExecutor(ImpexHeader impexHeader) {
    this.impexHeader = impexHeader;
  }

  void importData(DataReader dataReader) {}
}
