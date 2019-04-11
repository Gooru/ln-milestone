package org.gooru.milestone.processors.milestonestatuschecker;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.gooru.milestone.infra.data.EventBusMessage;
import org.gooru.milestone.infra.exceptions.ExceptionResolver;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.gooru.milestone.processors.AsyncMessageProcessor;
import org.gooru.milestone.responses.MessageResponse;
import org.gooru.milestone.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

public class MilestoneStatusCheckerProcessor implements AsyncMessageProcessor {

  private final Vertx vertx;
  private final Message<JsonObject> message;
  private final Future<MessageResponse> result;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MilestoneStatusCheckerProcessor.class);
  private EventBusMessage eventBusMessage;
  private final MilestoneStatusCheckerService service = new MilestoneStatusCheckerService(
      DbiRegistry.build());

  public MilestoneStatusCheckerProcessor(Vertx vertx, Message<JsonObject> message) {
    this.vertx = vertx;
    this.message = message;
    this.result = Future.future();
  }

  @Override
  public Future<MessageResponse> process() {
    vertx.<MessageResponse>executeBlocking(future -> {
      try {
        this.eventBusMessage = EventBusMessage.eventBusMessageBuilder(message);
        MilestoneStatusCheckerCommand command = MilestoneStatusCheckerCommand
            .build(eventBusMessage);
        LOGGER.debug("Processing for command: '{}", command.toString());
        boolean milestoneDone = service.checkStatusForMilestoneDone(command);
        future.complete(MessageResponseFactory
            .createOkayResponse(new JsonObject().put("milestoneDone", milestoneDone)));
      } catch (Throwable throwable) {
        LOGGER.warn("Encountered exception", throwable);
        future.fail(ExceptionResolver.resolveException(throwable));
      }
    }, asyncResult -> {
      if (asyncResult.succeeded()) {
        result.complete(asyncResult.result());
      } else {
        result.fail(asyncResult.cause());
      }
    });
    return result;
  }
}
