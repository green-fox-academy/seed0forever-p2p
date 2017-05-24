package com.greenfox.seed0forever.p2pchat.model.rest;

import com.greenfox.seed0forever.p2pchat.model.ChatClient;
import com.greenfox.seed0forever.p2pchat.model.Message;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ChatRestMessage implements RestMessageObject {

  @Valid
  @NotNull
  Message message;
  @Valid
  @NotNull
  ChatClient client;

  public ChatRestMessage() {
  }

  public ChatRestMessage(Message message, ChatClient client) {
    this.message = message;
    this.client = client;
  }

  @Override
  public String toString() {
    return "ChatRestMessage{" +
            "message=" +
            ((message != null)
                    ? message.toString()
                    : "null") +
            ", client=" +
            ((client != null)
                    ? client.toString()
                    : "null") +
            '}';
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public ChatClient getClient() {
    return client;
  }

  public void setClient(ChatClient client) {
    this.client = client;
  }
}
