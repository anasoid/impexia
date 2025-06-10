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

import org.anasoid.impexia.core.manager.transformer.ChainedTransformer;
import org.anasoid.impexia.core.manager.transformer.ChainedTransformerBuilder;
import org.anasoid.impexia.core.parser.header.HeaderParser;
import org.anasoid.impexia.importing.internal.spi.factory.AbstractImportingFactory;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.importing.manager.processor.header.ImportHeaderProcessor;
import org.anasoid.impexia.meta.header.ImpexHeader;

class HeaderImportingHelper {
  private final AbstractImportingFactory importingFactory;

  HeaderImportingHelper(AbstractImportingFactory importingFactory) {
    this.importingFactory = importingFactory;
  }

  ImportHeaderProcessor prepare(String[] headerRecords, ImportingImpexContext<?> context) {
    ImpexHeader impexHeader = parseHeader(headerRecords);
    ChainedTransformerBuilder<ImportHeaderProcessor, ImportingImpexContext<?>> builder =
        ChainedTransformer.builder();
    importingFactory.getOrderedPreparingHeaderTransformers().stream()
        .forEach(t -> builder.addTransformer(t.getOrder(), t.getTransformer()));

    ChainedTransformer<ImportHeaderProcessor, ImportingImpexContext<?>> ctxChainedTransformer =
        builder.build();
    return ctxChainedTransformer.transform(new ImportHeaderProcessor(impexHeader), context);
  }

  @SuppressWarnings("PMD.UseVarargs")
  protected ImpexHeader parseHeader(String[] headerRecords) {
    return HeaderParser.parse(headerRecords);
  }
}
