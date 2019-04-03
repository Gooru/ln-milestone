package org.gooru.milestone.infra.services.validators;

import org.gooru.milestone.infra.data.ProcessingContext;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class ContextValidatorImpl implements ContextValidator {

  private final DBI dbi4core;
  private final DBI dbi4ds;
  private static final Logger LOGGER = LoggerFactory.getLogger(ContextValidatorImpl.class);
  private ProcessingContext context;

  ContextValidatorImpl(DBI dbi4core, DBI dbi4ds) {
    this.dbi4core = dbi4core;
    this.dbi4ds = dbi4ds;
  }

  @Override
  public void validate(ProcessingContext context) {
    this.context = context;
    // TODO: Implement this
  }


}
