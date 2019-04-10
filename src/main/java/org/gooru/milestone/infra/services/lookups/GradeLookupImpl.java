package org.gooru.milestone.infra.services.lookups;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gooru.milestone.infra.data.GradeModel;

/**
 * @author ashish.
 */

class GradeLookupImpl implements GradeLookup {

  private final List<GradeModel> models;
  private transient boolean initialized = false;
  private Map<Long, GradeModel> lookupMap;

  GradeLookupImpl(List<GradeModel> models) {

    this.models = models;
  }

  @Override
  public GradeModel lookupUsingGradeId(Long gradeId) {
    initialize();
    return lookupMap.get(gradeId);
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
    for (GradeModel model : models) {
      lookupMap.put(model.getId(), model);
    }
  }

}
