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
 * Date :   01-Jan-2025
 */

package org.anasoid.impexia.core.internal.spi.register;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import org.anasoid.impexia.core.register.ModifierRegistratorAgent;
import org.anasoid.impexia.core.validators.header.descriptor.modifier.ModifierDescriptorEnum;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractRegistrator {

  @Getter(AccessLevel.PROTECTED)
  protected List<RegistratorAgent> registratorAgents = new ArrayList<>();

  @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
  public AbstractRegistrator() {
    registratorAgents.add(ModifierRegistratorAgent.getInstance());
    RegistratorAgent<ModifierDescriptorEnum> modifierDescriptorAgent = getModifierDescriptorAgent();
    if (modifierDescriptorAgent != null) {
      getRegistratorAgents().add(modifierDescriptorAgent);
    }
  }

  public void load() {
    registratorAgents.forEach(RegistratorAgent::load);
  }

  public void unLoad() {
    registratorAgents.forEach(RegistratorAgent::unLoad);
  }

  protected abstract RegistratorAgent<ModifierDescriptorEnum> getModifierDescriptorAgent();
}
