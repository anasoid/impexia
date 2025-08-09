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
 * Date :   11-Dec-2020
 */

package org.anasoid.impexia.test.app.jpa.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** only for test. */
@Entity
public class User {

  @Id
  @GeneratedValue
  @Column(unique = true, updatable = false)
  private UUID id;

  @Version private long version;

  private String email;
  private Name name;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createDate;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime modifyDate;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Phone> phones = new ArrayList<>();

  public UUID getId() {
    return id;
  }

  public long getVersion() {
    return version;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public List<Phone> getPhones() {
    return phones;
  }

  public void setPhones(List<Phone> phones) {
    this.phones = phones;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public LocalDateTime getModifyDate() {
    return modifyDate;
  }

  @PrePersist
  protected void onCreate() {
    createDate = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    modifyDate = LocalDateTime.now();
  }
}
