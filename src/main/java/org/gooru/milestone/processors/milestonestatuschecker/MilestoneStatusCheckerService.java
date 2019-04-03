package org.gooru.milestone.processors.milestonestatuschecker;

import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class MilestoneStatusCheckerService {

  private final DbiRegistry dbiRegistry;
  private MilestoneStatusCheckerCommand command;
  private static final Logger LOGGER = LoggerFactory.getLogger(MilestoneStatusCheckerService.class);

  MilestoneStatusCheckerService(DbiRegistry dbiRegistry) {
    this.dbiRegistry = dbiRegistry;
  }


  boolean checkStatusForMilestoneDone(MilestoneStatusCheckerCommand command) {
    LOGGER.debug(command.toString());
    return false;
  }
}
