package org.gooru.milestone.infra.data;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * @author ashish.
 */

public class CulModelMapper implements ResultSetMapper<CulModel> {

  private static final String COURSE_ID = "course_id";
  private static final String UNIT_ID = "unit_id";
  private static final String UNIT_SEQUENCE = "unit_sequence";
  private static final String LESSON_ID = "lesson_id";
  private static final String LESSON_SEQUENCE = "lesson_sequence";
  private static final String GUT_CODES = "gut_codes";


  @Override
  public CulModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    CulModel model = new CulModel();

    model.setCourseId(UUID.fromString(r.getString(COURSE_ID)));
    model.setUnitId(UUID.fromString(r.getString(UNIT_ID)));
    model.setUnitSequence(r.getInt(UNIT_SEQUENCE));
    model.setLessonId(UUID.fromString(r.getString(LESSON_ID)));
    model.setLessonSequence(r.getInt(LESSON_SEQUENCE));
    model.setGutCodes(fetchGutCodesArray(r));
    return model;
  }

  private List<String> fetchGutCodesArray(ResultSet r) throws SQLException {
    Array array = r.getArray(GUT_CODES);
    if (array != null) {
      String[] gutCodesArray = (String[]) array.getArray();
      if (gutCodesArray != null && gutCodesArray.length > 0) {
        return Arrays.asList(gutCodesArray);
      }
    }
    return Collections.emptyList();
  }
}
