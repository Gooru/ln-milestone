package org.gooru.milestone.infra.constants;

import java.util.UUID;

/**
 * @author ashish.
 */
public final class Constants {

  private Constants() {
    throw new AssertionError();
  }

  public static final class EventBus {

    public static final String MBEP_API_DISPATCHER = "org.gooru.milestone.eventbus.api.dispatcher";
    public static final String MBEP_AUTH = "org.gooru.milestone.eventbus.auth";
    public static final String MBEP_MILESTONE_INITIAL_QUEUE_PROCESSOR = "org.gooru.milestone.eventbus.queueprocessor";
    public static final String MBUS_TIMEOUT = "event.bus.send.timeout.seconds";

    private EventBus() {
      throw new AssertionError();
    }
  }

  public static final class Message {

    public static final String MSG_OP = "mb.operation";
    public static final String MSG_OP_STATUS = "mb.op.status";
    public static final String MSG_OP_STATUS_SUCCESS = "mb.op.status.success";
    public static final String MSG_OP_STATUS_FAIL = "mb.op.status.fail";
    public static final String MSG_USER_ANONYMOUS = "anonymous";
    public static final String MSG_USER_ID = "user_id";
    public static final String MSG_HTTP_STATUS = "http.status";
    public static final String MSG_HTTP_BODY = "http.body";
    public static final String MSG_HTTP_HEADERS = "http.headers";

    public static final String MSG_KEY_SESSION = "session";
    public static final String MSG_SESSION_TOKEN = "session.token";
    public static final String MSG_OP_AUTH = "auth";
    public static final String MSG_OP_MILESTONE_QUEUE = "op.milestone.queue";
    public static final String MSG_OP_MILESTONE_STATUS = "op.milestone.status";
    public static final String MSG_MESSAGE = "message";
    public static final String ACCESS_TOKEN_VALIDITY = "access_token_validity";

    private Message() {
      throw new AssertionError();
    }
  }

  public static final class Route {

    public static final String API_INTERNAL_BANNER = "/api/internal/banner";
    public static final String API_INTERNAL_METRICS = "/api/internal/metrics";
    public static final String API_AUTH_ROUTE = "/api/milestone/*";
    private static final String API_BASE_ROUTE = "/api/milestone/:version/";
    public static final String API_MILESTONE_QUEUE =
        API_BASE_ROUTE + "queue";
    public static final String API_MILESTONE_STATUS_CHECK =
        API_BASE_ROUTE + "status";

    private Route() {
      throw new AssertionError();
    }
  }

  public static final class Misc {

    public static final String COMPETENCY_PLACEHOLDER = new UUID(0, 0).toString();
    public static final String USER_PLACEHOLDER = new UUID(0, 0).toString();

    private Misc() {
      throw new AssertionError();
    }
  }

}
