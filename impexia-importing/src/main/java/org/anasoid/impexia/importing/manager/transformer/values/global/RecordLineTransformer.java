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

package org.anasoid.impexia.importing.manager.transformer.values.global;

import static org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnumGlobal.DEFAULT;
import static org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnumGlobal.VIRTUAL;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.anasoid.impexia.core.exceptions.InvalidLineStrictFormatException;
import org.anasoid.impexia.core.manager.transformer.ChildTransformer;
import org.anasoid.impexia.core.manager.values.AtomicColumnReference;
import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.core.manager.values.LineValues.LineValuesBuilder;
import org.anasoid.impexia.core.manager.values.column.StringColumnValue;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.importing.manager.config.ImportingImpexSettings;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;

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
                if (checkMissingColumn(records, i.get(), context.getSettings())) {
                  value = records[i.get()];
                }
                lineBuilder.value(
                    new AtomicColumnReference(
                        atr, StringColumnValue.builder().value(value).build()));
                i.getAndIncrement();
              }
            });
    checkAdditionalColumn(records, i.get(), context.getSettings());
    return lineBuilder.build();
  }

  private AtomicColumnReference getVirtualValue(ImpexAttribute impexAttribute) {
    Optional<ImpexModifier> defaultValue =
        impexAttribute.getModifiers().stream()
            .filter(m -> m.getKey().equalsIgnoreCase(DEFAULT.name()))
            .findFirst();
    if (defaultValue.isPresent()) {
      return new AtomicColumnReference(
          impexAttribute, StringColumnValue.builder().value(defaultValue.get().getValue()).build());
    } else {
      throw new InvalidLineStrictFormatException("Default Value is mandatory for virtual");
    }
  }

  private void checkAdditionalColumn(
      String[] records, int elementCount, ImportingImpexSettings settings) {
    if (records.length > elementCount
        && (settings == null || !Boolean.TRUE.equals(settings.getLineIgnoreAdditionalColumn()))) {
      throw new InvalidLineStrictFormatException(
          MessageFormat.format("Line has more elements {0} than header", records.length));
    }
  }

  private boolean checkMissingColumn(
      String[] records, int elementCount, ImportingImpexSettings settings) {
    if (elementCount >= records.length) {
      if (settings == null || !Boolean.TRUE.equals(settings.getLineMissingColumnAsNull())) {
        throw new InvalidLineStrictFormatException(
            MessageFormat.format("Line has less elements {0} than header", records.length));
      }
      return false;
    } else {
      return true;
    }
  }
}
