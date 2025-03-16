/*
 * Copyright 2020-2025 the original author or authors.
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
 * Date :   12-Jan-2025
 */

package org.anasoid.impexia.importing.internal.api.register;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.anasoid.impexia.core.manager.transformer.MonoTransformer;
import org.anasoid.impexia.core.manager.transformer.TransformerOrder;
import org.anasoid.impexia.core.register.RegistratorManager;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.meta.Scope;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.tuple.Pair;

public class RegisterHeaderPrepareManager
    implements RegistratorManager<RegisterHeaderPrepareElement> {

  @SuppressWarnings("PMD.UseConcurrentHashMap")
  private final Map<
          String, Pair<TransformerOrder, MonoTransformer<ImpexHeader, ImportingImpexContext<?>>>>
      headerPrepareFilters = new HashMap<>();

  private final MultiValuedMap<
          String, Pair<TransformerOrder, MonoTransformer<ImpexHeader, ImportingImpexContext<?>>>>
      headerPrepareFiltersByScope = new ArrayListValuedHashMap<>();

  public static RegisterHeaderPrepareManager getInstance() {
    return RegisterHeaderPrepareManager.LazyHolder.INSTANCE;
  }

  private static final class LazyHolder {

    static final RegisterHeaderPrepareManager INSTANCE =
        new RegisterHeaderPrepareManager(); // NOPMD
  }

  @Override
  public void register(RegisterHeaderPrepareElement element) {
    this.register(
        element.getName(),
        element.getScope(),
        element.getTransformerOrder(),
        element.getTransformer());
  }

  public void register(
      String name,
      Scope scope,
      TransformerOrder transformerOrder,
      MonoTransformer<ImpexHeader, ImportingImpexContext<?>> transformer) {
    Pair<TransformerOrder, MonoTransformer<ImpexHeader, ImportingImpexContext<?>>> prepareFilter =
        Pair.of(transformerOrder, transformer);
    headerPrepareFilters.put(name, prepareFilter);
    scope.getScopesAsString().forEach(s -> headerPrepareFiltersByScope.put(s, prepareFilter));
  }

  void unload() {
    headerPrepareFilters.clear();
  }

  public static class UnLoader {

    public void unload() {
      RegisterHeaderPrepareManager.getInstance().unload();
    }
  }

  public Collection<Pair<TransformerOrder, MonoTransformer<ImpexHeader, ImportingImpexContext<?>>>>
      getPrepareHeaderByScope(Scope scope) {
    return headerPrepareFiltersByScope.get(scope.getName());
  }
}
