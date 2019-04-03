package org.gooru.milestone.infra.data;

import java.util.UUID;

/**
 * @author ashish.
 */

public class ProcessingContext {

  private final MilestoneQueueModel model;
  private String subject;

  public MilestoneQueueModel getModel() {
    return model;
  }

  private ProcessingContext(MilestoneQueueModel model) {
    this.model = model;
  }


  public static ProcessingContext buildFromQueueModel(MilestoneQueueModel model) {
    return new ProcessingContext(model);
  }

  public UUID getCourseId() {
    return model.getCourseId();
  }

  public Boolean getOverride() {
    return model.getOverride();
  }

  public String getFwCode() {
    return model.getFwCode();
  }

  public String getSubject() {
    return subject;
  }


  public void setSubject(String subject) {
    if (this.subject == null) {
      this.subject = subject;
    } else {
      throw new IllegalStateException(
          "Tried to initialize the subject while it is already initialized");
    }
  }

  @Override
  public String toString() {
    return "ProcessingContext{" +
        "model=" + model.toJson() +
        ", subject='" + subject + '\'' +
        '}';
  }
}
