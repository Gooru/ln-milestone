package org.gooru.milestone.infra.services.queuerequest;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish
 */
class RequestQueueServiceImpl implements RequestQueueService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestQueueService.class);
  private final DbiRegistry dbiRegistry;
  private RequestQueueDao queueDao;
  private MilestoneQueueModel model;

  RequestQueueServiceImpl(DbiRegistry dbiRegistry) {
    this.dbiRegistry = dbiRegistry;
  }

  @Override
  public void enqueue(MilestoneQueueModel model) {
    this.model = model;
    queueDao = dbiRegistry.getNucleusDbi().onDemand(RequestQueueDao.class);
    doQueueing();
  }

  private void doQueueing() {
    queueDao.queueRequest(model);
  }

}
