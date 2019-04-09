package org.gooru.milestone.infra.services.milestonepersister;

import org.gooru.milestone.infra.data.MilestoneLessonMapModel;

/**
 * @author ashish.
 */

public interface MilestonePersister {

  void persistMilestone(MilestoneLessonMapModel model);

}
