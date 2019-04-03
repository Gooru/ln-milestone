package org.gooru.milestone.processors;

import io.vertx.core.Future;
import org.gooru.milestone.responses.MessageResponse;

/**
 * @author ashish.
 */
public interface AsyncMessageProcessor {

  Future<MessageResponse> process();

}
