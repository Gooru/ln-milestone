package org.gooru.milestone.responses.auth;

import io.vertx.core.json.JsonObject;

/**
 * @author ashish.
 */
public interface AuthSessionResponseHolder extends AuthResponseHolder {

  JsonObject getSession();
}
