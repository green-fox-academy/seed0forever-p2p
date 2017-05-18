package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.OkRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.RestMessageObject;
import com.greenfox.seed0forever.p2pchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageRestController {

  private MessageService messageService;

  @Autowired
  public MessageRestController(
          MessageService messageService) {
    this.messageService = messageService;
  }

  @CrossOrigin("*")
  @PostMapping("/receive")
  public RestMessageObject receiveMessage(@RequestBody ChatRestMessage receivedRestMessage) {
    messageService.save(receivedRestMessage.getMessage());
    return new OkRestMessage("ok");
  }
}
