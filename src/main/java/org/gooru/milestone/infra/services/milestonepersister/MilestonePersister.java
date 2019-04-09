package org.gooru.milestone.infra.services.milestonepersister;

import java.util.List;
import java.util.UUID;
import org.gooru.milestone.infra.data.MilestoneLessonMapModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;

/**
 * @author ashish.
 */

public interface MilestonePersister {

  void persistMilestones(List<MilestoneLessonMapModel> models);

  static MilestonePersister build(DbiRegistry dbiRegistry, UUID courseId, String fwCode) {
    return new MilestonePersisterImpl(dbiRegistry, courseId, fwCode);
  }

}
