package org.gooru.milestone.infra.services.queueoperators;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * @author ashish.
 */

interface RequestDequeuerDao {

  @SqlUpdate("delete from milestone_queue where id = :modelId")
  void dequeueRecord(@Bind("modelId") Long id);

}
