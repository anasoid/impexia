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
 * Date :   01-Jan-2025
 */

package org.anasoid.impexia.core.internal.spi.service;

import org.anasoid.impexia.core.internal.spi.register.AbstractRegistrator;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractImpexiaService {

  private final AbstractRegistrator registrator;

  protected AbstractImpexiaService(AbstractRegistrator registrator) {
    this.registrator = registrator;

    registrator.load();
  }
}