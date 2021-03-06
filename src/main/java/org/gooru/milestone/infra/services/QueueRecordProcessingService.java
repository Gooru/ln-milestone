package org.gooru.milestone.infra.services;


import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;

/**
 * @author ashish
 */

public interface QueueRecordProcessingService {

  void processQueueRecord(MilestoneQueueModel model);

  static QueueRecordProcessingService build(DbiRegistry dbiRegistry) {
    return new QueueRecordProcessingServiceImpl(dbiRegistry);
  }

  static QueueRecordProcessingService build() {
    return new QueueRecordProcessingServiceImpl(DbiRegistry.build());
  }

}
