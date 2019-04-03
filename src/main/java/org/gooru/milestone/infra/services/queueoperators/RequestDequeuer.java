package org.gooru.milestone.infra.services.queueoperators;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.jdbi.DBICreator;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

public interface RequestDequeuer {

  void dequeue(MilestoneQueueModel model);

  static RequestDequeuer build(DBI dbi) {
    return new RequestDequeuerImpl(dbi);
  }

  static RequestDequeuer build() {
    return new RequestDequeuerImpl(DBICreator.getDbiForDefaultDS());
  }

}
