package org.gooru.milestone.infra.data;

/**
 * @author ashish.
 */

public class GradeCompetencyMapModel {

  private Long id;
  private Long gradeId;
  private String subjectCode;
  private String fwCode;
  private Long domainId;
  private String domainCode;
  private String domainName;
  private int domainSequence;
  private String compCode;
  private String compName;
  private String compStudentDesc;
  private int compSequence;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getGradeId() {
    return gradeId;
  }

  public void setGradeId(Long gradeId) {
    this.gradeId = gradeId;
  }

  public String getSubjectCode() {
    return subjectCode;
  }

  public void setSubjectCode(String subjectCode) {
    this.subjectCode = subjectCode;
  }

  public String getFwCode() {
    return fwCode;
  }

  public void setFwCode(String fwCode) {
    this.fwCode = fwCode;
  }

  public Long getDomainId() {
    return domainId;
  }

  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }

  public String getDomainCode() {
    return domainCode;
  }

  public void setDomainCode(String domainCode) {
    this.domainCode = domainCode;
  }

  public int getDomainSequence() {
    return domainSequence;
  }

  public String getDomainName() {
    return domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  public void setDomainSequence(int domainSequence) {
    this.domainSequence = domainSequence;
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

  public int getCompSequence() {
    return compSequence;
  }

  public void setCompSequence(int compSequence) {
    this.compSequence = compSequence;
  }

  @Override
  public String toString() {
    return "GradeCompetencyMapModel{" +
        "id=" + id +
        ", gradeId=" + gradeId +
        ", subjectCode='" + subjectCode + '\'' +
        ", fwCode='" + fwCode + '\'' +
        ", domainId=" + domainId +
        ", domainCode='" + domainCode + '\'' +
        ", domainName=" + domainName +
        ", domainSequence=" + domainSequence +
        ", compCode='" + compCode + '\'' +
        ", compName='" + compName + '\'' +
        ", compStudentDesc='" + compStudentDesc + '\'' +
        ", compSequence=" + compSequence +
        '}';
  }
}
