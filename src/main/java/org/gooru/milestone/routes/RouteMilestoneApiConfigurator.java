package org.gooru.milestone.routes;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.gooru.milestone.infra.constants.Constants;
import org.gooru.milestone.infra.constants.Constants.Message;
import org.gooru.milestone.routes.utils.DeliveryOptionsBuilder;
import org.gooru.milestone.routes.utils.RouteRequestUtility;
import org.gooru.milestone.routes.utils.RouteResponseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish
 */
public class RouteMilestoneApiConfigurator implements RouteConfigurator {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(RouteMilestoneApiConfigurator.class);
  private EventBus eb = null;
  private long mbusTimeout;

  @Override
  public void configureRoutes(Vertx vertx, Router router, JsonObject config) {
    eb = vertx.eventBus();
    mbusTimeout = config.getLong(Constants.EventBus.MBUS_TIMEOUT, 30L) * 1_000;
    router.post(Constants.Route.API_MILESTONE_QUEUE).handler(this::doMilestone);
    router.get(Constants.Route.API_MILESTONE_STATUS_CHECK).handler(this::checkMilestoneStatus);
  }

  private void checkMilestoneStatus(RoutingContext routingContext) {
    DeliveryOptions options = DeliveryOptionsBuilder
        .buildWithoutApiVersion(routingContext, mbusTimeout,
            Message.MSG_OP_MILESTONE_STATUS);
    eb.<JsonObject>send(Constants.EventBus.MBEP_API_DISPATCHER,
        RouteRequestUtility.getBodyForMessage(routingContext),
        options, reply -> RouteResponseUtility.responseHandler(routingContext, reply, LOGGER));
  }

  private void doMilestone(RoutingContext routingContext) {
    DeliveryOptions options = DeliveryOptionsBuilder
        .buildWithoutApiVersion(routingContext, mbusTimeout, Message.MSG_OP_MILESTONE_QUEUE);
    eb.<JsonObject>send(Constants.EventBus.MBEP_API_DISPATCHER,
        RouteRequestUtility.getBodyForMessage(routingContext),
        options, reply -> RouteResponseUtility.responseHandler(routingContext, reply, LOGGER));
  }
}
