package org.gooru.milestone.infra.services.milestonepersister;

import org.gooru.milestone.infra.services.algebra.competency.CompetencyLine;

/**
 * @author ashish.
 */

public interface MilestonePersister {

  void persistMilestone(CompetencyLine skyline);

}
