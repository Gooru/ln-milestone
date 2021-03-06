package org.gooru.milestone.infra.services.subjectinferer;

import java.util.UUID;
import org.gooru.milestone.infra.jdbi.DBICreator;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */
public interface SubjectInferer {

  String inferSubjectForCourse(UUID courseId);

  static SubjectInferer build() {
    return new SubjectInfererImpl(DBICreator.getDbiForDefaultDS());
  }

  static SubjectInferer build(DBI dbi) {
    return new SubjectInfererImpl(dbi);
  }
}
