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
 * Date :   16-Mar-2025
 */

package org.anasoid.impexia.importing.internal.api.register;

import lombok.Getter;
import org.anasoid.impexia.core.manager.transformer.MonoTransformer;
import org.anasoid.impexia.core.manager.transformer.TransformerOrder;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.meta.Scope;
import org.anasoid.impexia.meta.header.ImpexHeader;

@Getter
public class RegisterHeaderPrepareElement {

  private final String name;
  private final Scope scope;
  private final TransformerOrder transformerOrder;
  private final MonoTransformer<ImpexHeader, ImportingImpexContext<?>> transformer;

  public RegisterHeaderPrepareElement(
      String name,
      Scope scope,
      TransformerOrder transformerOrder,
      MonoTransformer<ImpexHeader, ImportingImpexContext<?>> transformer) {
    this.name = name;
    this.scope = scope;
    this.transformerOrder = transformerOrder;
    this.transformer = transformer;
  }
}
