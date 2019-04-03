package org.gooru.milestone.infra.services.queueoperators;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.jdbi.DBICreator;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

public interface QueueRecordFetcherService {

  MilestoneQueueModel fetchRecordById(Long id);

  static QueueRecordFetcherService build() {
    return new QueueRecordFetcherServiceImpl(DBICreator.getDbiForDefaultDS());
  }

  static QueueRecordFetcherService build(DBI dbi) {
    return new QueueRecordFetcherServiceImpl(dbi);
  }


}
