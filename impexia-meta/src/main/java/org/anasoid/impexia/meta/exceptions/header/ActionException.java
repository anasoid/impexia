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
 * Date :   03-Nov-2020
 */

package org.anasoid.impexia.meta.exceptions.header;

/** Exception to be throw when validation of modifier. */
public class ActionException extends ImpexHeaderException {
  static final long serialVersionUID = -3387516993124229948L;

  public ActionException(String action) {
    super("Action in header not Valid :" + action);
  }

  public ActionException() {
    super("Action in header can't be null or empty");
  }
}
