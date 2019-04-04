package org.gooru.milestone.processors.milestonequeuer;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.gooru.milestone.infra.services.queuerequest.RequestQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class MilestoneQueueService {

  private final DbiRegistry dbiRegistry;
  private MilestoneQueueCommand command;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(MilestoneQueueService.class);
  private MilestoneQueueDao milestoneNucleusQueueDao;
  private MilestoneQueueDao milestoneDsDbQueueDao;

  MilestoneQueueService(DbiRegistry dbiRegistry) {
    this.dbiRegistry = dbiRegistry;
  }

  void enqueue(MilestoneQueueCommand command) {
    this.command = command;
    LOGGER.debug("Command is : {}", command.toString());
    validate();
    RequestQueueService.build(DbiRegistry.build())
        .enqueue(convertCommandToModel());
  }

  private MilestoneQueueModel convertCommandToModel() {
    MilestoneQueueModel model = new MilestoneQueueModel();
    model.setCourseId(command.getCourseId());
    model.setFwCode(command.getFwCode());
    model.setOverride(command.isOverride());
    model.setStatus(MilestoneQueueModel.RQ_STATUS_QUEUED);
    return model;
  }

  private void validate() {
    validateCourseAndIsPremium();
    validateFwCode();
  }

  private void validateFwCode() {
    if (!fetchDsDbDao().validateFWAndGutMapping(command.getFwCode())) {
      throw new IllegalArgumentException("FW code is invalid or not gut mapped");
    }
  }

  private void validateCourseAndIsPremium() {
    if (!fetchNucleusDao().validateCourseAndItsPremiumness(command.getCourseId())) {
      throw new IllegalArgumentException("Invalid or non premium course for specified class");
    }
  }

  private MilestoneQueueDao fetchDsDbDao() {
    if (milestoneDsDbQueueDao == null) {
      milestoneDsDbQueueDao = dbiRegistry.getDsdbDbi().onDemand(MilestoneQueueDao.class);
    }
    return milestoneDsDbQueueDao;
    
  }
  
  private MilestoneQueueDao fetchNucleusDao() {
    if (milestoneNucleusQueueDao == null) {
      milestoneNucleusQueueDao = dbiRegistry.getNucleusDbi().onDemand(MilestoneQueueDao.class);
    }
    return milestoneNucleusQueueDao;
  }
}
