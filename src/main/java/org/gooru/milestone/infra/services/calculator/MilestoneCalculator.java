package org.gooru.milestone.infra.services.calculator;

import java.util.List;
import org.gooru.milestone.infra.data.CulModel;
import org.gooru.milestone.infra.data.MilestoneLessonMapModel;
import org.gooru.milestone.infra.data.ProcessingContext;
import org.gooru.milestone.infra.services.lookups.GcmLookup;
import org.gooru.milestone.infra.services.lookups.GradeLookup;

/**
 * @author ashish.
 */

public interface MilestoneCalculator {

  List<MilestoneLessonMapModel> calculateMilestones(
      List<CulModel> culModels);

  static MilestoneCalculator build(ProcessingContext context, GradeLookup gradeLookup,
      GcmLookup gcmLookup) {
    return new MilestoneCalculatorImpl(context, gradeLookup, gcmLookup);
  }


}
