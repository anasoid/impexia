package org.anasoid.impexia.importing.manager.transformer.global;

import static org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnumGlobal.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.anasoid.impexia.core.exceptions.InvalidLineStrictFormatException;
import org.anasoid.impexia.core.manager.values.AtomicColumnReference;
import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.importing.manager.config.ImportingImpexSettings;
import org.anasoid.impexia.importing.manager.transformer.values.global.RecordLineTransformer;
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
                    .modifier(ImpexModifier.builder(UNIQUE.toString(), "true").build())
                    .build())
            .attribute(ImpexAttribute.builder("name").build())
            .build();
    LineValues lineValue =
        recordLineTransformer.transform(
            new String[] {"aa", "bb"}, impexHeader, ImportingImpexContext.builder().build());
    assertThat(lineValue.size()).isEqualTo(2);
    AtomicColumnReference v1 = lineValue.getValues().get(0);
    assertThat(v1.getColumnValue().getValue()).isEqualTo("aa");
    assertThat(v1.getImpexAttribute().getField()).isEqualTo("code");
    AtomicColumnReference v2 = lineValue.getValues().get(1);
    assertThat(v2.getColumnValue().getValue()).isEqualTo("bb");
    assertThat(v2.getImpexAttribute().getField()).isEqualTo("name");
  }

  @Test
  void transform_virtual_test() {

    RecordLineTransformer recordLineTransformer = new RecordLineTransformer();
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder(UNIQUE.toString(), "true").build())
                    .build())
            .attribute(
                ImpexAttribute.builder("virtual")
                    .modifier(ImpexModifier.builder(VIRTUAL.toString(), "true").build())
                    .modifier(ImpexModifier.builder(DEFAULT.toString(), "vv").build())
                    .build())
            .attribute(ImpexAttribute.builder("name").build())
            .build();
    LineValues lineValue =
        recordLineTransformer.transform(
            new String[] {"aa", "bb"}, impexHeader, ImportingImpexContext.builder().build());
    assertThat(lineValue.size()).isEqualTo(3);
    AtomicColumnReference v1 = lineValue.getValues().get(0);
    assertThat(v1.getColumnValue().getValue()).isEqualTo("aa");
    assertThat(v1.getImpexAttribute().getField()).isEqualTo("code");
    AtomicColumnReference v2 = lineValue.getValues().get(1);
    assertThat(v2.getColumnValue().getValue()).isEqualTo("vv");
    assertThat(v2.getImpexAttribute().getField()).isEqualTo("virtual");
    AtomicColumnReference v3 = lineValue.getValues().get(2);
    assertThat(v3.getColumnValue().getValue()).isEqualTo("bb");
    assertThat(v3.getImpexAttribute().getField()).isEqualTo("name");
  }

  @Test
  void transform_fail_header_missing_line_value() {

    RecordLineTransformer recordLineTransformer = new RecordLineTransformer();
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder(UNIQUE.toString(), "true").build())
                    .build())
            .attribute(ImpexAttribute.builder("name").build())
            .build();
    try {
      recordLineTransformer.transform(
          new String[] {"aa"}, impexHeader, ImportingImpexContext.builder().build());
      Assertions.fail();
    } catch (InvalidLineStrictFormatException e) {
      assertThat(e.getMessage()).contains("has less ");
    }
  }

  @Test
  void transform_success_header_missing_line_value() {

    RecordLineTransformer recordLineTransformer = new RecordLineTransformer();
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder(UNIQUE.toString(), "true").build())
                    .build())
            .attribute(ImpexAttribute.builder("name").build())
            .build();

    LineValues lineValue =
        recordLineTransformer.transform(
            new String[] {"aa"},
            impexHeader,
            ImportingImpexContext.builder()
                .settings(ImportingImpexSettings.builder().lineMissingColumnAsNull(true).build())
                .build());
    assertThat(lineValue.size()).isEqualTo(2);
    AtomicColumnReference v1 = lineValue.getValues().get(0);
    assertThat(v1.getColumnValue().getValue()).isEqualTo("aa");
    assertThat(v1.getImpexAttribute().getField()).isEqualTo("code");
    AtomicColumnReference v2 = lineValue.getValues().get(1);
    assertThat(v2.getColumnValue().getValue()).isNull();
    assertThat(v2.getImpexAttribute().getField()).isEqualTo("name");
  }

  @Test
  void transform_fail_header_additional_column_value() {

    RecordLineTransformer recordLineTransformer = new RecordLineTransformer();
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder(UNIQUE.toString(), "true").build())
                    .build())
            .build();
    try {
      recordLineTransformer.transform(
          new String[] {"aa", "bb"}, impexHeader, ImportingImpexContext.builder().build());
      Assertions.fail();
    } catch (InvalidLineStrictFormatException e) {
      assertThat(e.getMessage()).contains("has more ");
    }
  }

  @Test
  void transform_success_header_additional_column_value() {

    RecordLineTransformer recordLineTransformer = new RecordLineTransformer();
    ImpexHeader impexHeader =
        ImpexHeader.builder("product", ImpexAction.INSERT)
            .attribute(
                ImpexAttribute.builder("code")
                    .modifier(ImpexModifier.builder(UNIQUE.toString(), "true").build())
                    .build())
            .build();

    LineValues lineValue =
        recordLineTransformer.transform(
            new String[] {"aa", "bb"},
            impexHeader,
            ImportingImpexContext.builder()
                .settings(ImportingImpexSettings.builder().lineIgnoreAdditionalColumn(true).build())
                .build());

    AtomicColumnReference v1 = lineValue.getValues().get(0);
    assertThat(v1.getColumnValue().getValue()).isEqualTo("aa");
    assertThat(v1.getImpexAttribute().getField()).isEqualTo("code");
  }
}
