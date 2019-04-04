package org.gooru.milestone.infra.services.validators;

import org.gooru.milestone.infra.data.ProcessingContext;
import org.gooru.milestone.infra.jdbi.DBICreator;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.skife.jdbi.v2.DBI;

/**
 * The validator to validate the context provided. Ideally following things may need validation:
 * <ol>
 *   <li> Course is not deleted and premium </li>
 *   <li> Framework exists </li>
 *   <li> Valid grades are present for subject and FW combination </li>
 *   <li> Valid competency grade map is present for subject and FW combination </li>
 * </ol>
 *
 * However,
 * <ul>
 *   <li>First validation is already done upstream. But we do it here as well.</li>
 *   <li>Doing fourth validation on DS db will be as good as implicit validation for #2 and #3</li>
 * </ul>
 * Hence, default implementation short circuits #2 and #3 operation. If the requirement is
 * to do all validations, another implementation needs to be provided.
 *
 * @author ashish.
 */

public interface ContextValidator {

  void validate(ProcessingContext context);

  static ContextValidator build() {
    return new ContextValidatorImpl(DbiRegistry.build());
  }

  static ContextValidator build(DbiRegistry dbiRegistry) {
    return new ContextValidatorImpl(dbiRegistry);
  }
}
