package org.gooru.milestone.infra.services;


import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.jdbi.DBICreator;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish
 */

public interface QueueRecordProcessingService {

  void processQueueRecord(MilestoneQueueModel model);

  static QueueRecordProcessingService build() {
    return new QueueRecordProcessingServiceImpl(DBICreator.getDbiForDefaultDS(),
        DBICreator.getDbiForDsdbDS());
  }

  static QueueRecordProcessingService build(DBI dbi4core, DBI dbi4ds) {
    return new QueueRecordProcessingServiceImpl(dbi4core, dbi4ds);
  }

}
