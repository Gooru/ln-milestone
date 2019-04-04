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
  private MilestoneStatusCheckerDao milestoneStatusCheckerDao;

  MilestoneStatusCheckerService(DbiRegistry dbiRegistry) {
    this.dbiRegistry = dbiRegistry;
  }


  boolean checkStatusForMilestoneDone(MilestoneStatusCheckerCommand command) {
    this.command = command;
    LOGGER.debug(command.toString());
    validate();
    return checkStatusDone(command);
  }

  private boolean checkStatusDone(MilestoneStatusCheckerCommand command) {
    return fetchDao().checkMilestoneDone(command.getCourseId(), command.getFwCode());
  }

  private void validate() {
    if (!fetchDao().validateCourseAndItsPremiumness(command.getCourseId())) {
      throw new IllegalArgumentException("Course is not valid or not premium");
    }
  }

  private MilestoneStatusCheckerDao fetchDao() {
    if (milestoneStatusCheckerDao == null) {
      milestoneStatusCheckerDao = dbiRegistry.getNucleusDbi()
          .onDemand(MilestoneStatusCheckerDao.class);
    }
    return milestoneStatusCheckerDao;
  }
}
