package org.gooru.milestone.infra.services.gradeinitializer;

import java.util.List;
import org.gooru.milestone.infra.data.GradeModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;

/**
 * @author ashish.
 */

public interface GradeInitializer {

  List<GradeModel> initializeGrades(String subjectCode, String fwCode);

  static GradeInitializer build(DbiRegistry dbiRegistry) {
    return new GradeInitializerImpl(dbiRegistry);
  }

  static GradeInitializer build() {
    return new GradeInitializerImpl(DbiRegistry.build());
  }

}
