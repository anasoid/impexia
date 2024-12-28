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

package org.anasoid.impexia.core.manager.transformer;

import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class ChildChainedTransformer<T, P, C> implements ChildTransformer<T, T, P, C> {

  private final List<ChildTransformer<T, T, P, C>> transformers;

  public ChildChainedTransformer(
      List<Pair<TransformerOrder, ChildTransformer<T, T, P, C>>> orderedTransformers) {
    this.transformers =
        orderedTransformers.stream()
            .sorted(Comparator.comparing(p -> p.getLeft().getOrder()))
            .map(Pair::getValue)
            .toList();
  }

  @Override
  public T transform(T value, P parent, C ctx) {
    T currentValue = value;
    for (ChildTransformer<T, T, P, C> transformer : transformers) {
      currentValue = transformer.transform(currentValue, parent, ctx);
    }
    return currentValue;
  }
}
