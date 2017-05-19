package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.OkRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.RestMessageObject;
import com.greenfox.seed0forever.p2pchat.service.BroadcastService;
import com.greenfox.seed0forever.p2pchat.service.LogService;
import com.greenfox.seed0forever.p2pchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
@CrossOrigin("*")
public class MessageRestController {

  // GitHub username of app developer
  // read from environment variable CHAT_APP_UNIQUE_ID
  private String chatAppUniqueId;
  // Heroku URL address of an other chat application
  // read from environment variable CHAT_APP_PEER_ADDRESSS
  private String chatAppPeerAddress;


  private BroadcastService broadcastService;
  private LogService logService;
  private MessageService messageService;

  @Autowired
  public MessageRestController(
          BroadcastService broadcastService,
          LogService logService,
          MessageService messageService) {
    this.broadcastService = broadcastService;
    this.logService = logService;
    this.messageService = messageService;

    this.chatAppUniqueId = System.getenv("CHAT_APP_UNIQUE_ID");
    this.chatAppPeerAddress = System.getenv("CHAT_APP_PEER_ADDRESS");
  }

  @PostMapping("/receive")
  public RestMessageObject receiveMessage(@RequestBody ChatRestMessage receivedRestMessage) {
    logService.printLogIfNeeded(
            "/api/message/receive",
            "POST",
            "INFO",
            receivedRestMessage.toString());


    boolean messageIsFromThisClient = receivedRestMessage
            .getClient()
            .getId()
            .equalsIgnoreCase(chatAppUniqueId);

    if (!messageIsFromThisClient) {
      broadcastService.forwardMessage(receivedRestMessage);
    }

    messageService.saveWithoutIdCollision(receivedRestMessage.getMessage());

    return new OkRestMessage("ok");
  }
}
