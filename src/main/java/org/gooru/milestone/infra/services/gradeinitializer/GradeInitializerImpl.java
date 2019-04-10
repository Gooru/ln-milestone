package org.gooru.milestone.infra.services.gradeinitializer;

import java.util.Collections;
import java.util.List;
import org.gooru.milestone.infra.data.GradeModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

public class GradeInitializerImpl implements GradeInitializer {

  private final DbiRegistry dbiRegistry;
  private GradeInitializerDao dsdbDao;
  private static final Logger LOGGER = LoggerFactory.getLogger(GradeInitializer.class);

  GradeInitializerImpl(DbiRegistry dbiRegistry) {

    this.dbiRegistry = dbiRegistry;
  }

  @Override
  public List<GradeModel> initializeGrades(String subjectCode, String fwCode) {
    if (subjectCode == null || fwCode == null) {
      LOGGER.warn("Invalid or incorrect (null) subjectCode or fwCode");
      return Collections.emptyList();
    }
    List<GradeModel> models = fetchDsdbDao().fetchGradeModelsForSubjectAndFw(subjectCode, fwCode);
    if (models != null) {
      return models;
    }
    return Collections.emptyList();
  }

  private GradeInitializerDao fetchDsdbDao() {
    if (dsdbDao == null) {
      dsdbDao = dbiRegistry.getDsdbDbi().onDemand(GradeInitializerDao.class);
    }
    return dsdbDao;
  }

}
