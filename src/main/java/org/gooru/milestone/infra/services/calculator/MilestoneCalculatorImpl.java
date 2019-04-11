package org.gooru.milestone.infra.services.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.gooru.milestone.infra.data.CulModel;
import org.gooru.milestone.infra.data.GradeCompetencyMapModel;
import org.gooru.milestone.infra.data.GradeModel;
import org.gooru.milestone.infra.data.MilestoneLessonMapModel;
import org.gooru.milestone.infra.data.ProcessingContext;
import org.gooru.milestone.infra.services.idgenerators.MilestoneIdGenerator;
import org.gooru.milestone.infra.services.lookups.GcmLookup;
import org.gooru.milestone.infra.services.lookups.GradeLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class MilestoneCalculatorImpl implements MilestoneCalculator {

  private final ProcessingContext context;
  private final GradeLookup gradeLookup;
  private final GcmLookup gcmLookup;
  private List<CulModel> culModels;
  private MilestoneIdGenerator milestoneIdGenerator;
  private List<MilestoneLessonMapModel> milestones;

  private static final Logger LOGGER = LoggerFactory.getLogger(MilestoneCalculator.class);

  MilestoneCalculatorImpl(ProcessingContext context, GradeLookup gradeLookup,
      GcmLookup gcmLookup) {

    this.context = context;
    this.gradeLookup = gradeLookup;
    this.gcmLookup = gcmLookup;
  }

  @Override
  public List<MilestoneLessonMapModel> calculateMilestones(List<CulModel> culModels) {
    this.culModels = culModels;
    LOGGER.debug("Calculating milestones");
    if (culModels == null || culModels.isEmpty() || gcmLookup == null || gradeLookup == null
        || context == null || context.getCourseId() == null || context.getFwCode() == null) {
      LOGGER.warn("Not having enough data to calculate milestones");
      return Collections.emptyList();
    }
    return calculate();
  }

  private List<MilestoneLessonMapModel> calculate() {
    milestones = new ArrayList<>(culModels.size());
    milestoneIdGenerator = MilestoneIdGenerator.build();

    for (CulModel culModel : culModels) {
      List<String> gutCodes = culModel.getGutCodes();
      if (gutCodes != null && !gutCodes.isEmpty()) {
        for (String gutcode : gutCodes) {
          calculateForSpecificGutCode(culModel, gutcode);
        }
      } else {
        LOGGER.info("Gut codes empty or null for CulModel: '{}'", culModel.toString());
      }
    }
    LOGGER.info("Done processing, returning '{}' lessons in milestones", milestones.size());
    return milestones;
  }

  private void calculateForSpecificGutCode(CulModel culModel, String gutcode) {
    GradeCompetencyMapModel gcmModel = gcmLookup.lookupUsingGutCode(gutcode);
    GradeModel gradeModel;
    if (gcmModel != null) {
      gradeModel = gradeLookup.lookupUsingGradeId(gcmModel.getGradeId());
      if (gradeModel != null) {
        updateResultWithMilestoneLessonMapModel(culModel, gcmModel, gradeModel);
      } else {
        LOGGER.info(
            "Not able to find gradeModel for gutCode: '{}' for culModel: '{}' for gradeId: '{}'",
            gutcode, culModel.toString(), gcmModel.getGradeId());
      }
    } else {
      LOGGER.info("Not able to find gcmModel for gutCode: '{}' for culModel: '{}'", gutcode,
          culModel.toString());
    }
  }

  private void updateResultWithMilestoneLessonMapModel(CulModel culModel,
      GradeCompetencyMapModel gcmModel, GradeModel gradeModel) {

    MilestoneLessonMapModel result = new MilestoneLessonMapModel();
    result.setCourseId(culModel.getCourseId());
    result.setUnitId(culModel.getUnitId());
    result.setLessonId(culModel.getLessonId());
    result.setGradeId(gradeModel.getId());
    result.setGradeName(gradeModel.getGradeName());
    result.setGradeSeq(gradeModel.getGradeSequence());
    result.setCompCode(gcmModel.getCompCode());
    result.setCompName(gcmModel.getCompName());
    result.setCompStudentDesc(gcmModel.getCompStudentDesc());
    result.setCompSeq(gcmModel.getCompSequence());
    result.setDomainId(gcmModel.getDomainId());
    result.setDomainCode(gcmModel.getDomainCode());
    result.setDomainSeq(gcmModel.getDomainSequence());
    result.setFwCode(gcmModel.getFwCode());
    result.setSubjectCode(gcmModel.getSubjectCode());

    // Populate the milestone ID as well, and avoid another iteration
    result.setMilestoneId(milestoneIdGenerator.generateId(result));

    milestones.add(result);
  }
}
