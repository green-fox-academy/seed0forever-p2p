package com.greenfox.seed0forever.p2pchat.service;

import com.greenfox.seed0forever.p2pchat.model.ChatClient;
import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestAllMessages;
import com.greenfox.seed0forever.p2pchat.model.rest.RestMessageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRestAllMessagesService {

  private final MessageService messageService;
  // GitHub username of app developer
  // read from environment variable CHAT_APP_UNIQUE_ID
  private String chatAppUniqueId;

  @Autowired
  public ChatRestAllMessagesService(
          MessageService messageService) {
    this.messageService = messageService;
    this.chatAppUniqueId =
            System.getenv("CHAT_APP_UNIQUE_ID");
  }

  public RestMessageObject respondWithAllMessages() {
    ChatRestAllMessages chatRestAllMessages = new ChatRestAllMessages();
    chatRestAllMessages.setClient(
            new ChatClient(chatAppUniqueId));
    chatRestAllMessages.setMessages(messageService.listAll());

    return chatRestAllMessages;
  }
}
