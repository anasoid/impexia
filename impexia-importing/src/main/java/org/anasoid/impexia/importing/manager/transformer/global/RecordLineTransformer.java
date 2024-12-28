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
import org.anasoid.impexia.core.exceptions.InvalidLineStrictFormatException;
import org.anasoid.impexia.core.manager.transformer.ChildTransformer;
import org.anasoid.impexia.core.manager.values.AtomicColumnReference;
import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.core.manager.values.LineValues.LineValuesBuilder;
import org.anasoid.impexia.core.manager.values.column.StringColumnValue;
import org.anasoid.impexia.importing.manager.config.ImportingImpexConfig;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;

public class RecordLineTransformer<C extends ImportingImpexContext<?>>
    implements ChildTransformer<LineValues, String[], ImpexHeader, C> {

  @Override
  public LineValues transform(String[] records, ImpexHeader impexHeader, C context) {
    LineValuesBuilder<?, ?> lineBuilder = LineValues.builder();
    AtomicInteger i = new AtomicInteger();

    impexHeader.getAttributes().stream()
        .forEach(
            atr -> {
              if (atr.getModifiers().stream()
                  .anyMatch(m -> m.getKey().equalsIgnoreCase(VIRTUAL.name()))) {
                lineBuilder.value(getVirtualValue(atr));
              } else {
                String value = null;
                if (checkMissingColumn(records, i.get(), context.getConfig())) {
                  value = records[i.get()];
                }
                lineBuilder.value(
                    new AtomicColumnReference(
                        atr, StringColumnValue.builder().value(value).build()));
                i.getAndIncrement();
              }
            });
    checkAdditionalColumn(records, i.get(), context.getConfig());
    return lineBuilder.build();
  }

  private AtomicColumnReference getVirtualValue(ImpexAttribute impexAttribute) {
    String defaultValue =
        impexAttribute.getModifiers().stream()
            .filter(m -> m.getKey().equalsIgnoreCase(DEFAULT.name()))
            .findFirst()
            .get()
            .getValue();

    return new AtomicColumnReference(
        impexAttribute, StringColumnValue.builder().value(defaultValue).build());
  }

  private void checkAdditionalColumn(
      String[] records, int elementCount, ImportingImpexConfig config) {
    if (records.length > elementCount
        && (config == null || !Boolean.TRUE.equals(config.getLineIgnoreAdditionalColumn()))) {
      throw new InvalidLineStrictFormatException(
          String.format("Line has more elements {0} than header", records.length));
    }
  }

  private boolean checkMissingColumn(
      String[] records, int elementCount, ImportingImpexConfig config) {
    if (elementCount >= records.length) {
      if (config == null || !Boolean.TRUE.equals(config.getLineMissingColumnAsNull())) {
        throw new InvalidLineStrictFormatException(
            String.format("Line has less elements {0} than header", records.length));
      }
      return false;
    } else {
      return true;
    }
  }
}
