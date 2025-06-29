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
 * Date :   23-Dec-2024
 */

package org.anasoid.impexia.meta.scope;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import org.anasoid.impexia.meta.Scope;

public enum ScopeEnum implements Scope {
  GLOBAL("GLOBAL", new HashSet<>()),
  ORM("ORM", Set.of(GLOBAL));
  @Getter private final String name;
  @Getter private final Set<Scope> scopes;

  ScopeEnum(String name, Set<Scope> scopes) {
    this.name = name;
    this.scopes = scopes;
  }

  @Override
  public Set<String> getScopesAsString() {

    Set<String> result = new HashSet<>(Set.of(name));
    scopes.forEach(s -> result.addAll(s.getScopesAsString()));

    return result;
  }
}
