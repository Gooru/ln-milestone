package org.gooru.milestone.infra.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * @author ashish.
 */

public class MilestoneLessonMapModelMapper implements ResultSetMapper<MilestoneLessonMapModel> {

  private static final String ID = "id";
  private static final String MILESTONE_ID = "milestone_id";
  private static final String COURSE_ID = "course_id";
  private static final String UNIT_ID = "unit_id";
  private static final String LESSON_ID = "lesson_id";
  private static final String GRADE_ID = "grade_id";
  private static final String GRADE_NAME = "grade_name";
  private static final String GRADE_SEQ = "grade_seq";
  private static final String FW_CODE = "fw_code";
  private static final String TX_SUBJECT_CODE = "tx_subject_code";
  private static final String TX_DOMAIN_ID = "tx_domain_id";
  private static final String TX_DOMAIN_SEQ = "tx_domain_seq";
  private static final String TX_DOMAIN_CODE = "tx_domain_code";
  private static final String TX_COMP_CODE = "tx_comp_code";
  private static final String TX_COMP_NAME = "tx_comp_name";
  private static final String TX_COMP_STUDENT_DESC = "tx_comp_student_desc";
  private static final String TX_COMP_SEQ = "tx_comp_seq";


  @Override
  public MilestoneLessonMapModel map(int index, ResultSet r, StatementContext ctx)
      throws SQLException {
    MilestoneLessonMapModel model = new MilestoneLessonMapModel();

    model.setId(r.getLong(ID));
    model.setMilestoneId(r.getString(MILESTONE_ID));
    model.setCourseId(UUID.fromString(r.getString(COURSE_ID)));
    model.setUnitId(UUID.fromString(r.getString(UNIT_ID)));
    model.setLessonId(UUID.fromString(r.getString(LESSON_ID)));
    model.setGradeId(r.getLong(GRADE_ID));
    model.setGradeName(r.getString(GRADE_NAME));
    model.setGradeSeq(r.getInt(GRADE_SEQ));
    model.setFwCode(r.getString(FW_CODE));
    model.setSubjectCode(r.getString(TX_SUBJECT_CODE));
    model.setDomainCode(r.getString(TX_DOMAIN_CODE));
    model.setDomainId(r.getLong(TX_DOMAIN_ID));
    model.setDomainSeq(r.getInt(TX_DOMAIN_SEQ));
    model.setCompCode(r.getString(TX_COMP_CODE));
    model.setCompName(r.getString(TX_COMP_NAME));
    model.setCompSeq(r.getInt(TX_COMP_SEQ));
    model.setCompStudentDesc(r.getString(TX_COMP_STUDENT_DESC));

    return model;
  }
}
