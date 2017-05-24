package com.greenfox.seed0forever.p2pchat.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChatClient {

  // CHAT_APP_UNIQUE_ID of the message sender client app
  @NotNull
  @Size(min=1)
  private String id;

  public ChatClient() {
  }

  public ChatClient(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "ChatClient{" +
            "id='" + id + '\'' +
            '}';
  }
}
