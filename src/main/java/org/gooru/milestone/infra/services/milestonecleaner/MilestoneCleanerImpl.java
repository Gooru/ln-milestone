package org.gooru.milestone.infra.services.milestonecleaner;

import java.util.UUID;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class MilestoneCleanerImpl implements MilestoneCleaner {

  private final DbiRegistry dbiRegistry;
  private MilestoneCleanerDao milestoneCleanerCoreDao;
  private static final Logger LOGGER = LoggerFactory.getLogger(MilestoneCleaner.class);

  MilestoneCleanerImpl(DbiRegistry dbiRegistry) {

    this.dbiRegistry = dbiRegistry;
  }

  @Override
  public void cleanupMilestones(UUID courseId, String fwCode) {
    if (courseId != null && fwCode != null) {
      fetchCoreDao().cleanupMilestones(courseId, fwCode);
    } else {
      LOGGER.warn("Invalid cleanup call. CourseId: '{}' and FwCode: '{}'", courseId, fwCode);
    }
  }

  @Override
  public void cleanupMilestone(Long milestoneId) {
    if (milestoneId != null) {
      fetchCoreDao().cleanupMilestone(milestoneId);
    } else {
      LOGGER.warn("Invalid cleanup call. MilestoneId: '{}'", milestoneId);
    }
  }

  private MilestoneCleanerDao fetchCoreDao() {
    if (milestoneCleanerCoreDao == null) {
      milestoneCleanerCoreDao = dbiRegistry.getNucleusDbi().onDemand(MilestoneCleanerDao.class);
    }
    return milestoneCleanerCoreDao;
  }
}
