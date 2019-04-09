package org.gooru.milestone.infra.services.milestonepersister;

import java.util.List;
import java.util.UUID;
import org.gooru.milestone.infra.data.MilestoneAnalyticsModel;
import org.gooru.milestone.infra.data.MilestoneLessonMapModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;

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
    persistMilestonesInCore();
    initializeMilestoneAnalyticsModelsFromCore();
    cleanupMilestonesFromAnalytics();
    persistMilestoneInAnalytics();
  }

  private void persistMilestoneInAnalytics() {
    fetchAnalyticsDao().persistMilestonesInAnalytics(analyticsModels);
  }

  private void cleanupMilestonesFromAnalytics() {
    fetchAnalyticsDao().cleanupExistingMilestoneForCourseAndFw(courseId, fwCode);
  }

  private void initializeMilestoneAnalyticsModelsFromCore() {
    analyticsModels = fetchCoreDao().fetchAnalyticsModelsFromCore(courseId, fwCode);
  }

  private void persistMilestonesInCore() {
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
