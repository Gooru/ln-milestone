package org.gooru.milestone.infra.services.queueoperators;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish
 */
class QueueRecordDispatcherServiceImpl implements
    QueueRecordDispatcherService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(QueueRecordDispatcherService.class);
  private final DBI dbi;

  QueueRecordDispatcherServiceImpl(DBI dbi) {
    this.dbi = dbi;
  }

  @Override
  public MilestoneQueueModel getNextRecordToDispatch() {
    QueueOperatorDao dao = dbi.onDemand(QueueOperatorDao.class);
    MilestoneQueueModel model = dao.getNextDispatchableModel();
    if (model == null) {
      model = MilestoneQueueModel.createNonPersistedEmptyModel();
    } else {
      dao.setQueuedRecordStatusAsDispatched(model.getId());
    }
    return model;
  }
}
