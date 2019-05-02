package org.gooru.milestone.infra.data;

import java.util.UUID;

/**
 * @author ashish.
 */

public class MilestoneLessonMapModel {

  private Long id;
  private String milestoneId;
  private UUID courseId;
  private UUID unitId;
  private UUID lessonId;
  private Long gradeId;
  private String gradeName;
  private int gradeSeq;
  private String fwCode;
  private String subjectCode;
  private Long domainId;
  private int domainSeq;
  private String domainCode;
  private String domainName;
  private String compCode;
  private String compName;
  private String compStudentDesc;
  private int compSeq;

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

  public Long getGradeId() {
    return gradeId;
  }

  public void setGradeId(Long gradeId) {
    this.gradeId = gradeId;
  }

  public String getGradeName() {
    return gradeName;
  }

  public void setGradeName(String gradeName) {
    this.gradeName = gradeName;
  }

  public int getGradeSeq() {
    return gradeSeq;
  }

  public void setGradeSeq(int gradeSeq) {
    this.gradeSeq = gradeSeq;
  }

  public String getFwCode() {
    return fwCode;
  }

  public void setFwCode(String fwCode) {
    this.fwCode = fwCode;
  }

  public String getSubjectCode() {
    return subjectCode;
  }

  public void setSubjectCode(String subjectCode) {
    this.subjectCode = subjectCode;
  }

  public Long getDomainId() {
    return domainId;
  }

  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }

  public int getDomainSeq() {
    return domainSeq;
  }

  public String getDomainName() {
    return domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  public void setDomainSeq(int domainSeq) {
    this.domainSeq = domainSeq;
  }

  public String getDomainCode() {
    return domainCode;
  }

  public void setDomainCode(String domainCode) {
    this.domainCode = domainCode;
  }

  public String getCompCode() {
    return compCode;
  }

  public void setCompCode(String compCode) {
    this.compCode = compCode;
  }

  public String getCompName() {
    return compName;
  }

  public void setCompName(String compName) {
    this.compName = compName;
  }

  public String getCompStudentDesc() {
    return compStudentDesc;
  }

  public void setCompStudentDesc(String compStudentDesc) {
    this.compStudentDesc = compStudentDesc;
  }

  public int getCompSeq() {
    return compSeq;
  }

  public void setCompSeq(int compSeq) {
    this.compSeq = compSeq;
  }

  @Override
  public String toString() {
    return "MilestoneLessonMapModel{" +
        "id=" + id +
        ", milestoneId='" + milestoneId + '\'' +
        ", courseId=" + courseId +
        ", unitId=" + unitId +
        ", lessonId=" + lessonId +
        ", gradeId=" + gradeId +
        ", gradeName='" + gradeName + '\'' +
        ", gradeSeq=" + gradeSeq +
        ", fwCode='" + fwCode + '\'' +
        ", subjectCode='" + subjectCode + '\'' +
        ", domainId=" + domainId +
        ", domainName=" + domainName +
        ", domainSeq=" + domainSeq +
        ", domainCode='" + domainCode + '\'' +
        ", compCode='" + compCode + '\'' +
        ", compName='" + compName + '\'' +
        ", compStudentDesc='" + compStudentDesc + '\'' +
        ", compSeq=" + compSeq +
        '}';
  }
}
