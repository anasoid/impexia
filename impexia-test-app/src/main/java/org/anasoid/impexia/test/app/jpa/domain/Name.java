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
 * Date :   12-Dec-2020
 */

package org.anasoid.impexia.test.app.jpa.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

  public Name() {}

  /**
   * Constructors.
   */
  public Name(String first, String middle, String last) {
    this.first = first;
    this.middle = middle;
    this.last = last;
  }

  private String first;

  private String middle;

  private String last;

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public String getMiddle() {
    return middle;
  }

  public void setMiddle(String middle) {
    this.middle = middle;
  }

  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }
}
