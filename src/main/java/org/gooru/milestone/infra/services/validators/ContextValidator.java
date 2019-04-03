package org.gooru.milestone.infra.services.validators;

import org.gooru.milestone.infra.data.ProcessingContext;
import org.gooru.milestone.infra.jdbi.DBICreator;
import org.skife.jdbi.v2.DBI;

/**
 * The validator to validate the context provided before rescope is calculated.
 *
 * If the validation fails, idea is to abort the processing of the rescope and halt the machinery
 * for this request.
 *
 * @author ashish.
 */

public interface ContextValidator {

  void validate(ProcessingContext context);

  static ContextValidator build() {
    return new ContextValidatorImpl(DBICreator.getDbiForDefaultDS(),
        DBICreator.getDbiForDsdbDS());
  }

  static ContextValidator build(DBI dbi4core, DBI dbi4ds) {
    return new ContextValidatorImpl(dbi4core, dbi4ds);
  }
}
