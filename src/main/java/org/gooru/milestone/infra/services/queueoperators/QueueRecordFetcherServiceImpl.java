package org.gooru.milestone.infra.services.queueoperators;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

class QueueRecordFetcherServiceImpl implements QueueRecordFetcherService {

  private final DBI dbi;

  QueueRecordFetcherServiceImpl(DBI dbi) {
    this.dbi = dbi;
  }

  @Override
  public MilestoneQueueModel fetchRecordById(Long id) {
    QueueOperatorDao dao = dbi.onDemand(QueueOperatorDao.class);
    return dao.fetchSpecifiedRecord(id);
  }
}
