package org.gooru.milestone.processors.milestonequeuer;

import java.util.UUID;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

/**
 * @author ashish.
 */

interface MilestoneQueueDao {

  @SqlQuery("select exists (select 1 from course where id = :courseId and is_deleted = false and version = 'premium')")
  boolean validateCourseAndItsPremiumness(@Bind("courseId") UUID courseId);

  @SqlQuery("select exists (select 1 from fw_master where fw_code = :fwCode and is_gut_mapped = true)")
  boolean validateFWAndGutMapping(@Bind("fwCode") String fwCode);
}
