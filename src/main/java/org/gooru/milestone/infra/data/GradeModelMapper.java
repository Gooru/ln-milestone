package org.gooru.milestone.infra.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * @author ashish.
 */

public class GradeModelMapper implements ResultSetMapper<GradeModel> {

  private static final String ID = "id";
  private static final String GRADE = "grade";
  private static final String TX_SUBJECT_CODE = "tx_subject_code";
  private static final String GRADE_SEQ = "grade_seq";
  private static final String FW_CODE = "fw_code";


  @Override
  public GradeModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    GradeModel model = new GradeModel();

    model.setId(r.getLong(ID));
    model.setGradeName(r.getString(GRADE));
    model.setSubjectCode(r.getString(TX_SUBJECT_CODE));
    model.setGradeSequence(r.getInt(GRADE_SEQ));
    model.setFwCode(r.getString(FW_CODE));

    return model;
  }

}
