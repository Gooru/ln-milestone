package org.gooru.milestone.infra.services;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.data.ProcessingContext;
import org.gooru.milestone.infra.services.queueoperators.ProcessingEligibilityVerifier;
import org.gooru.milestone.infra.services.queueoperators.RequestDequeuer;
import org.gooru.milestone.infra.services.subjectinferer.SubjectInferer;
import org.gooru.milestone.infra.services.validators.ContextValidator;
import org.skife.jdbi.v2.DBI;
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

  private final DBI dbi4core;
  private final DBI dbi4ds;
  private MilestoneQueueModel model;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(QueueRecordProcessingServiceImpl.class);
  private ProcessingContext context;

  QueueRecordProcessingServiceImpl(DBI dbi4core, DBI dbi4ds) {
    this.dbi4core = dbi4core;
    this.dbi4ds = dbi4ds;
  }

  @Override
  public void processQueueRecord(MilestoneQueueModel model) {
    this.model = model;
    if (!ProcessingEligibilityVerifier.build(dbi4core)
        .isEligibleForProcessing(model)) {
      LOGGER.debug("Record is not found to be in dispatched state, may be processed already.");
      dequeueRecord();
      return;
    }
    processRecord();
  }

  private void dequeueRecord() {
    LOGGER.debug("Dequeueing record");
    RequestDequeuer.build(dbi4core).dequeue(model);
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
    validate();
    initialize();
  }

  private void doPostProcessing() {
    // TODO : Implement this
  }

  private void validate() {
    ContextValidator.build(dbi4core, dbi4ds).validate(context);
  }

  private void initialize() {
    initializeSubject();
  }

  private void initializeSubject() {
    String subject = SubjectInferer.build(dbi4core).inferSubjectForCourse(context.getCourseId());
    context.setSubject(subject);
  }

}
