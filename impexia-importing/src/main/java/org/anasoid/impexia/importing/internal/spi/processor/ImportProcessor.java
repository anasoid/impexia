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
 * Date :   22-Mar-2025
 */

package org.anasoid.impexia.importing.internal.spi.processor;

import java.util.Iterator;
import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.importing.internal.spi.datasource.DataSourceContainer;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.importing.manager.processor.header.ImportHeaderProcessor;

public interface ImportProcessor<
    S extends DataSourceContainer<?, ?, ?>, C extends ImportingImpexContext<?>, R, T> {

  void insert(
      S datasource, ImportHeaderProcessor importHeaderProcessor, C context, LineValues lineValues);

  int update(
      S datasource,
      ImportHeaderProcessor importHeaderProcessor,
      C context,
      R item,
      LineValues lineValues);

  Iterator<R> load(
      S datasource, ImportHeaderProcessor importHeaderProcessor, C context, LineValues lineValues);

  int remove(
      S datasource,
      ImportHeaderProcessor importHeaderProcessor,
      C context,
      R item,
      LineValues lineValues);

  T loadItem(C context, R itemReference);
}
