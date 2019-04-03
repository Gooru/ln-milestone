package org.gooru.milestone.responses.auth;

public interface AuthResponseHolder {

  boolean isAuthorized();

  boolean isAnonymous();

  String getUser();
}
