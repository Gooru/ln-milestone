package org.gooru.milestone.infra.services.queueoperators;

import static org.junit.Assert.*;

import java.util.UUID;
import org.gooru.milestone.DBITestHelper;
import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

public class ProcessingEligibilityVerifierDaoTest {

  private DBI dbi = new DBITestHelper().getDBI();
  private ProcessingEligibilityVerifierDao dao = dbi
      .onDemand(ProcessingEligibilityVerifierDao.class);
  private static MilestoneQueueModel model = initializeModel();

  private static MilestoneQueueModel initializeModel() {
    MilestoneQueueModel model = new MilestoneQueueModel();
    model.setClassId(UUID.fromString("cd89974c-e8d2-4de8-ad3f-ef62ec8f5e39"));
    model.setCourseId(UUID.fromString("cd89974c-e8d2-4de8-ad3f-ef62ec8f5e39"));
    model.setCategory(2);
    model.setStatus(1);
    model.setUserId(UUID.fromString("cd89974c-e8d2-4de8-ad3f-ef62ec8f5e39"));
    return model;
  }

  @Test
  public void profileBaselineDoneForUserInIL() {
/*
    boolean b = dao.profileBaselineDoneForUserInIL(model);
    assertTrue(b);
*/
  }

  @Test
  public void profileBaselineDoneForUserInClass() {
    boolean b = dao.ilpAlreadyDoneForUser(model);
    assertTrue(b);
  }

  @Test
  public void isQueuedRecordStillDispatched() {
    dao.isQueuedRecordStillDispatched(1L);
  }
}