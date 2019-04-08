package org.gooru.milestone.infra.services;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.data.ProcessingContext;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.gooru.milestone.infra.services.milestonecleaner.MilestoneCleaner;
import org.gooru.milestone.infra.services.queueoperators.ProcessingEligibilityVerifier;
import org.gooru.milestone.infra.services.queueoperators.RequestDequeuer;
import org.gooru.milestone.infra.services.subjectinferer.SubjectInferer;
import org.gooru.milestone.infra.services.validators.ContextValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @formatter:off
/**
 *
 * - Start
 *
 * - End
 *
 * @author ashish.
 */
// @formatter:on

class QueueRecordProcessingServiceImpl implements QueueRecordProcessingService {

  private final DbiRegistry dbiRegistry;
  private MilestoneQueueModel model;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(QueueRecordProcessingServiceImpl.class);
  private ProcessingContext context;

  QueueRecordProcessingServiceImpl(DbiRegistry dbiRegistry) {
    this.dbiRegistry = dbiRegistry;
  }

  @Override
  public void processQueueRecord(MilestoneQueueModel model) {
    this.model = model;
    if (!ProcessingEligibilityVerifier.build(dbiRegistry.getNucleusDbi(), model.getOverride())
        .isEligibleForProcessing(model)) {
      LOGGER.debug("Record is not found to be in dispatched state, may be processed already.");
      dequeueRecord();
      return;
    }
    processRecord();
  }

  private void dequeueRecord() {
    LOGGER.debug("Dequeueing record");
    RequestDequeuer.build(dbiRegistry.getNucleusDbi()).dequeue(model);
  }

  private void processRecord() {
    LOGGER.debug("Doing real processing");
    context = ProcessingContext.buildFromQueueModel(model);

    try {
      preprocess();
      process();
      doPostProcessing();
    } catch (Throwable e) {
      LOGGER.warn("Not able to calculate milestone for model: '{}'. Will dequeue record.",
          model.toJson(), e);
      throw new IllegalStateException(
          "Not able to calculate milestone for model: " + model.toJson(), e);
    } finally {
      dequeueRecord();
    }
  }

  private void process() {
    // TODO : Implement this
  }


  private void preprocess() {
    initialize();
    validate();
    handleOverride();
  }

  private void handleOverride() {
    if (model.getOverride()) {
      MilestoneCleaner.build(dbiRegistry).cleanupMilestones(model.getCourseId(), model.getFwCode());
    }
  }

  private void doPostProcessing() {
    // TODO : Implement this
  }

  private void validate() {
    ContextValidator.build(dbiRegistry).validate(context);
  }

  private void initialize() {
    initializeSubject();
  }

  private void initializeSubject() {
    String subject = SubjectInferer.build(dbiRegistry.getNucleusDbi())
        .inferSubjectForCourse(context.getCourseId());
    context.setSubject(subject);
  }

}
