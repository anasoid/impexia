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
 * Date :   14-Dec-2020
 */

package org.anasoid.impexia.test.app.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Entity Manager Util Only for Test.
 */
public final class EntityManagerUtil {

  // USE THE SAME NAME IN persistence.xml!
  public static final String PERSISTENCE_UNIT_NAME = "test-app-jpa";

  private static EntityManager entityManager;

  private EntityManagerUtil() {
  }

  /**
   * get (test-app-jpa) EntityManager.
   *
   * @return
   */
  @SuppressWarnings("PMD.CloseResource")
  public static EntityManager getEntityManager() {
    if (entityManager == null) {
      // the same in persistence.xml
      EntityManagerFactory emFactory =
          Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

      return emFactory.createEntityManager();
    }
    return entityManager;
  }
}
