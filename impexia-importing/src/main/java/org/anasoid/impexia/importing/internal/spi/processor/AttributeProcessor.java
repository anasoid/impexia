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

import java.util.ArrayList;
import java.util.List;
import org.anasoid.impexia.importing.internal.spi.processor.attribute.result.AttributeProcessingResult;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;

public abstract class AttributeProcessor<I, V> {

  public abstract AttributeProcessingResult<V> process(
      I item, V value, ImpexHeader header, ImpexAttribute attribute);

  public AttributeProcessingResult<List<V>> process(
      I item, List<V> value, ImpexHeader header, ImpexAttribute attribute) {
    List<V> list = new ArrayList<>();
    if (value != null) {

      value.stream().forEach(list::add);
    }
    return AttributeProcessingResult.<List<V>>builder().value(list).build();
  }
}
