package org.gooru.milestone.infra.services.idgenerators;

import org.gooru.milestone.infra.data.MilestoneLessonMapModel;

/**
 * @author ashish.
 */

public interface MilestoneIdGenerator {

  String generateId(MilestoneLessonMapModel model);

  static MilestoneIdGenerator build() {
    return new MilestoneIdGeneratorImpl();
  }

}
