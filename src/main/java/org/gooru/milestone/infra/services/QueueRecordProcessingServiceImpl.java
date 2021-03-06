package org.gooru.milestone.infra.services;

import java.util.List;
import org.gooru.milestone.infra.data.CulModel;
import org.gooru.milestone.infra.data.GradeCompetencyMapModel;
import org.gooru.milestone.infra.data.GradeModel;
import org.gooru.milestone.infra.data.MilestoneLessonMapModel;
import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.data.ProcessingContext;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.gooru.milestone.infra.services.calculator.MilestoneCalculator;
import org.gooru.milestone.infra.services.contentinitializer.CulModelsInitializer;
import org.gooru.milestone.infra.services.gcminitializer.GcmInitializer;
import org.gooru.milestone.infra.services.gradeinitializer.GradeInitializer;
import org.gooru.milestone.infra.services.lookups.GcmLookup;
import org.gooru.milestone.infra.services.lookups.GradeLookup;
import org.gooru.milestone.infra.services.milestonecleaner.MilestoneCleaner;
import org.gooru.milestone.infra.services.milestonepersister.MilestonePersister;
import org.gooru.milestone.infra.services.queueoperators.ProcessingEligibilityVerifier;
import org.gooru.milestone.infra.services.queueoperators.RequestDequeuer;
import org.gooru.milestone.infra.services.subjectinferer.SubjectInferer;
import org.gooru.milestone.infra.services.validators.ContextValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ashish.
 */

class QueueRecordProcessingServiceImpl implements QueueRecordProcessingService {

  private final DbiRegistry dbiRegistry;
  private MilestoneQueueModel model;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(QueueRecordProcessingServiceImpl.class);
  private ProcessingContext context;
  private List<CulModel> culModels;
  private List<GradeModel> gradeModels;
  private List<GradeCompetencyMapModel> gradeCompetencyMapModels;
  private GcmLookup gcmLookup;
  private GradeLookup gradeLookup;
  private List<MilestoneLessonMapModel> milestones;

  QueueRecordProcessingServiceImpl(DbiRegistry dbiRegistry) {
    this.dbiRegistry = dbiRegistry;
  }

  @Override
  public void processQueueRecord(MilestoneQueueModel model) {
    this.model = model;
    LOGGER.info("Processing for model: '{}", model.toJson());
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
    LOGGER.debug("Doing initialize of CUL models in process");
    initializeCulModels();
    LOGGER.debug("Doing initialize of GCM models in process");
    initializeGCM();
    LOGGER.debug("Doing initialize of Grade models in process");
    initializeGrades();
    LOGGER.debug("Doing initialize of Lookups in process");
    initializeLookups();
    LOGGER.debug("Doing creation of Milestones in process");
    createMilestones();
    LOGGER.debug("Persisting of Milestone models in process");
    persistMilestones();
  }

  private void persistMilestones() {
    MilestonePersister.build(dbiRegistry, context.getCourseId(), context.getFwCode())
        .persistMilestones(milestones);
  }

  private void createMilestones() {
    milestones = MilestoneCalculator.build(context, gradeLookup, gcmLookup)
        .calculateMilestones(culModels);
  }

  private void initializeLookups() {
    gcmLookup = GcmLookup.build(gradeCompetencyMapModels);
    gradeLookup = GradeLookup.build(gradeModels);
  }

  private void initializeGrades() {
    gradeModels = GradeInitializer.build(dbiRegistry)
        .initializeGrades(context.getSubject(), context.getFwCode());

  }

  private void initializeGCM() {
    gradeCompetencyMapModels = GcmInitializer.build(dbiRegistry)
        .initializeGcmModels(context.getSubject(), context.getFwCode());

  }

  private void initializeCulModels() {
    culModels = CulModelsInitializer.build(dbiRegistry)
        .initializeCulModelsForCourse(context.getCourseId());
  }


  private void preprocess() {
    LOGGER.debug("Doing initialize in pre-process");
    initialize();
    LOGGER.debug("Doing validate in pre-process");
    validate();
    LOGGER.debug("Doing handleOverride in pre-process");
    handleOverride();
  }

  private void handleOverride() {
    if (model.getOverride()) {
      LOGGER.debug("Override is set, will clean up main table");
      MilestoneCleaner.build(dbiRegistry).cleanupMilestones(model.getCourseId(), model.getFwCode());
    }
  }

  private void doPostProcessing() {
    // Currently a NO-OP
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
    LOGGER.info("Found subject: '{}' for course : '{}", subject, context.getCourseId());
    context.setSubject(subject);
  }

}
