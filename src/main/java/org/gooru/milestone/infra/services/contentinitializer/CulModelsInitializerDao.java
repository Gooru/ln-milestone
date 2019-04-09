package org.gooru.milestone.infra.services.contentinitializer;

import java.util.List;
import java.util.UUID;
import org.gooru.milestone.infra.data.CulModel;
import org.gooru.milestone.infra.data.CulModelMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * @author ashish.
 */

interface CulModelsInitializerDao {

  @Mapper(CulModelMapper.class)
  @SqlQuery(
      "select l.course_id, u.unit_id, u.sequence_id unit_sequence, l.lesson_id, l.sequence_id lesson_sequence, "
          + " l.gut_codes from lesson l inner join unit u on l.unit_id = u.unit_id and l.course_id = u.course_id "
          + " where l.course_id = :courseId and l.is_deleted = false order by u.sequence_id, l.sequence_id")
  List<CulModel> fetchCulModelsForCourse(@Bind("courseId") UUID courseId);

}
