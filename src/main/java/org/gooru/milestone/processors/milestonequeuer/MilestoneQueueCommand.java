package org.gooru.milestone.processors.milestonequeuer;

import io.vertx.core.json.JsonObject;
import java.util.UUID;
import org.gooru.milestone.infra.data.EventBusMessage;
import org.gooru.milestone.infra.utils.UuidUtils;

/**
 * @author ashish.
 */

class MilestoneQueueCommand {

  private final UUID courseId;
  private final String fwCode;
  private final boolean override;

  private MilestoneQueueCommand(UUID courseId, String fwCode, boolean override) {
    this.courseId = courseId;
    this.fwCode = fwCode;
    this.override = override;
  }

  UUID getCourseId() {
    return courseId;
  }

  String getFwCode() {
    return fwCode;
  }

  boolean isOverride() {
    return override;
  }

  static MilestoneQueueCommand build(EventBusMessage eventBusMessage) {
    JsonObject request = eventBusMessage.getRequestBody();
    String fwCodeInRequest = request.getString(MilestoneQueueCommand.RequestAttributes.FW_CODE);
    if (fwCodeInRequest == null || fwCodeInRequest.isEmpty()) {
      throw new IllegalArgumentException("Invalid FW code");
    }

    String courseIdString = request.getString(MilestoneQueueCommand.RequestAttributes.COURSE_ID);
    if (!UuidUtils.validateUuid(courseIdString)) {
      throw new IllegalArgumentException("Invalid course id");
    }

    if (!request.containsKey(RequestAttributes.OVERRIDE)) {
      throw new IllegalArgumentException("Invalid override flag");
    }

    boolean override = request.getBoolean(RequestAttributes.OVERRIDE);

    return new MilestoneQueueCommand(UUID.fromString(courseIdString), fwCodeInRequest, override);
  }


  @Override
  public String toString() {
    return "MilestoneQueueCommand{" +
        "courseId=" + courseId +
        ", fwCode='" + fwCode + '\'' +
        ", override=" + override +
        '}';
  }

  private static class RequestAttributes {

    private static final String OVERRIDE = "override";
    private static final String FW_CODE = "fwCode";
    private static final String COURSE_ID = "courseId";


    private RequestAttributes() {
      throw new AssertionError();
    }
  }

}
