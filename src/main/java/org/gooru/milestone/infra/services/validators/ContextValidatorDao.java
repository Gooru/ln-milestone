package org.gooru.milestone.infra.services.validators;

import java.util.UUID;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

/**
 * @author ashish.
 */

interface ContextValidatorDao {

  @SqlQuery("select exists (select 1 from grade_competency_map where tx_subject_code = :subjectCode and fw_code = :fwCode limit 1)")
  boolean checkGradeExistsForSubjectFW(@Bind("subjectCode") String subjectCode,
      @Bind("fwCode") String fwCode);

  @SqlQuery("select exists (select 1 from course where id = :courseId and is_deleted = false and version = 'premium')")
  boolean validateCourseAndItsPremiumness(@Bind("courseId") UUID courseId);


}
