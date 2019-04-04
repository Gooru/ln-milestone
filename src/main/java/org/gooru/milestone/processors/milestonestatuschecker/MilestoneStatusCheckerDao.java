package org.gooru.milestone.processors.milestonestatuschecker;

import java.util.UUID;
import org.gooru.milestone.infra.jdbi.PGArray;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

/**
 * @author ashish.
 */

interface MilestoneStatusCheckerDao {

  @SqlQuery("select exists (select 1 from course where id =  :courseId and is_deleted = false and version = 'premium')")
  boolean validateCourseAndItsPremiumness(@Bind("courseId") UUID courseId);

  @SqlQuery("select exists (select 1 from milestone_lesson_map where course_id = :courseId and fw_code = :fwCode limit 1)")
  boolean checkMilestoneDone(@Bind("courseId") UUID courseId, @Bind("fwCode") String fwCode);
}
