package org.gooru.milestone.infra.services.gcminitializer;

import java.util.List;
import org.gooru.milestone.infra.data.GradeCompetencyMapModel;
import org.gooru.milestone.infra.data.GradeCompetencyMapModelMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * @author ashish.
 */

public interface GcmInitializerDao {

  @Mapper(GradeCompetencyMapModelMapper.class)
  @SqlQuery(
      "select id, grade_id, tx_subject_code, fw_code, tx_domain_id, tx_domain_code, tx_domain_seq, "
          + " tx_comp_code, tx_comp_name, tx_comp_student_desc, tx_comp_seq from grade_competency_map "
          + " where tx_subject_code = :subjectCode and fw_code = :fwCode")
  List<GradeCompetencyMapModel> initializeGcmForSubjectAndFw(
      @Bind("subjectCode") String subjectCode, @Bind("fwCode") String fwCode);
}
