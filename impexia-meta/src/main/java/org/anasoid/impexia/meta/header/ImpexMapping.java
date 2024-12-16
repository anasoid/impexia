/*
 * Copyright 2020-2020 the original author or authors.
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
 * Date :   21-Nov-2020
 */

package org.anasoid.impexia.meta.header;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/** Impex mapping description container. */
@SuppressWarnings({"PMD.AbstractClassWithoutAbstractMethod"})
@SuperBuilder
@Getter
@ToString
public class ImpexMapping {

  protected String field;

  @Setter(AccessLevel.PROTECTED)
  @ToString.Exclude
  protected ImpexAttribute parent;

  @Singular protected List<ImpexMapping> mappings;

  public static ImpexMapping.ImpexMappingBuilder builder(String field) {
    return new ImpexMapping.ImpexMappingBuilderImpl().field(field);
  }
}
