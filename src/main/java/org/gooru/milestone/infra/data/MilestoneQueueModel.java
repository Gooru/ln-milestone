package org.gooru.milestone.infra.data;

import io.vertx.core.json.JsonObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.gooru.milestone.infra.utils.UuidUtils;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * @author ashish
 */
public class MilestoneQueueModel {

  public static final int RQ_STATUS_QUEUED = 0;
  public static final int RQ_STATUS_DISPATCHED = 1;
  public static final int RQ_STATUS_INPROCESS = 2;

  private Long id;
  private UUID courseId;
  private String fwCode;
  private Boolean override;
  private int status;

  public static MilestoneQueueModel createNonPersistedEmptyModel() {
    return new MilestoneQueueModel();
  }

  public String toSummaryJson() {
    return new JsonObject().put("id", id).toString();
  }

  public String toJson() {
    return new JsonObject().put("id", id)
        .put("courseId", UuidUtils.uuidToString(courseId))
        .put("fwCode", fwCode)
        .put("status", status).toString();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getCourseId() {
    return courseId;
  }

  public void setCourseId(UUID courseId) {
    this.courseId = courseId;
  }

  public String getFwCode() {
    return fwCode;
  }

  public void setFwCode(String fwCode) {
    this.fwCode = fwCode;
  }

  public Boolean getOverride() {
    return override;
  }

  public void setOverride(Boolean override) {
    this.override = override;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public boolean isModelPersisted() {
    return id != null;
  }

  public static class MilestoneQueueModelMapper implements
      ResultSetMapper<MilestoneQueueModel> {

    @Override
    public MilestoneQueueModel map(final int index, final ResultSet resultSet,
        final StatementContext statementContext) throws SQLException {
      MilestoneQueueModel model = new MilestoneQueueModel();
      model.setId(resultSet.getLong("id"));
      model.setStatus(resultSet.getInt("status"));
      model.setCourseId(UuidUtils.convertStringToUuid(resultSet.getString("course_id")));
      model.setFwCode(resultSet.getString("fw_code"));
      model.setOverride(resultSet.getBoolean("override"));
      return model;
    }

  }

}
