package org.gooru.milestone.infra.services.lookups;

import java.util.List;
import org.gooru.milestone.infra.data.GradeCompetencyMapModel;

/**
 * @author ashish.
 */

public interface GcmLookup {

  GradeCompetencyMapModel lookupUsingGutCode(String gutCode);

  static GcmLookup build(List<GradeCompetencyMapModel> models) {
    return new GcmLookupImpl(models);
  }

}
