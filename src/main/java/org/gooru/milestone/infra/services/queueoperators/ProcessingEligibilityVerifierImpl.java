package org.gooru.milestone.infra.services.queueoperators;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class ProcessingEligibilityVerifierImpl implements
    ProcessingEligibilityVerifier {

  private final DBI dbi4core;
  private final boolean override;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ProcessingEligibilityVerifierImpl.class);
  private MilestoneQueueModel model;
  private ProcessingEligibilityVerifierDao dao4core;

  ProcessingEligibilityVerifierImpl(DBI dbi4core, boolean override) {
    this.dbi4core = dbi4core;
    this.override = override;
  }

  @Override
  public boolean isEligibleForProcessing(MilestoneQueueModel model) {
    this.model = model;
    if (!recordIsStillInDispatchedState()) {
      LOGGER.debug("Record is not found to be in dispatched state, may be processed already.");
      return false;
    }
    if (!override && wasMilestoneCreationAlreadyDone()) {
      LOGGER.debug("ILP was already done");
      return false;
    }

    return true;
  }


  private boolean wasMilestoneCreationAlreadyDone() {
    return fetchCoreDao().wasMilestoneCreationAlreadyDone(model);
  }

  private boolean recordIsStillInDispatchedState() {
    return fetchCoreDao().isQueuedRecordStillDispatched(model.getId());
  }

  private ProcessingEligibilityVerifierDao fetchCoreDao() {
    if (dao4core == null) {
      dao4core = dbi4core.onDemand(ProcessingEligibilityVerifierDao.class);
    }
    return dao4core;
  }

}
