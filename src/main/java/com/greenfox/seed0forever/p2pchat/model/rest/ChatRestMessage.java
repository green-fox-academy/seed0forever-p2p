package com.greenfox.seed0forever.p2pchat.model.rest;

import com.greenfox.seed0forever.p2pchat.model.ChatClient;
import com.greenfox.seed0forever.p2pchat.model.Message;

public class ChatRestMessage implements RestMessageObject {

  Message message;
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
            "message=" + message.toString() +
            ", client=" + client.toString() +
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
