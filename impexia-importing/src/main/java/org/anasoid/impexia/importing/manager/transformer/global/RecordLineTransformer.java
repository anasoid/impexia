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
 * Date :   22-Dec-2024
 */

package org.anasoid.impexia.importing.manager.transformer.global;

import static org.anasoid.impexia.meta.modifier.ModifierEnumGlobal.DEFAULT;
import static org.anasoid.impexia.meta.modifier.ModifierEnumGlobal.VIRTUAL;

import java.util.concurrent.atomic.AtomicInteger;
import org.anasoid.impexia.core.manager.transformer.ChildTransformer;
import org.anasoid.impexia.core.manager.values.AtomicColumnReference;
import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.core.manager.values.LineValues.LineValuesBuilder;
import org.anasoid.impexia.core.manager.values.column.StringColumnValue;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.meta.header.ImpexHeader;

public class RecordLineTransformer<C extends ImportingImpexContext<?>>
    implements ChildTransformer<LineValues, String[], ImpexHeader, C> {

  @Override
  public LineValues transform(String[] records, ImpexHeader impexHeader, C ctx) {
    LineValuesBuilder<?, ?> lineBuilder = LineValues.builder();
    AtomicInteger i = new AtomicInteger();
    AtomicInteger v = new AtomicInteger();
    impexHeader.getAttributes().stream()
        .forEach(
            atr -> {
              if (atr.getModifiers().stream()
                  .anyMatch(m -> m.getKey().equalsIgnoreCase(VIRTUAL.name()))) {
                String defaultValue =
                    atr.getModifiers().stream()
                        .filter(m -> m.getKey().equalsIgnoreCase(DEFAULT.name()))
                        .findFirst()
                        .get()
                        .getValue();
                lineBuilder.value(
                    new AtomicColumnReference(
                        atr, StringColumnValue.builder().value(defaultValue).build()));
                v.getAndIncrement();
              } else {
                lineBuilder.value(
                    new AtomicColumnReference(
                        atr, StringColumnValue.builder().value(records[i.get()]).build()));
                i.getAndIncrement();
              }
            });

    return lineBuilder.build();
  }
}
