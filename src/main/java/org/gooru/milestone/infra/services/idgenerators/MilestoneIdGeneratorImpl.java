package org.gooru.milestone.infra.services.idgenerators;

import org.gooru.milestone.infra.data.MilestoneLessonMapModel;

/**
 * Use the courseId + SEP + fwCode + SEP + gradeId + SEP + gradeSeq + SEP + version
 *
 * @author ashish.
 */

class MilestoneIdGeneratorImpl implements MilestoneIdGenerator {

  private static final String VERSION = "v1";
  private static final String SEP = "_";

  @Override
  public String generateId(MilestoneLessonMapModel model) {

    if (model == null || model.getCourseId() == null || model.getFwCode() == null
        || model.getGradeId() == null || model.getGradeSeq() == 0) {
      throw new IllegalArgumentException("Invalid data point to generate Milestone ID");
    }
    return model.getCourseId() + SEP + model.getFwCode() + SEP + model.getGradeId() + SEP + model
        .getGradeSeq() + SEP + VERSION;
  }
}
