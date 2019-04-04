package org.gooru.milestone.infra.services.queuerequest;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * @author ashish on 18/5/18.
 */
interface RequestQueueDao {


  @SqlUpdate(
      "insert into milestone_queue(course_id, fw_code, override, status) values (:courseId, :fwCode,"
          + " :override, 0) ON CONFLICT DO NOTHING")
  void queueRequest(@BindBean MilestoneQueueModel model);

}
