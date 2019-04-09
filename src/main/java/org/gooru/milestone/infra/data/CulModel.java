package org.gooru.milestone.infra.data;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author ashish.
 */

public class CulModel {
  private UUID courseId;
  private UUID unitId;
  private Integer unitSequence;
  private UUID lessonId;
  private Integer lessonSequence;
  private List<String> gutCodes;

  public UUID getCourseId() {
    return courseId;
  }

  public void setCourseId(UUID courseId) {
    this.courseId = courseId;
  }

  public UUID getUnitId() {
    return unitId;
  }

  public void setUnitId(UUID unitId) {
    this.unitId = unitId;
  }

  public Integer getUnitSequence() {
    return unitSequence;
  }

  public void setUnitSequence(Integer unitSequence) {
    this.unitSequence = unitSequence;
  }

  public UUID getLessonId() {
    return lessonId;
  }

  public void setLessonId(UUID lessonId) {
    this.lessonId = lessonId;
  }

  public Integer getLessonSequence() {
    return lessonSequence;
  }

  public void setLessonSequence(Integer lessonSequence) {
    this.lessonSequence = lessonSequence;
  }

  public List<String> getGutCodes() {
    return gutCodes;
  }

  public void setGutCodes(List<String> gutCodes) {
    this.gutCodes = gutCodes;
  }

  @Override
  public String toString() {
    String gutCodesParsed = "[]";
    if (gutCodes != null) {
      gutCodesParsed = Arrays.toString(gutCodes.toArray());
    }
    return "CulModel{" +
        "courseId=" + courseId +
        ", unitId=" + unitId +
        ", unitSequence=" + unitSequence +
        ", lessonId=" + lessonId +
        ", lessonSequence=" + lessonSequence +
        ", gutCodes=" + gutCodesParsed +
        '}';
  }
}
