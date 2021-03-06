package org.gooru.milestone.responses;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.gooru.milestone.infra.constants.Constants;

/**
 * @author ashish.
 */
public final class ResponseUtil {

  public static void processSuccess(Message<JsonObject> message, JsonObject jsonResult) {
    final DeliveryOptions deliveryOptions = new DeliveryOptions()
        .addHeader(Constants.Message.MSG_OP_STATUS,
            Constants.Message.MSG_OP_STATUS_SUCCESS);
    message.reply(jsonResult, deliveryOptions);

  }

  public static void processFailure(Message<JsonObject> message) {
    message.reply(new JsonObject(),
        new DeliveryOptions()
            .addHeader(Constants.Message.MSG_OP_STATUS, Constants.Message.MSG_OP_STATUS_FAIL));
  }

  private ResponseUtil() {
    throw new AssertionError();
  }

}
