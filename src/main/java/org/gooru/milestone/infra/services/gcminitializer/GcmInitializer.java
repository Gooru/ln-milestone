package org.gooru.milestone.infra.services.gcminitializer;

import java.util.List;
import org.gooru.milestone.infra.data.GradeCompetencyMapModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;

/**
 * @author ashish.
 */

public interface GcmInitializer {

  List<GradeCompetencyMapModel> initializeGcmModels(String subjectCode, String fwCode);

  static GcmInitializer build(DbiRegistry dbiRegistry) {
    return new GcmInitializerImpl(dbiRegistry);
  }

  static GcmInitializer build() {
    return new GcmInitializerImpl(DbiRegistry.build());
  }

}
