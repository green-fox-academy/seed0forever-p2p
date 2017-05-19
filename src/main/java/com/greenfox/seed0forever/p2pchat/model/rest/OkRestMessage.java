package com.greenfox.seed0forever.p2pchat.model.rest;

public class OkRestMessage implements RestMessageObject {

  private String status;

  public OkRestMessage() {
  }

  public OkRestMessage(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "OkRestMessage{" +
            "status='" +
            ((status != null)
                    ? status
                    : "null") +
            '\'' +
            '}';
  }
}
