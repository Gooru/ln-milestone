package org.gooru.milestone.infra.services.classsetting;

import org.gooru.milestone.infra.jdbi.DBICreator;
import org.gooru.milestone.infra.services.algebra.competency.CompetencyLine;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

public interface GradeBoundFinder {

  CompetencyLine findAverageLineForGrade(Long gradeId);

  static GradeBoundFinder build(DBI dbi4ds) {
    return new GradeBoundFinderImpl(dbi4ds);
  }

  static GradeBoundFinder build() {
    return new GradeBoundFinderImpl(DBICreator.getDbiForDsdbDS());
  }

}
