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

package org.anasoid.impexia.importing.manager.prepare;

import java.util.List;
import org.anasoid.impexia.core.manager.transformer.ChainedTransformer;
import org.anasoid.impexia.core.manager.transformer.Transformer;
import org.anasoid.impexia.core.manager.transformer.TransformerOrder;
import org.anasoid.impexia.importing.manager.config.ImportingImpexConfig;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.apache.commons.lang3.tuple.Pair;

public class PrepareHeader<C extends ImportingImpexConfig> {

  ChainedTransformer<ImpexHeader, C> ctxChainedTransformer;

  public PrepareHeader(
      List<Pair<TransformerOrder, Transformer<ImpexHeader, ImpexHeader, C>>> orderedTransformers) {
    ctxChainedTransformer = new ChainedTransformer<>(orderedTransformers);
  }

  ImpexHeader prepare(ImpexHeader header, C context) {
    return ctxChainedTransformer.transform(header, context);
  }
}