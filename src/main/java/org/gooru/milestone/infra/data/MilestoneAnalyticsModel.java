package org.gooru.milestone.infra.data;

import java.util.UUID;

/**
 * @author ashish.
 */

public class MilestoneAnalyticsModel {

  private Long id;
  private String milestoneId;
  private UUID courseId;
  private UUID unitId;
  private UUID lessonId;
  private String fwCode;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMilestoneId() {
    return milestoneId;
  }

  public void setMilestoneId(String milestoneId) {
    this.milestoneId = milestoneId;
  }

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

  public UUID getLessonId() {
    return lessonId;
  }

  public void setLessonId(UUID lessonId) {
    this.lessonId = lessonId;
  }

  public String getFwCode() {
    return fwCode;
  }

  public void setFwCode(String fwCode) {
    this.fwCode = fwCode;
  }

  @Override
  public String toString() {
    return "MilestoneAnalyticsModel{" +
        "id=" + id +
        ", milestoneId='" + milestoneId + '\'' +
        ", courseId=" + courseId +
        ", unitId=" + unitId +
        ", lessonId=" + lessonId +
        ", fwCode='" + fwCode + '\'' +
        '}';
  }
}
