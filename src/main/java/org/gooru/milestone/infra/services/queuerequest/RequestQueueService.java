package org.gooru.milestone.infra.services.queuerequest;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.jdbi.DbiRegistry;

/**
 * Module to queue the request for milestone view for classes setup to force calculate which are
 * associated with premium course. The specified validations are not enforced in this module, it is
 * left to caller
 *
 * @author ashish
 */
public interface RequestQueueService {

  /*
   * It is assumed that caller has verified course being not deleted and premium, and correctness of fw code
   */
  void enqueue(MilestoneQueueModel model);

  static RequestQueueService build() {
    return new RequestQueueServiceImpl(DbiRegistry.build());
  }

  static RequestQueueService build(DbiRegistry dbiRegistry) {
    return new RequestQueueServiceImpl(dbiRegistry);
  }

}
