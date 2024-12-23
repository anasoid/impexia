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

package org.anasoid.impexia.importing.manager.transformer;

public enum TransformerOrder {
  HEADER_INIT(0),
  HEADER_FIRST(0),
  COLUMN_INIT(0),
  COLUMN_FIRST(0),
  COLUMN_END(0),
  HEADER_END(0);

  int order;

  TransformerOrder(int order) {
    this.order = order;
  }
}
