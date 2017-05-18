package com.greenfox.seed0forever.p2pchat.model.rest;

public class ErrorRestMessage implements RestMessageObject {

  private String status;
  private String message;

  public ErrorRestMessage() {
  }

  public ErrorRestMessage(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
