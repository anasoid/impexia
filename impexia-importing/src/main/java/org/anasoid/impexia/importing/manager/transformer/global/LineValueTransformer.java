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

import java.util.Arrays;
import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.core.manager.values.LineValues.LineValuesBuilder;
import org.anasoid.impexia.core.manager.values.column.StringColumnValue;
import org.anasoid.impexia.importing.manager.transformer.LineTransformer;

public class LineValueTransformer implements LineTransformer<String[], LineValues> {

  @Override
  public LineValues transform(String[] in) {
    LineValuesBuilder<?, ?> lineBuilder = LineValues.builder();
    Arrays.stream(in)
        .forEach(str -> lineBuilder.value(StringColumnValue.builder().value(str).build()));

    return lineBuilder.build();
  }
}
