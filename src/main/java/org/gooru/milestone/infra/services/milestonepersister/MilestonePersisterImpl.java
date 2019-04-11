package org.gooru.milestone.infra.services.milestonepersister;

import java.util.List;
import java.util.UUID;
import org.gooru.milestone.infra.data.MilestoneAnalyticsModel;
import org.gooru.milestone.infra.data.MilestoneLessonMapModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This module is responsible to persist the models list in Core DB and in Analytics DB.
 *
 * In analytics DB, this module first cleans up the existing milestones and populates it  based on
 * latest values present in Core DB.
 *
 * @author ashish.
 */

class MilestonePersisterImpl implements MilestonePersister {

  private final DbiRegistry dbiRegistry;
  private final UUID courseId;
  private final String fwCode;
  private List<MilestoneLessonMapModel> models;
  private List<MilestoneAnalyticsModel> analyticsModels;
  private MilestonePersisterCoreDao coreDao;
  private MilestonePersisterAnalyticsDao analyticsDao;
  private static final Logger LOGGER = LoggerFactory.getLogger(MilestonePersister.class);

  MilestonePersisterImpl(DbiRegistry dbiRegistry, UUID courseId, String fwCode) {

    this.dbiRegistry = dbiRegistry;
    this.courseId = courseId;
    this.fwCode = fwCode;
  }

  @Override
  public void persistMilestones(List<MilestoneLessonMapModel> models) {

    this.models = models;
    process();
  }

  private void process() {
    LOGGER.debug("Processing of persist milestones");
    persistMilestonesInCore();
    initializeMilestoneAnalyticsModelsFromCore();
    cleanupMilestonesFromAnalytics();
    persistMilestoneInAnalytics();
  }

  private void persistMilestoneInAnalytics() {
    LOGGER.debug("Persisting milestones in analytics");
    fetchAnalyticsDao().persistMilestonesInAnalytics(analyticsModels);
  }

  private void cleanupMilestonesFromAnalytics() {
    LOGGER.debug("Cleaning up milestones from analytics");
    fetchAnalyticsDao().cleanupExistingMilestoneForCourseAndFw(courseId, fwCode);
  }

  private void initializeMilestoneAnalyticsModelsFromCore() {
    LOGGER.debug("Fetching milestones from core to be updated in analytics");
    analyticsModels = fetchCoreDao().fetchAnalyticsModelsFromCore(courseId, fwCode);
  }

  private void persistMilestonesInCore() {
    LOGGER.debug("Persisting milestones in core");
    fetchCoreDao().persistMilestones(models);
  }

  private MilestonePersisterAnalyticsDao fetchAnalyticsDao() {
    if (analyticsDao == null) {
      analyticsDao = dbiRegistry.getAnalyticsDbi().onDemand(MilestonePersisterAnalyticsDao.class);
    }
    return analyticsDao;
  }

  private MilestonePersisterCoreDao fetchCoreDao() {
    if (coreDao == null) {
      coreDao = dbiRegistry.getNucleusDbi().onDemand(MilestonePersisterCoreDao.class);
    }
    return coreDao;
  }

}
