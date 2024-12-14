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

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Product {

  @Id()
  @Column(unique = true)
  private String code;

  @Version
  private long version;

  private String name;
  @ElementCollection
  private List<String> keywords = new ArrayList<>();

  @ManyToMany(mappedBy = "products")
  private List<Category> categories = new ArrayList<>();

  @ElementCollection()
  private Map<String, Long> prices = new HashMap<>(); // NOPMD

  public String getCode() {
    return code;
  }

  public long getVersion() {
    return version;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getKeywords() {
    return keywords;
  }

  public void setKeywords(List<String> keywords) {
    this.keywords = keywords;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public Map<String, Long> getPrices() {
    return prices;
  }

  public void setPrices(Map<String, Long> prices) {
    this.prices = prices;
  }
}
