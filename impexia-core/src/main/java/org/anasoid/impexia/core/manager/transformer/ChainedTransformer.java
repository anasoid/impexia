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

import java.util.List;

public class ChainedTransformer<T, C> implements MonoTransformer<T, C> {

  private final List<MonoTransformer<T, C>> transformers;

  public ChainedTransformer(List<MonoTransformer<T, C>> transformers) {
    this.transformers = transformers;
  }

  @Override
  public T transform(T value, C ctx) {
    T currentValue = value;
    for (Transformer<T, T, C> transformer : transformers) {
      currentValue = transformer.transform(currentValue, ctx);
    }
    return currentValue;
  }

  public static <T, C> ChainedTransformerBuilder<T, C> builder() {
    return new ChainedTransformerBuilder<>();
  }
}
