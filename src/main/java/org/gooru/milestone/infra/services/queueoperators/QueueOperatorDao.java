package org.gooru.milestone.infra.services.queueoperators;

import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.data.MilestoneQueueModel.MilestoneQueueModelMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

interface QueueOperatorDao {

  @SqlUpdate("update milestone_queue set status = 0 where status != 0")
  void initializeQueueStatus();

  @Mapper(MilestoneQueueModelMapper.class)
  @SqlQuery(
      "select id, course_id, status, fw_code, override from milestone_queue where status = 0 order by id asc limit 1")
  MilestoneQueueModel getNextDispatchableModel();

  @SqlUpdate("update milestone_queue set status = 1 where id = :modelId")
  void setQueuedRecordStatusAsDispatched(@Bind("modelId") Long id);

  @Mapper(MilestoneQueueModelMapper.class)
  @SqlQuery(
      "select id, course_id, status, fw_code, override from milestone_queue where id = :id")
  MilestoneQueueModel fetchSpecifiedRecord(@Bind("id") Long id);
}
