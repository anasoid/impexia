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

package org.anasoid.impexia.core.meta.header;

import java.util.Collection;
import org.anasoid.impexia.meta.header.ImpexMapping;

public class DefaultImpexMapping extends ImpexMapping {

  public DefaultImpexMapping(String field) {
    this.field = field;
  }

  public DefaultImpexMapping addMapping(ImpexMapping impexMapping) {
    this.getMappings().add(impexMapping);
    return this;
  }

  public DefaultImpexMapping addAllMapping(Collection<ImpexMapping> impexMappings) {
    this.getMappings().addAll(impexMappings);
    return this;
  }

  @Override
  public String toString() {
    return "DefaultImpexMapping{"
        + "field='"
        + field
        + '\''
        + (!mappings.isEmpty() ? ", mappings=" + mappings : "")
        + '}';
  }
}
