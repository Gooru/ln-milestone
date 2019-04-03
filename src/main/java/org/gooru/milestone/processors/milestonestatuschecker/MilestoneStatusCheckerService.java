package org.gooru.milestone.processors.milestonestatuschecker;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class MilestoneStatusCheckerService {

  private final DbiRegistry dbiRegistry;
  private MilestoneStatusCheckerCommand command;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(MilestoneStatusCheckerService.class);
  private MilestoneStatusCheckerDao milestoneQueueDao;
  private List<UUID> members;
  private Set<String> usersSet;

  MilestoneStatusCheckerService(DbiRegistry dbiRegistry) {
    this.dbiRegistry = dbiRegistry;
  }


  boolean checkStatusForMilestoneDone(MilestoneStatusCheckerCommand command) {
    return false;
  }
}
