package org.anasoid.impexia.importing.manager.transformer.global;

import static org.junit.jupiter.api.Assertions.*;

import org.anasoid.impexia.core.manager.values.LineValues;
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

class LineValueTransformerTest {

  @Test
  void transform_test() {

    LineValueTransformer lineValueTransformer = new LineValueTransformer();
    LineValues lineValue = lineValueTransformer.transform(new String[] {"aa", "bb"});
    Assertions.assertEquals(lineValue.size(), 2);
    Assertions.assertEquals(lineValue.getValues().get(0).getValue(), "aa");
    Assertions.assertEquals(lineValue.getValues().get(1).getValue(), "bb");
  }
}
