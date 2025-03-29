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

import java.util.Collection;
import org.anasoid.impexia.core.manager.transformer.ChainedTransformer;
import org.anasoid.impexia.core.manager.transformer.MonoTransformer;
import org.anasoid.impexia.core.manager.transformer.TransformerOrder;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.importing.manager.processor.header.ImportHeaderProcessor;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.apache.commons.lang3.tuple.Pair;

public class PrepareHeader {

  ChainedTransformer<ImportHeaderProcessor, ImportingImpexContext<?>> ctxChainedTransformer;

  public PrepareHeader(
      Collection<
              Pair<
                  TransformerOrder,
                  MonoTransformer<ImportHeaderProcessor, ImportingImpexContext<?>>>>
          orderedTransformers) {
    ctxChainedTransformer = new ChainedTransformer<>(orderedTransformers);
  }

  public ImportHeaderProcessor prepare(ImpexHeader header, ImportingImpexContext<?> context) {
    return ctxChainedTransformer.transform(new ImportHeaderProcessor(header), context);
  }
}
