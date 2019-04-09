package org.gooru.milestone.infra.services.milestonepersister;

import java.util.List;
import java.util.UUID;
import org.gooru.milestone.infra.data.MilestoneAnalyticsModel;
import org.gooru.milestone.infra.data.MilestoneAnalyticsModelMapper;
import org.gooru.milestone.infra.data.MilestoneLessonMapModel;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * @author ashish.
 */

interface MilestonePersisterCoreDao {

  @SqlBatch("insert into milestone_lesson_map "
      + " (milestone_id, course_id, unit_id, lesson_id, grade_id, grade_name, grade_seq, fw_code, "
      + " tx_subject_code, tx_domain_id, tx_domain_seq, tx_domain_code, tx_comp_code, tx_comp_name, "
      + " tx_comp_student_desc, tx_comp_seq) values (:milestoneId, :courseId, :unitId, :lessonId, "
      + " :gradeId, :gradeName, :gradeSeq, :fwCode, :subjectCode, :domainId, :domainSeq, "
      + " :domainCode, :compCode, :compName, :compStudentDesc, :compSeq)")
  void persistMilestones(@BindBean List<MilestoneLessonMapModel> models);

  @Mapper(MilestoneAnalyticsModelMapper.class)
  @SqlQuery("select id, milestone_id, course_id, unit_id, lesson_id, fw_code from milestone "
      + " where course_id = :courseId and fw_code = :fwCode")
  List<MilestoneAnalyticsModel> fetchAnalyticsModelsFromCore(@Bind("courseId") UUID courseId,
      @Bind("fwCode") String fwCode);
}
