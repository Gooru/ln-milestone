package org.gooru.milestone.infra.services.gradeinitializer;

import java.util.List;
import org.gooru.milestone.infra.data.GradeModel;
import org.gooru.milestone.infra.data.GradeModelMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * @author ashish.
 */

public interface GradeInitializerDao {


  @Mapper(GradeModelMapper.class)
  @SqlQuery("select id, grade, tx_subject_code, fw_code, grade_seq from grade_master where "
      + " tx_subject_code = :subjectCode and fw_code = :fwCode")
  List<GradeModel> fetchGradeModelsForSubjectAndFw(@Bind("subjectCode") String subjectCode,
      @Bind("fwCode") String fwCode);

}
