package org.gooru.milestone.infra.services.gcminitializer;

import java.util.Collections;
import java.util.List;
import org.gooru.milestone.infra.data.GradeCompetencyMapModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

public class GcmInitializerImpl implements GcmInitializer {

  private final DbiRegistry dbiRegistry;
  private GcmInitializerDao dsdbDao;
  private static final Logger LOGGER = LoggerFactory.getLogger(GcmInitializer.class);

  GcmInitializerImpl(DbiRegistry dbiRegistry) {

    this.dbiRegistry = dbiRegistry;
  }

  @Override
  public List<GradeCompetencyMapModel> initializeGcmModels(String subjectCode, String fwCode) {
    if (subjectCode == null || fwCode == null) {
      LOGGER.warn("Invalid or incorrect (null) subjectCode or fwCode");
      return Collections.emptyList();
    }
    List<GradeCompetencyMapModel> models = fetchDsdbDao()
        .initializeGcmForSubjectAndFw(subjectCode, fwCode);
    if (models != null) {
      return models;
    }
    return Collections.emptyList();
  }

  private GcmInitializerDao fetchDsdbDao() {
    if (dsdbDao == null) {
      dsdbDao = dbiRegistry.getDsdbDbi().onDemand(GcmInitializerDao.class);
    }
    return dsdbDao;
  }
}
