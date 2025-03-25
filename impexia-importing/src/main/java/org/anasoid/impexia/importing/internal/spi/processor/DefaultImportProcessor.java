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
 * Date :   18-Mar-2025
 */

package org.anasoid.impexia.importing.internal.spi.processor;

import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.importing.internal.spi.datasource.DataSourceContainer;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.meta.header.ImpexHeader;
import java.util.Iterator;
import java.util.Map;

public class DefaultImportProcessor implements ImportProcessor {
  Map<String, AttributeProcessor<?, ?>> attributeProcessors;

  @Override
  public int insert(DataSourceContainer datasource, ImpexHeader impexHeader,
      ImportingImpexContext context, LineValues lineValues) {

    return 0;
  }

  @Override
  public int update(DataSourceContainer datasource, ImpexHeader impexHeader,
      ImportingImpexContext context, Object item, LineValues lineValues) {
    return 0;
  }

  @Override
  public Iterator load(DataSourceContainer datasource, ImpexHeader impexHeader,
      ImportingImpexContext context, LineValues lineValues) {
    return null;
  }

  @Override
  public int remove(DataSourceContainer datasource, ImpexHeader impexHeader,
      ImportingImpexContext context, Object item, LineValues lineValues) {
    return 0;
  }

  @Override
  public Object loadItem(ImportingImpexContext context, Object itemReference) {
    return null;
  }
}
