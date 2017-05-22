package com.greenfox.seed0forever.p2pchat.service;

import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRestMessageService {

  // GitHub username of app developer
  // read from environment variable CHAT_APP_UNIQUE_ID
  private String chatAppUniqueId;
  // Heroku URL address of an other chat application
  // read from environment variable CHAT_APP_PEER_ADDRESSS
  private String chatAppPeerAddress;

  private BroadcastService broadcastService;
  private MessageService messageService;

  @Autowired
  public ChatRestMessageService(
          BroadcastService broadcastService,
          MessageService messageService) {
    this.chatAppUniqueId =
            System.getenv("CHAT_APP_UNIQUE_ID");
    this.chatAppPeerAddress =
            System.getenv("CHAT_APP_PEER_ADDRESS");
    this.broadcastService = broadcastService;
    this.messageService = messageService;
  }

  public void forwardAndSave(ChatRestMessage receivedRestMessage) {
    boolean messageIsFromThisClient = receivedRestMessage
            .getClient()
            .getId()
            .equalsIgnoreCase(chatAppUniqueId);

    if (!messageIsFromThisClient) {
      broadcastService.forwardMessage(receivedRestMessage);
    }

    messageService.saveWithoutIdCollision(receivedRestMessage.getMessage());
  }
}
