package org.gooru.milestone.infra.services.gutcodecolumnfinder;

/**
 * @author ashish.
 */

public interface LessonColumnForGutCodes {

  boolean gutCodeColumnIsGutCodes();

  boolean gutCodeColumnIsAggregatedGutCodes();

  static LessonColumnForGutCodes build() {
    return new LessonColumnForGutCodesImpl();
  }
}
