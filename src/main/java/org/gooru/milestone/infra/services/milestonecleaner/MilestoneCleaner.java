package org.gooru.milestone.infra.services.milestonecleaner;

import java.util.UUID;
import org.gooru.milestone.infra.jdbi.DbiRegistry;

/**
 * @author ashish.
 */

public interface MilestoneCleaner {

  void cleanupMilestones(UUID courseId, String fwCode);

  void cleanupMilestone(Long milestoneId);

  static MilestoneCleaner build() {
    return new MilestoneCleanerImpl(DbiRegistry.build());
  }

  static MilestoneCleaner build(DbiRegistry dbiRegistry) {
    return new MilestoneCleanerImpl(dbiRegistry);
  }

}
