package org.gooru.milestone.infra.services.gutcodecolumnfinder;

import org.gooru.milestone.infra.components.AppConfiguration;

/**
 * @author ashish.
 */

class LessonColumnForGutCodesImpl implements LessonColumnForGutCodes {

  @Override
  public boolean gutCodeColumnIsGutCodes() {
    return AppConfiguration.getInstance().shouldReadGutCodesColumnForLessonGutCodes();
  }

  @Override
  public boolean gutCodeColumnIsAggregatedGutCodes() {
    return !AppConfiguration.getInstance().shouldReadGutCodesColumnForLessonGutCodes();
  }
}
