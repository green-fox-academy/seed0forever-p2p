package com.greenfox.seed0forever.p2pchat.service;

import com.greenfox.seed0forever.p2pchat.model.Message;
import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import com.greenfox.seed0forever.p2pchat.service.async.AsyncBroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRestMessageService {

  private final AsyncBroadcastService asyncBroadcastService;
  private final LogService logService;
  private final MessageService messageService;
  // GitHub username of app developer
  // read from environment variable CHAT_APP_UNIQUE_ID
  private String chatAppUniqueId;
  // Heroku URL address of an other chat application
  // read from environment variable CHAT_APP_PEER_ADDRESSS
  private String chatAppPeerAddress;

  @Autowired
  public ChatRestMessageService(
          AsyncBroadcastService asyncBroadcastService,
          LogService logService,
          MessageService messageService) {
    this.chatAppUniqueId =
            System.getenv("CHAT_APP_UNIQUE_ID");
    this.chatAppPeerAddress =
            System.getenv("CHAT_APP_PEER_ADDRESS");
    this.asyncBroadcastService = asyncBroadcastService;
    this.logService = logService;
    this.messageService = messageService;
  }

  public void forwardAndSave(ChatRestMessage receivedRestMessage) {
    boolean messageIsFromThisClient = receivedRestMessage
            .getClient()
            .getId()
            .equalsIgnoreCase(chatAppUniqueId);
    Message message = receivedRestMessage.getMessage();
    boolean messageAlreadySaved = messageService.existsByUserAndTime(
            message.getUsername(),
            message.getTimestamp());

    if (!messageIsFromThisClient
            && !messageAlreadySaved) {
      asyncBroadcastService.forwardMessage(receivedRestMessage);

    } else if (messageAlreadySaved) {
      logService.printLogIfNeeded(
              "[ChatRestMessageService]",
              "asyncBroadcastService.forwardMessage",
              "INFO",
              "filtered message, already exists: "
                      + ", message = ");
    } else {
      messageService.saveWithoutIdCollision(receivedRestMessage.getMessage());

      logService.printLogIfNeeded(
              "[ChatRestMessageService]",
              "asyncBroadcastService.forwardMessage",
              "INFO",
              "saved message: "
                      + ", message = ");
    }
  }
}
