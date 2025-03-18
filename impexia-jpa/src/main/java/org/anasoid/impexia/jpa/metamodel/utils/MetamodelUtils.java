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
 * Date :   16-Mar-2025
 */

package org.anasoid.impexia.jpa.metamodel.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.Optional;

public class MetamodelUtils {
  private final EntityManager entityManager;

  public MetamodelUtils(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Optional<Class<?>> getEntityByName(String className) {
    Metamodel metamodel = entityManager.getMetamodel();
    for (EntityType<?> entityType : metamodel.getEntities()) {
      if (entityType.getName().equals(className)) {
        return Optional.of(entityType.getJavaType());
      }
    }
    return Optional.empty();
  }

  public Optional<SingularAttribute<?, ?>> getAttribute(
      Class<?> entityClass, String attributeName) {
    Metamodel metamodel = entityManager.getMetamodel();
    EntityType<?> entityType = metamodel.entity(entityClass);

    for (SingularAttribute<?, ?> attribute : entityType.getSingularAttributes()) {
      if (attribute.getName().equals(attributeName)) {
        return Optional.of(attribute);
      }
    }
    return Optional.empty();
  }
}
