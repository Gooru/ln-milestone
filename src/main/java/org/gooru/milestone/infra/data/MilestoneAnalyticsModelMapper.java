package org.gooru.milestone.infra.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * @author ashish.
 */

public class MilestoneAnalyticsModelMapper implements ResultSetMapper<MilestoneAnalyticsModel> {

  private static final String ID = "id";
  private static final String MILESTONE_ID = "milestone_id";
  private static final String COURSE_ID = "course_id";
  private static final String UNIT_ID = "unit_id";
  private static final String LESSON_ID = "lesson_id";
  private static final String FW_CODE = "fw_code";


  @Override
  public MilestoneAnalyticsModel map(int index, ResultSet r, StatementContext ctx)
      throws SQLException {

    MilestoneAnalyticsModel model = new MilestoneAnalyticsModel();

    model.setId(r.getLong(ID));
    model.setCourseId(UUID.fromString(r.getString(COURSE_ID)));
    model.setUnitId(UUID.fromString(r.getString(UNIT_ID)));
    model.setLessonId(UUID.fromString(r.getString(LESSON_ID)));
    model.setMilestoneId(r.getString(MILESTONE_ID));
    model.setFwCode(r.getString(FW_CODE));

    return model;
  }
}
