package org.gooru.milestone.infra.services.queueoperators;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.jdbi.DBICreator;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

public interface ProcessingEligibilityVerifier {

  boolean isEligibleForProcessing(MilestoneQueueModel model);

  static ProcessingEligibilityVerifier build() {
    return new ProcessingEligibilityVerifierImpl(DBICreator.getDbiForDefaultDS(), false);
  }

  static ProcessingEligibilityVerifier build(DBI dbi4core, boolean override) {
    return new ProcessingEligibilityVerifierImpl(dbi4core, override);
  }

}
