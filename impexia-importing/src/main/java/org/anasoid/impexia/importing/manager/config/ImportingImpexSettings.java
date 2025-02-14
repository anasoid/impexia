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
import org.anasoid.impexia.core.manager.config.AbstractImpexSettings;

@Getter
@Setter
@SuppressWarnings({"PMD.AbstractClassWithoutAbstractMethod", "PMD.AbstractClassWithoutAnyMethod"})
public class ImportingImpexSettings extends AbstractImpexSettings {
  private static final String PREFIX = "settings.import.";
  public static final String CONFIG_LINE_IGNORE_ADDITIONAL_COLUMN =
      PREFIX + "line.ignore.additional.data";
  public static final String CONFIG_LINE_MISSING_COLUMN_AS_NULL =
      PREFIX + "line.missing.column.as.null";

  private Boolean lineIgnoreAdditionalColumn;
  private Boolean lineMissingColumnAsNull;

  protected ImportingImpexSettings(ImportingImpexSettingsBuilder<?, ?> b) {
    super(b);
    this.lineIgnoreAdditionalColumn = b.lineIgnoreAdditionalColumn;
    this.lineMissingColumnAsNull = b.lineMissingColumnAsNull;
  }

  public static ImportingImpexSettingsBuilder<?, ?> builder() {
    return new ImportingImpexSettingsBuilderImpl();
  }

  public ImportingImpexSettingsBuilder<?, ?> toBuilder() {
    return new ImportingImpexSettingsBuilderImpl().$fillValuesFrom(this);
  }

  public static abstract class ImportingImpexSettingsBuilder<C extends ImportingImpexSettings, B extends ImportingImpexSettingsBuilder<C, B>> extends
      AbstractImpexSettingsBuilder<C, B> {

    private Boolean lineIgnoreAdditionalColumn;
    private Boolean lineMissingColumnAsNull;

    private static void $fillValuesFromInstanceIntoBuilder(ImportingImpexSettings instance,
        ImportingImpexSettingsBuilder<?, ?> b) {
      b.lineIgnoreAdditionalColumn(instance.lineIgnoreAdditionalColumn);
      b.lineMissingColumnAsNull(instance.lineMissingColumnAsNull);
    }

    public B lineIgnoreAdditionalColumn(Boolean lineIgnoreAdditionalColumn) {
      this.lineIgnoreAdditionalColumn = lineIgnoreAdditionalColumn;
      return self();
    }

    public B lineMissingColumnAsNull(Boolean lineMissingColumnAsNull) {
      this.lineMissingColumnAsNull = lineMissingColumnAsNull;
      return self();
    }

    protected abstract B self();

    public abstract C build();

    public String toString() {
      return "ImportingImpexSettings.ImportingImpexSettingsBuilder(super=" + super.toString()
          + ", lineIgnoreAdditionalColumn=" + this.lineIgnoreAdditionalColumn
          + ", lineMissingColumnAsNull=" + this.lineMissingColumnAsNull + ")";
    }

    protected B $fillValuesFrom(C instance) {
      super.$fillValuesFrom(instance);
      ImportingImpexSettingsBuilder.$fillValuesFromInstanceIntoBuilder(instance, this);
      return self();
    }
  }

  private static final class ImportingImpexSettingsBuilderImpl extends
      ImportingImpexSettingsBuilder<ImportingImpexSettings, ImportingImpexSettingsBuilderImpl> {

    private ImportingImpexSettingsBuilderImpl() {
    }

    protected ImportingImpexSettingsBuilderImpl self() {
      return this;
    }

    public ImportingImpexSettings build() {
      return new ImportingImpexSettings(this);
    }
  }
}
