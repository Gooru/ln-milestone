package org.gooru.milestone.bootstrap.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.gooru.milestone.infra.constants.Constants;
import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.services.QueueRecordProcessingService;
import org.gooru.milestone.infra.services.queueoperators.QueueRecordFetcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */
public class MilestoneProcessingVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MilestoneProcessingVerticle.class);

  private static final String SUCCESS = "SUCCESS";
  private static final String FAIL = "FAIL";

  @Override
  public void start(Future<Void> startFuture) {

    EventBus eb = vertx.eventBus();
    eb.localConsumer(Constants.EventBus.MBEP_MILESTONE_INITIAL_QUEUE_PROCESSOR,
        this::processMessage)
        .completionHandler(result -> {
          if (result.succeeded()) {
            LOGGER.info("Milestone processor processing handler ready to listen");
            startFuture.complete();
          } else {
            LOGGER.error(
                "Error registering the Milestone processing handler. Halting the machinery");
            startFuture.fail(result.cause());
            Runtime.getRuntime().halt(1);
          }
        });
  }

  @Override
  public void stop(Future<Void> stopFuture) {
  }

  private void processMessage(Message<String> message) {
    vertx.executeBlocking(future -> {
      try {

        JsonObject request = new JsonObject(message.body());
        Long id = request.getLong("id");

        MilestoneQueueModel model = QueueRecordFetcherService.build().fetchRecordById(id);
        QueueRecordProcessingService.build().processQueueRecord(model);

        future.complete();
      } catch (Exception e) {
        LOGGER.warn("Not able to do Milestone calculation for the model. '{}'", message.body(), e);
        future.fail(e);
      }
    }, asyncResult -> {
      if (asyncResult.succeeded()) {
        message.reply(SUCCESS);
      } else {
        LOGGER
            .warn("Milestone calculation not done for model: '{}'", message.body(),
                asyncResult.cause());
        message.reply(FAIL);
      }
    });
  }
}
