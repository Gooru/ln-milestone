package org.gooru.milestone.processors;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.gooru.milestone.processors.milestonequeuer.MilestoneQueueProcessor;
import org.gooru.milestone.responses.MessageResponse;

/**
 * @author ashish
 */
public final class ProcessorBuilder {

  public static AsyncMessageProcessor buildPlaceHolderExceptionProcessor(Vertx vertx,
      Message<JsonObject> message) {
    return () -> {
      Future<MessageResponse> future = Future.future();
      future.fail(new IllegalStateException("Illegal State for processing command"));
      return future;
    };
  }

  public static AsyncMessageProcessor buildMilestoneQueueProcessor(
      Vertx vertx, Message<JsonObject> message) {
    return new MilestoneQueueProcessor(vertx, message);
  }

  public static AsyncMessageProcessor buildMilestoneStatusCheckProcessor(
      Vertx vertx, Message<JsonObject> message) {
    // TODO: Implement this
    return null;
  }

}
