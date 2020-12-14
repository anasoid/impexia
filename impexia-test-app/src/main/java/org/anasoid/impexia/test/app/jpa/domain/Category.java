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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Category {

  @Id
  @Column(unique = true)
  private String code;

  private String name;

  @ManyToOne() private Category superCategory;

  @OneToMany(mappedBy = "superCategory")
  private List<Category> subCategories;

  @ManyToMany()
  private List<Product> products = new ArrayList<>();

  public String getCode() {
    return code;
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

  public Category getSuperCategory() {
    return superCategory;
  }

  public void setSuperCategory(Category superCategory) {
    this.superCategory = superCategory;
  }

  public List<Category> getSubCategories() {
    return subCategories;
  }

  public void setSubCategories(List<Category> subCategories) {
    this.subCategories = subCategories;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }
}
