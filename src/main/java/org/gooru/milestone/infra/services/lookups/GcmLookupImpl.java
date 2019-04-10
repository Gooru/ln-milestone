package org.gooru.milestone.infra.services.lookups;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gooru.milestone.infra.data.GradeCompetencyMapModel;

/**
 * @author ashish.
 */

public class GcmLookupImpl implements GcmLookup {

  private final List<GradeCompetencyMapModel> models;
  private Map<String, GradeCompetencyMapModel> lookupMap;
  private transient boolean initialized = false;

  GcmLookupImpl(List<GradeCompetencyMapModel> models) {
    this.models = models;
  }

  @Override
  public GradeCompetencyMapModel lookupUsingGutCode(String gutCode) {
    initialize();
    return lookupMap.get(gutCode);
  }

  private void initialize() {
    if (initialized) {
      return;
    }

    if (models == null || models.isEmpty()) {
      lookupMap = Collections.emptyMap();
    } else {
      lookupMap = new HashMap<>(models.size());
      populateMapFromModels();
    }

    initialized = true;
  }

  private void populateMapFromModels() {
    for (GradeCompetencyMapModel model : models) {
      lookupMap.put(model.getCompCode(), model);
    }
  }

}
