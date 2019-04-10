package org.gooru.milestone.infra.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * @author ashish.
 */

public class GradeCompetencyMapModelMapper implements ResultSetMapper<GradeCompetencyMapModel> {

  private static final String ID = "id";
  private static final String GRADE_ID = "grade_id";
  private static final String TX_SUBJECT_CODE = "tx_subject_code";
  private static final String FW_CODE = "fw_code";
  private static final String TX_DOMAIN_ID = "tx_domain_id";
  private static final String TX_DOMAIN_CODE = "tx_domain_code";
  private static final String TX_DOMAIN_SEQ = "tx_domain_seq";
  private static final String TX_COMP_CODE = "tx_comp_code";
  private static final String TX_COMP_NAME = "tx_comp_name";
  private static final String TX_COMP_STUDENT_DESC = "tx_comp_student_desc";
  private static final String TX_COMP_SEQ = "tx_comp_seq";


  @Override
  public GradeCompetencyMapModel map(int index, ResultSet r, StatementContext ctx)
      throws SQLException {
    GradeCompetencyMapModel model = new GradeCompetencyMapModel();

    model.setId(r.getLong(ID));
    model.setGradeId(r.getLong(GRADE_ID));
    model.setSubjectCode(r.getString(TX_SUBJECT_CODE));
    model.setFwCode(r.getString(FW_CODE));
    model.setDomainId(r.getLong(TX_DOMAIN_ID));
    model.setDomainCode(r.getString(TX_DOMAIN_CODE));
    model.setDomainSequence(r.getInt(TX_DOMAIN_SEQ));
    model.setCompCode(r.getString(TX_COMP_CODE));
    model.setCompName(r.getString(TX_COMP_NAME));
    model.setCompStudentDesc(r.getString(TX_COMP_STUDENT_DESC));
    model.setCompSequence(r.getInt(TX_COMP_SEQ));
    return model;
  }

}
