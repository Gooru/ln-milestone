package org.gooru.milestone.infra.services.milestonepersister;

import java.util.Arrays;
import java.util.List;
import org.gooru.milestone.DBITestHelper;
import org.gooru.milestone.infra.data.MilestoneQueueModel;
import org.gooru.milestone.infra.data.ProcessingContext;
import org.gooru.milestone.infra.services.algebra.competency.Competency;
import org.gooru.milestone.infra.services.algebra.competency.CompetencyLine;
import org.gooru.milestone.infra.services.algebra.competency.CompetencyMap;
import org.gooru.milestone.infra.services.queueoperators.QueueRecordFetcherService;
import org.gooru.milestone.infra.services.settings.SettingsModel;
import org.gooru.milestone.infra.services.subjectinferer.SubjectInferer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MilestonePersisterTest {

  DBI dbi4ds;
  CompetencyLine competencyLine;
  ProcessingContext context;
  private static final Logger LOGGER = LoggerFactory.getLogger(MilestonePersisterTest.class);

  @Before
  public void setUp() throws Exception {
    dbi4ds = new DBITestHelper().getDBI();
    initializeCompetencyLine();
    initializeContext();
  }

  private void initializeContext() {
    LOGGER.info("Initializing model from queue record");
    MilestoneQueueModel model = QueueRecordFetcherService.build(dbi4ds).fetchRecordById(5L);
    LOGGER.info("MilestoneQueueModel: '{}", model);

    LOGGER.info("Building processing context");
    context = ProcessingContext.buildFromQueueModel(model);
    LOGGER.info("Context is: '{}'", context);

    LOGGER.info("Creating SettingsModel");
    SettingsModel sm = SettingsModel.build(dbi4ds, context.getClassId(), context.getUserId());
    LOGGER.info("SettingsModel: '{}", sm);
    context.setSettingsModel(sm);

    LOGGER.info("Inferring subject");
    String subject = SubjectInferer.build(dbi4ds).inferSubjectForClass(context.getClassId());
    LOGGER.info("Subject: '{}'", subject);
    context.setSubject(subject);

  }

  private void initializeCompetencyLine() {
    Competency c1 = Competency.build("MA", "D1", "D1C4", 4);
    Competency c2 = Competency.build("MA", "D1", "D1C6", 6);
    Competency c3 = Competency.build("MA", "D2", "D2C2", 2);
    Competency c4 = Competency.build("MA", "D2", "D2C7", 7);
    Competency c5 = Competency.build("MA", "D3", "D3C3", 3);
    Competency c6 = Competency.build("MA", "D3", "D3C5", 5);

    List<Competency> competencies = Arrays.asList(c1, c2, c3, c4, c5, c6);

    CompetencyMap competencyMap = CompetencyMap.build(competencies);
    CompetencyLine skyline = competencyMap.getCeilingLine();
    competencyLine = competencyMap.getFloorLine();
    LOGGER.info("Done initializing competency line");
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void persistLearnerProfileTest() {
    LOGGER.info("Starting test");
    LOGGER.info("Persisting for non diagnostic");
    MilestonePersister.buildForDiagnosticPlay(dbi4ds, context)
        .persistLearnerProfile(competencyLine);
    LOGGER.info("Done test");
  }

}