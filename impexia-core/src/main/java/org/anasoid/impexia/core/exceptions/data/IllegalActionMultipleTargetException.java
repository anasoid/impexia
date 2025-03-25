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
 * Date :   30-Nov-2020
 */

package org.anasoid.impexia.core.exceptions.data;

/**
 * Exception for multiple target action.
 *
 * <p>Throw when action is not allowed to have multiple target.
 *
 * <p>Use batchmode to allow multiple target.
 *
 * @see org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnumGlobal
 */
public class IllegalActionMultipleTargetException extends Exception {
  static final long serialVersionUID = -3387554563124229948L;

  public IllegalActionMultipleTargetException() {
    super("Multiple item found for action");
  }

  public IllegalActionMultipleTargetException(String message) {
    super(message);
  }

  public IllegalActionMultipleTargetException(String message, Throwable cause) {
    super(message, cause);
  }
}
