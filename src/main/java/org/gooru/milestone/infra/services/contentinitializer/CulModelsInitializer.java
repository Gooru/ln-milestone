package org.gooru.milestone.infra.services.contentinitializer;

import java.util.List;
import java.util.UUID;
import org.gooru.milestone.infra.data.CulModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;

/**
 * @author ashish.
 */

public interface CulModelsInitializer {

  List<CulModel> initializeCulModelsForCourse(UUID courseId);

  static CulModelsInitializer build(DbiRegistry dbiRegistry) {
    return new CulModelsInitializerImpl(dbiRegistry);
  }

}
