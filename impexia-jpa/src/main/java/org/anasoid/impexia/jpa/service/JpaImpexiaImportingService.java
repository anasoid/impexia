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

package org.anasoid.impexia.jpa.service;

import org.anasoid.impexia.importing.internal.service.AbstractImpexiaImportingService;
import org.anasoid.impexia.jpa.importing.register.JpaImportingRegistrator;
import org.anasoid.impexia.jpa.meta.JpaScopeEnum;
import org.anasoid.impexia.meta.Scope;

public class JpaImpexiaImportingService extends AbstractImpexiaImportingService {

  public JpaImpexiaImportingService() {
    super(new JpaImportingRegistrator());
  }

  @Override
  protected Scope getScope() {
    return JpaScopeEnum.JPA;
  }
}