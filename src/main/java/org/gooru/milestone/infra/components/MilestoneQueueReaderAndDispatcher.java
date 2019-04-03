package org.gooru.milestone.infra.components;


import static org.gooru.milestone.infra.constants.Constants.EventBus.MBEP_MILESTONE_INITIAL_QUEUE_PROCESSOR;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.services.queueoperators.QueueInitializerService;
import org.gooru.milestone.infra.services.queueoperators.QueueRecordDispatcherService;
import org.gooru.milestone.routes.utils.DeliveryOptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the timer base runner class which is responsible to read the Persisted queued requests
 * and send them to Event bus so that they can be processed by listeners. It does wait for reply, so
 * that we do increase the backpressure on TCP bus too much, however what is replied is does not
 * matter as we do schedule another one shot timer to do the similar stuff. For the first run, it
 * re-initializes the status in the DB so that any tasks that were under processing when the
 * application shut down happened would be picked up again.
 *
 * @author ashish.
 */
public final class MilestoneQueueReaderAndDispatcher implements Initializer, Finalizer {

  private static final MilestoneQueueReaderAndDispatcher ourInstance = new MilestoneQueueReaderAndDispatcher();
  private static final int delay = 1_000;
  private static long timerId;
  private static boolean firstTrigger = true;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(MilestoneQueueReaderAndDispatcher.class);
  private static final int PROCESS_TIMEOUT = 300;

  public static MilestoneQueueReaderAndDispatcher getInstance() {
    return ourInstance;
  }

  private Vertx vertx;

  private MilestoneQueueReaderAndDispatcher() {
  }

  @Override
  public void finalizeComponent() {
    vertx.cancelTimer(timerId);
  }

  @Override
  public void initializeComponent(Vertx vertx, JsonObject config) {
    this.vertx = vertx;

    timerId = vertx.setTimer(delay, new TimerHandler(vertx));
  }

  static final class TimerHandler implements Handler<Long> {

    private final Vertx vertx;

    TimerHandler(Vertx vertx) {
      this.vertx = vertx;
    }

    @Override
    public void handle(Long event) {
      vertx.<MilestoneQueueModel>executeBlocking(future -> {
        if (firstTrigger) {
          LOGGER.debug("Timer handling for first trigger");
          QueueInitializerService.build().initializeQueue();
          firstTrigger = false;
        }
        MilestoneQueueModel model = QueueRecordDispatcherService.build()
            .getNextRecordToDispatch();
        future.complete(model);
      }, asyncResult -> {
        if (asyncResult.succeeded()) {
          if (asyncResult.result().isModelPersisted()) {
            vertx.eventBus()
                .send(MBEP_MILESTONE_INITIAL_QUEUE_PROCESSOR, asyncResult.result().toSummaryJson(),
                    DeliveryOptionsBuilder.buildWithoutApiVersion(PROCESS_TIMEOUT),
                    eventBusResponse -> {
                      timerId = vertx.setTimer(delay, new TimerHandler(vertx));
                    });
          } else {
            timerId = vertx.setTimer(delay, new TimerHandler(vertx));
          }
        } else {
          LOGGER.warn("Processing of record from queue failed. ", asyncResult.cause());
          timerId = vertx.setTimer(delay, new TimerHandler(vertx));
        }
      });

    }
  }

}
