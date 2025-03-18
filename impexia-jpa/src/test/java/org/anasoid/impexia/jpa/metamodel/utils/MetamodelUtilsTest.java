package org.anasoid.impexia.jpa.metamodel.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.Optional;
import org.anasoid.impexia.test.app.jpa.EntityManagerUtil;
import org.anasoid.impexia.test.app.jpa.domain.Category;
import org.junit.jupiter.api.Test;

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

class MetamodelUtilsTest {

  @Test
  void testEntityByName() {

    try (EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager()) {

      MetamodelUtils metamodelUtils = new MetamodelUtils(em);
      assertThat(metamodelUtils.getEntityByName("None")).isEmpty();
      assertThat(metamodelUtils.getEntityByName("Category")).isNotEmpty().contains(Category.class);
    }
  }

  @Test
  void testAttributeName() {

    try (EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager()) {

      MetamodelUtils metamodelUtils = new MetamodelUtils(em);
      Optional<SingularAttribute<?, ?>> noneAttribute =
          metamodelUtils.getAttribute(Category.class, "none");
      Optional<SingularAttribute<?, ?>> nameAttribute =
          metamodelUtils.getAttribute(Category.class, "name");
      assertThat(noneAttribute).isEmpty();
      assertThat(nameAttribute).isNotEmpty();
    }
  }
}
