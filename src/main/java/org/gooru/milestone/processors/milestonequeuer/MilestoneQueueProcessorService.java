package org.gooru.milestone.processors.milestonequeuer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.gooru.milestone.infra.services.queuerequest.RequestQueueService;
import org.gooru.milestone.infra.utils.CollectionUtils;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class MilestoneQueueProcessorService {

  private final DBI dbiForDefaultDS;
  private final DBI dbiForDsdbDS;
  private MilestoneQueueCommand command;
  private static final Logger LOGGER = LoggerFactory
      .getLogger(MilestoneQueueProcessorService.class);
  private MilestoneQueueDao milestoneQueueDao;
  private List<UUID> members;
  private Set<String> usersSet;

  MilestoneQueueProcessorService(DBI dbiForDefaultDS,
      DBI dbiForDsdbDS) {

    this.dbiForDefaultDS = dbiForDefaultDS;
    this.dbiForDsdbDS = dbiForDsdbDS;
  }

  void calculateInitialSkyline(MilestoneQueueCommand command) {
    this.command = command;
    LOGGER.debug("Command is : {}", command.toString());
    validateClassAndMembersAndAuthorization();
    validateCourseAndIsPremium();
    initializeMembers();
    RequestQueueService.build(dbiForDefaultDS)
        .enqueueForClassUsingForceCalculateWithPremiunCourse(command.getClassId(), members);
  }

  private void initializeMembers() {
    members = usersSet.stream().map(UUID::fromString).collect(Collectors.toList());
  }

  private void validateCourseAndIsPremium() {
    if (!fetchDao().validateCourseAndItsPremiumness(command.getClassId())) {
      throw new IllegalArgumentException("Invalid or non premium course for specified class");
    }
  }

  private void validateClassAndMembersAndAuthorization() {
    // validate class (not archive/not deleted and force_calculate_ilp), memberships provided(active and with specified class),
    // origin set and teacher/co teacher authorization
    usersSet = new HashSet<>(command.getUsersList());
    int userCountInRequest = usersSet.size();
    int usersCountInDb = fetchDao().validatedCountForClassMembers(command.getClassId(),
        CollectionUtils.convertToSqlArrayOfUUID(usersSet), command.getTeacherId());
    if (userCountInRequest != usersCountInDb) {
      throw new IllegalArgumentException("Incorrect class or students or teacher");
    }
  }

  private MilestoneQueueDao fetchDao() {
    if (milestoneQueueDao == null) {
      milestoneQueueDao = dbiForDefaultDS
          .onDemand(MilestoneQueueDao.class);
    }
    return milestoneQueueDao;
  }
}
