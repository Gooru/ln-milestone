package org.gooru.milestone.infra.services.contentinitializer;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.gooru.milestone.infra.data.CulModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;

/**
 * @author ashish.
 */

public class CulModelsInitializerImpl implements CulModelsInitializer {

  private final DbiRegistry dbiRegistry;
  private CulModelsInitializerDao coreDao;

  CulModelsInitializerImpl(DbiRegistry dbiRegistry) {

    this.dbiRegistry = dbiRegistry;
  }

  @Override
  public List<CulModel> initializeCulModelsForCourse(UUID courseId) {
    return fetchModelsFromDb(courseId);
  }

  private List<CulModel> fetchModelsFromDb(UUID courseId) {
    List<CulModel> models = fetchCoreDao().fetchCulModelsForCourse(courseId);
    if (models != null) {
      return models;
    }
    return Collections.emptyList();
  }

  private CulModelsInitializerDao fetchCoreDao() {
    if (coreDao == null) {
      coreDao = dbiRegistry.getNucleusDbi().onDemand(CulModelsInitializerDao.class);
    }
    return coreDao;
  }
}
