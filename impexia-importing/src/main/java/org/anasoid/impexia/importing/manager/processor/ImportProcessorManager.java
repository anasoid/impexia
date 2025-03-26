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
 * Date :   23-Mar-2025
 */

package org.anasoid.impexia.importing.manager.processor;

import java.util.Iterator;
import org.anasoid.impexia.core.exceptions.data.IllegalActionItemAlreadyExistsException;
import org.anasoid.impexia.core.exceptions.data.IllegalActionMultipleTargetException;
import org.anasoid.impexia.core.exceptions.data.IllegalActionNoItemFoundException;
import org.anasoid.impexia.core.manager.values.LineValues;
import org.anasoid.impexia.core.utils.ImpexModifierUtils;
import org.anasoid.impexia.importing.internal.spi.datasource.DataSourceContainer;
import org.anasoid.impexia.importing.internal.spi.processor.ImportProcessor;
import org.anasoid.impexia.importing.manager.config.ImportingImpexContext;
import org.anasoid.impexia.importing.manager.processor.header.ImportHeaderProcessor;

public class ImportProcessorManager<
    S extends DataSourceContainer<?, ?, ?>, C extends ImportingImpexContext<?>, R, T> {

  private ImportProcessor<S, C, R, T> importProcessor;

  @SuppressWarnings("PMD.ExhaustiveSwitchHasDefault")
  public void process(
      S datasource, ImportHeaderProcessor importHeaderProcessor, C context, LineValues lineValues)
      throws IllegalActionMultipleTargetException,
          IllegalActionItemAlreadyExistsException,
          IllegalActionNoItemFoundException {

    switch (importHeaderProcessor.getImpexHeader().getAction()) {
      case INSERT:
        this.insert(datasource, importHeaderProcessor, context, lineValues);
        break;
      case UPDATE:
        this.update(datasource, importHeaderProcessor, context, lineValues);
        break;
      case INSERT_UPDATE:
        this.insertOrUpdate(datasource, importHeaderProcessor, context, lineValues);
        break;
      case REMOVE:
        this.remove(datasource, importHeaderProcessor, context, lineValues);
        break;
      default:
        break;
    }
  }

  protected void insert(
      S datasource, ImportHeaderProcessor importHeaderProcessor, C context, LineValues lineValues)
      throws IllegalActionItemAlreadyExistsException {
    Iterator<R> iteratorItemReference =
        this.load(datasource, importHeaderProcessor, context, lineValues);
    if (!iteratorItemReference.hasNext()) {
      importProcessor.insert(datasource, importHeaderProcessor, context, lineValues);
    } else {
      throw new IllegalActionItemAlreadyExistsException();
    }
  }

  protected int update(
      S datasource, ImportHeaderProcessor importHeaderProcessor, C context, LineValues lineValues)
      throws IllegalActionMultipleTargetException, IllegalActionNoItemFoundException {
    Iterator<R> iteratorItemReference =
        this.load(datasource, importHeaderProcessor, context, lineValues);
    if (iteratorItemReference.hasNext()) {
      return update(datasource, importHeaderProcessor, context, iteratorItemReference, lineValues);
    } else {
      throw new IllegalActionNoItemFoundException();
    }
  }

  private int update(
      S datasource,
      ImportHeaderProcessor importHeaderProcessor,
      C context,
      Iterator<R> iteratorItemReference,
      LineValues lineValues)
      throws IllegalActionMultipleTargetException {

    R itemReference = iteratorItemReference.next();
    if (!ImpexModifierUtils.isBatchMode(importHeaderProcessor.getImpexHeader())
        && iteratorItemReference.hasNext()) {
      throw new IllegalActionMultipleTargetException();
    }

    int result =
        importProcessor.update(
            datasource, importHeaderProcessor, context, itemReference, lineValues);
    while (iteratorItemReference.hasNext()) {
      itemReference = iteratorItemReference.next();
      result +=
          importProcessor.update(
              datasource, importHeaderProcessor, context, itemReference, lineValues);
    }
    return result;
  }

  protected int insertOrUpdate(
      S datasource, ImportHeaderProcessor importHeaderProcessor, C context, LineValues lineValues)
      throws IllegalActionMultipleTargetException {
    Iterator<R> iteratorItemReference =
        this.load(datasource, importHeaderProcessor, context, lineValues);
    if (iteratorItemReference.hasNext()) {
      return update(datasource, importHeaderProcessor, context, iteratorItemReference, lineValues);
    } else {
      importProcessor.insert(datasource, importHeaderProcessor, context, lineValues);
      return 1;
    }
  }

  protected int remove(
      S datasource, ImportHeaderProcessor importHeaderProcessor, C context, LineValues lineValues)
      throws IllegalActionNoItemFoundException, IllegalActionMultipleTargetException {

    Iterator<R> iteratorItemReference =
        this.load(datasource, importHeaderProcessor, context, lineValues);
    if (iteratorItemReference.hasNext()) {
      R itemReference = iteratorItemReference.next();
      if (!ImpexModifierUtils.isBatchMode(importHeaderProcessor.getImpexHeader())
          && iteratorItemReference.hasNext()) {
        throw new IllegalActionMultipleTargetException();
      }

      int result =
          importProcessor.remove(
              datasource, importHeaderProcessor, context, itemReference, lineValues);
      while (iteratorItemReference.hasNext()) {
        itemReference = iteratorItemReference.next();
        result +=
            importProcessor.remove(
                datasource, importHeaderProcessor, context, itemReference, lineValues);
      }
      return result;
    } else {
      throw new IllegalActionNoItemFoundException();
    }
  }

  Iterator<R> load(
      S datasource, ImportHeaderProcessor importHeaderProcessor, C context, LineValues lineValues) {
    return importProcessor.load(datasource, importHeaderProcessor, context, lineValues);
  }
}
