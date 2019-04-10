package org.gooru.milestone.infra.services.milestonepersister;

import java.util.List;
import java.util.UUID;
import org.gooru.milestone.infra.data.MilestoneAnalyticsModel;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * @author ashish.
 */

interface MilestonePersisterAnalyticsDao {

  @SqlBatch(
      "insert into milestone (id, milestone_id, course_id, unit_id, lesson_id, fw_code) values "
          + " (:id, :milestoneId, :courseId, :unitId, :lessonId, :fwCode)")
  void persistMilestonesInAnalytics(@BindBean List<MilestoneAnalyticsModel> models);

  @SqlUpdate("delete from milestone where course_id = :courseId and fw_code = :fwCode")
  void cleanupExistingMilestoneForCourseAndFw(@Bind("courseId") UUID courseId,
      @Bind("fwCode") String fwCode);

}
