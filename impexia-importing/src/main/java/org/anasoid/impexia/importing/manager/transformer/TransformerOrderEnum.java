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
 * Date :   23-Dec-2024
 */

package org.anasoid.impexia.importing.manager.transformer;

import lombok.Getter;
import org.anasoid.impexia.core.manager.transformer.TransformerOrder;

public enum TransformerOrderEnum implements TransformerOrder {
  INIT(-1000),
  RAW(0),
  TYPE(RAW.order + 1000),
  COLLECTION(TYPE.order + 1000),
  DATA_FORMAT(COLLECTION.order + 1000),
  TRANSFORMER(1000000);

  @Getter Integer order;

  TransformerOrderEnum(int order) {
    this.order = order;
  }

  public TransformerOrder before(TransformerOrderEnum transformerOrder) {
    return () -> transformerOrder.getOrder() - 1;
  }

  public TransformerOrder after(TransformerOrderEnum transformerOrder) {
    return () -> transformerOrder.getOrder() + 1;
  }
}
