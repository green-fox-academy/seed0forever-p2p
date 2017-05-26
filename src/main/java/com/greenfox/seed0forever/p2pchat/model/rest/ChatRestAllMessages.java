package com.greenfox.seed0forever.p2pchat.model.rest;

import com.greenfox.seed0forever.p2pchat.model.ChatClient;
import com.greenfox.seed0forever.p2pchat.model.Message;
import java.util.List;

public class ChatRestAllMessages implements RestMessageObject {

  private List<Message> messages;
  private ChatClient client;

  public ChatRestAllMessages() {
  }

  public ChatRestAllMessages(
          List<Message> messages,
          ChatClient client) {
    this.messages = messages;
    this.client = client;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public ChatClient getClient() {
    return client;
  }

  public void setClient(ChatClient client) {
    this.client = client;
  }

  @Override
  public String toString() {
    return "ChatRestAllMessages{" +
            "messages=" + messages +
            ", client=" + client +
            '}';
  }
}
