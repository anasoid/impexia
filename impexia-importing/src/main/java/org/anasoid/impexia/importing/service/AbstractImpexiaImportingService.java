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

package org.anasoid.impexia.importing.service;

import org.anasoid.impexia.core.data.importing.DataReader;
import org.anasoid.impexia.core.data.importing.HeaderReader;
import org.anasoid.impexia.core.parser.header.HeaderParser;
import org.anasoid.impexia.meta.header.ImpexHeader;
import org.anasoid.impexia.meta.modifier.Scope;

public abstract class AbstractImpexiaImportingService {

  void importData(HeaderReader headerReader, DataReader dataReader, int maxPass) {
    parseHeader(headerReader.getHeader());
  }

  @SuppressWarnings("PMD.UseVarargs")
  protected ImpexHeader parseHeader(String[] headerRecords) {
    return HeaderParser.parseHeaderRow(headerRecords);
  }

  protected abstract Scope getScope();
}
