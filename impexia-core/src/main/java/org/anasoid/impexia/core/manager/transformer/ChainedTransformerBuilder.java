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
 * Date :   29-Mar-2025
 */

package org.anasoid.impexia.core.manager.transformer;

import java.util.Comparator;
import java.util.List;

public class ChainedTransformerBuilder<T, C> {

  List<OrderedTransformer<MonoTransformer<T, C>>> orderedTransformers;

  public ChainedTransformerBuilder<T, C> addTransformer(
      TransformerOrder order, MonoTransformer<T, C> transformers) {
    this.orderedTransformers.add(new OrderedTransformer(order, transformers));
    return this;
  }

  public ChainedTransformer<T, C> build() {
    return new ChainedTransformer<>(
        orderedTransformers.stream()
            .sorted(Comparator.comparing(p -> p.getOrder().getOrder()))
            .map(OrderedTransformer::getTransformer)
            .toList());
  }
}
