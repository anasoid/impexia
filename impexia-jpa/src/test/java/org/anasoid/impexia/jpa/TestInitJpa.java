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

package org.anasoid.impexia.jpa;

import java.util.Objects;
import javax.persistence.EntityManager;
import org.anasoid.impexia.test.app.jpa.EntityManagerUtil;
import org.anasoid.impexia.test.app.jpa.domain.Name;
import org.anasoid.impexia.test.app.jpa.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestInitJpa {
  @Test
  void testSaveUser() {

    EntityManager em = EntityManagerUtil.getEntityManager();
    em.getTransaction().begin();
    User user = new User();
    user.setEmail("test@test.com");
    user.setName(new Name("first", "middle", "last"));
    em.persist(user);
    em.getTransaction().commit();
    Assertions.assertTrue(!Objects.isNull(user.getId()));
  }
}
