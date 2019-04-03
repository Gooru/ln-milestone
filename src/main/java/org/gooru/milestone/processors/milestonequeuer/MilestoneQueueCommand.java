package org.gooru.milestone.processors.milestonequeuer;

import io.vertx.core.json.JsonArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.gooru.milestone.infra.data.EventBusMessage;
import org.gooru.milestone.infra.utils.UuidUtils;

/**
 * @author ashish.
 */

class MilestoneQueueCommand {

  private final UUID classId;
  private final List<String> usersList;

  public UUID getTeacherId() {
    return teacherId;
  }

  private final UUID teacherId;

  private MilestoneQueueCommand(UUID classId, List<String> usersList,
      UUID teacherId) {
    this.classId = classId;
    this.usersList = Collections.unmodifiableList(usersList);
    this.teacherId = teacherId;
  }

  public UUID getClassId() {
    return classId;
  }

  List<String> getUsersList() {
    return usersList;
  }

  static MilestoneQueueCommand build(EventBusMessage eventBusMessage) {
    JsonArray users = eventBusMessage.getRequestBody().getJsonArray(RequestAttributes.USERS);
    if (users == null || users.isEmpty()) {
      throw new IllegalArgumentException("Invalid users list");
    }
    List<String> usersList = validateForBeingUuidAndFetchUsers(users);
    String classIdString = eventBusMessage.getRequestBody().getString(RequestAttributes.CLASS_ID);
    if (!UuidUtils.validateUuid(classIdString)) {
      throw new IllegalArgumentException("Invalid class id");
    }
    if (!UuidUtils.validateUuid(Objects.toString(eventBusMessage.getUserId()))) {
      throw new IllegalArgumentException("Invalid teacher id");
    }
    return new MilestoneQueueCommand(UUID.fromString(classIdString),
        usersList, eventBusMessage.getUserId());
  }

  private static List<String> validateForBeingUuidAndFetchUsers(JsonArray users) {
    int size = users.size();
    List<String> usersList = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      String user = users.getString(i);
      if (!UuidUtils.validateUuid(user)) {
        throw new IllegalArgumentException("Invalid format for one of the users");
      }
      usersList.add(user);
    }
    return usersList;
  }

  @Override
  public String toString() {
    return "MilestoneQueueCommand{" +
        "classId=" + classId + ", usersList=" + usersList + ", teacherId=" + teacherId + '}';
  }

  private static class RequestAttributes {

    private static final String USERS = "users";
    private static final String CLASS_ID = "classId";


    private RequestAttributes() {
      throw new AssertionError();
    }
  }

}
