package org.gooru.milestone.processors.milestonestatuschecker;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.UUID;
import org.gooru.milestone.infra.data.EventBusMessage;
import org.gooru.milestone.infra.utils.UuidUtils;

/**
 * @author ashish.
 */

class MilestoneStatusCheckerCommand {

  private final UUID courseId;
  private final String fwCode;

  private MilestoneStatusCheckerCommand(UUID courseId, String fwCode) {
    this.courseId = courseId;
    this.fwCode = fwCode;
  }

  public UUID getCourseId() {
    return courseId;
  }

  public String getFwCode() {
    return fwCode;
  }

  static MilestoneStatusCheckerCommand build(EventBusMessage eventBusMessage) {
    JsonObject request = eventBusMessage.getRequestBody();
    String fwCodeInRequest = getFirstMemberOfStringArrayWithMandatoryValidationFromJson(
        request, RequestAttributes.FW_CODE);

    String courseIdString = getFirstMemberOfStringArrayWithMandatoryValidationFromJson(request,
        RequestAttributes.COURSE_ID);

    if (!UuidUtils.validateUuid(courseIdString)) {
      throw new IllegalArgumentException("Invalid course id");
    }

    return new MilestoneStatusCheckerCommand(UUID.fromString(courseIdString), fwCodeInRequest);
  }

  private static String getFirstMemberOfStringArrayWithMandatoryValidationFromJson(JsonObject json,
      String arrayKey) {
    JsonArray valuesArray = json.getJsonArray(arrayKey);
    if (valuesArray == null || valuesArray.isEmpty()) {
      throw new IllegalArgumentException("Invalid " + arrayKey);
    }
    String stringValue = valuesArray.getString(0);
    if (stringValue == null || stringValue.isEmpty()) {
      throw new IllegalArgumentException("Null/Empty " + arrayKey);
    }
    return stringValue;
  }

  @Override
  public String toString() {
    return "MilestoneStatusCheckerCommand{" +
        "courseId=" + courseId +
        ", fwCode='" + fwCode + '\'' +
        '}';
  }

  private static class RequestAttributes {

    private static final String FW_CODE = "fwCode";
    private static final String COURSE_ID = "courseId";


    private RequestAttributes() {
      throw new AssertionError();
    }
  }

}
