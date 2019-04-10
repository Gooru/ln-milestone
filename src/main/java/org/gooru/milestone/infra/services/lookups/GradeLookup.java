package org.gooru.milestone.infra.services.lookups;

import java.util.List;
import org.gooru.milestone.infra.data.GradeModel;

/**
 * @author ashish.
 */

public interface GradeLookup {

  GradeModel lookupUsingGradeId(Long gradeId);

  static GradeLookup build(List<GradeModel> models) {
    return new GradeLookupImpl(models);
  }
}
