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
 * Date :   24-Dec-2024
 */

package org.anasoid.impexia.importing.manager.configurer;

import java.util.ArrayList;
import java.util.List;
import org.anasoid.impexia.core.manager.transformer.ChildTransformer;
import org.anasoid.impexia.core.manager.transformer.Transformer;
import org.anasoid.impexia.core.manager.transformer.TransformerOrder;
import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.importing.manager.config.ImportingImpexSettings;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.apache.commons.lang3.tuple.Pair;

public class ImpexiaImportingConfigurerFactory<C extends ImportingImpexSettings>
    implements ImpexiaImportingConfigurer<C> {

  @Override
  public List<Pair<TransformerOrder, Transformer<ImpexHeader, ImpexHeader, C>>>
      getHeaderTransformer() {
    List<Pair<TransformerOrder, Transformer<ImpexHeader, ImpexHeader, C>>> transformers =
        new ArrayList<>();
    return transformers;
  }

  public List<Pair<TransformerOrder, ChildTransformer<LineValues, LineValues, ImpexHeader, C>>>
      getLineValuesTransformer() {
    List<Pair<TransformerOrder, ChildTransformer<LineValues, LineValues, ImpexHeader, C>>>
        transformers = new ArrayList<>();
    return transformers;
  }
}
