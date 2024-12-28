package org.anasoid.impexia.importing.manager.transformer.global;

import static org.anasoid.impexia.meta.modifier.ModifierEnumGlobal.*;

import org.anasoid.impexia.core.manager.values.AtomicColumnReference;
import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.meta.header.ImpexAction;
import org.anasoid.impexia.meta.header.ImpexAttribute;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.header.ImpexModifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

class RecordLineTransformerTest {

  @Test
  void transform_test() {

    RecordLineTransformer recordLineTransformer = new RecordLineTransformer();
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder(UNIQUE, "true").build())
                    .build())
            .attribute(ImpexAttribute.builder("name").build())
            .build();
    LineValues lineValue =
        recordLineTransformer.transform(
            new String[] {"aa", "bb"}, impexHeader, ImportingImpexContext.builder().build());
    Assertions.assertEquals(2, lineValue.size());
    AtomicColumnReference v1 = lineValue.getValues().get(0);
    Assertions.assertEquals("aa", v1.getColumnValue().getValue());
    Assertions.assertEquals("code", v1.getImpexAttribute().getField());
    AtomicColumnReference v2 = lineValue.getValues().get(1);
    Assertions.assertEquals("bb", v2.getColumnValue().getValue());
    Assertions.assertEquals("name", v2.getImpexAttribute().getField());
  }

  @Test
  void transform_virtual_test() {

    RecordLineTransformer recordLineTransformer = new RecordLineTransformer();
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder(UNIQUE, "true").build())
                    .build())
            .attribute(
                ImpexAttribute.builder("virtual")
                    .modifier(ImpexModifier.builder(VIRTUAL, "true").build())
                    .modifier(ImpexModifier.builder(DEFAULT, "vv").build())
                    .build())
            .attribute(ImpexAttribute.builder("name").build())
            .build();
    LineValues lineValue =
        recordLineTransformer.transform(
            new String[] {"aa", "bb"}, impexHeader, ImportingImpexContext.builder().build());
    Assertions.assertEquals(3, lineValue.size());
    AtomicColumnReference v1 = lineValue.getValues().get(0);
    Assertions.assertEquals("aa", v1.getColumnValue().getValue());
    Assertions.assertEquals("code", v1.getImpexAttribute().getField());
    AtomicColumnReference v2 = lineValue.getValues().get(1);
    Assertions.assertEquals("vv", v2.getColumnValue().getValue());
    Assertions.assertEquals("virtual", v2.getImpexAttribute().getField());
    AtomicColumnReference v3 = lineValue.getValues().get(2);
    Assertions.assertEquals("bb", v3.getColumnValue().getValue());
    Assertions.assertEquals("name", v3.getImpexAttribute().getField());
  }
}
