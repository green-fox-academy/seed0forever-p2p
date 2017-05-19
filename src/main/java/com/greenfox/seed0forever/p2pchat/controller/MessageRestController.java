package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.OkRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.RestMessageObject;
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

  private LogService logService;
  private MessageService messageService;

  @Autowired
  public MessageRestController(LogService logService,
          MessageService messageService) {
    this.logService = logService;
    this.messageService = messageService;
  }

  @PostMapping("/receive")
  public RestMessageObject receiveMessage(@RequestBody ChatRestMessage receivedRestMessage) {
    logService.printLogIfNeeded(
            "/api/message/receive",
            "POST",
            "INFO",
            receivedRestMessage.toString());

    messageService.save(receivedRestMessage.getMessage());
    return new OkRestMessage("ok");
  }
}
