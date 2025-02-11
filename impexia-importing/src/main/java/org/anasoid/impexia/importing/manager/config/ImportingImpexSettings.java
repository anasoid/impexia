/*
 * Copyright 2020-2024 the original author or authors.
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
 * Date :   23-Dec-2024
 */

package org.anasoid.impexia.importing.manager.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.impexia.core.manager.config.AbstractImpexSettings;

@Getter
@Setter
@SuperBuilder
@SuppressWarnings({"PMD.AbstractClassWithoutAbstractMethod", "PMD.AbstractClassWithoutAnyMethod"})
public class ImportingImpexSettings extends AbstractImpexSettings {
  private static final String PREFIX = "settings.import.";
  public static final String CONFIG_LINE_IGNORE_ADDITIONAL_COLUMN =
      PREFIX + "line.ignore.additional.data";
  public static final String CONFIG_LINE_MISSING_COLUMN_AS_NULL =
      PREFIX + "line.missing.column.as.null";

  private Boolean lineIgnoreAdditionalColumn;
  private Boolean lineMissingColumnAsNull;
}
