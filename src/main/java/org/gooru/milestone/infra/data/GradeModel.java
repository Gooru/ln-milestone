package org.gooru.milestone.infra.data;

/**
 * @author ashish.
 */

public class GradeModel {

  private Long id;
  private String gradeName;
  private String subjectCode;
  private int gradeSequence;
  private String fwCode;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGradeName() {
    return gradeName;
  }

  public void setGradeName(String gradeName) {
    this.gradeName = gradeName;
  }

  public String getSubjectCode() {
    return subjectCode;
  }

  public void setSubjectCode(String subjectCode) {
    this.subjectCode = subjectCode;
  }

  public int getGradeSequence() {
    return gradeSequence;
  }

  public void setGradeSequence(int gradeSequence) {
    this.gradeSequence = gradeSequence;
  }

  public String getFwCode() {
    return fwCode;
  }

  public void setFwCode(String fwCode) {
    this.fwCode = fwCode;
  }

  @Override
  public String toString() {
    return "GradeModel{" +
        "id=" + id +
        ", gradeName='" + gradeName + '\'' +
        ", subjectCode='" + subjectCode + '\'' +
        ", gradeSequence=" + gradeSequence +
        ", fwCode='" + fwCode + '\'' +
        '}';
  }
}
