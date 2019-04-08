package org.gooru.milestone.infra.services.milestonecleaner;

import java.util.UUID;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * @author ashish.
 */

interface MilestoneCleanerDao {

  @SqlUpdate("delete from milestone_lesson_map where id = :id")
  void cleanupMilestone(@Bind("id") Long id);

  @SqlUpdate("delete from milestone_lesson_map where course_id = :courseId and fw_code = :fwCode")
  void cleanupMilestones(@Bind("courseId") UUID courseId, @Bind("fwCode") String fwCode);


}
