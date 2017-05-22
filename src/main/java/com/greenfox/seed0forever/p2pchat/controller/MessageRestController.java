package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.ErrorRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.OkRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.RestMessageObject;
import com.greenfox.seed0forever.p2pchat.service.ChatRestMessageService;
import com.greenfox.seed0forever.p2pchat.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
@CrossOrigin("*")
public class MessageRestController {

  private ChatRestMessageService chatRestMessageService;
  private LogService logService;

  @Autowired
  public MessageRestController(
          ChatRestMessageService chatRestMessageService,
          LogService logService) {
    this.chatRestMessageService = chatRestMessageService;
    this.logService = logService;
  }

  @PostMapping("/receive")
  public ResponseEntity<?> receiveMessage(
          @RequestBody ChatRestMessage receivedRestMessage) {

    boolean messageExists = receivedRestMessage != null;

    logService.printLogIfNeeded(
            "/api/message/receive",
            "POST",
            "INFO",
            messageExists
                    ? receivedRestMessage.toString()
                    : "[no message received by receiveMessage()");

    if (messageExists) {
      chatRestMessageService.forwardAndSave(receivedRestMessage);
      return new ResponseEntity<>(
              new OkRestMessage("ok"),
              HttpStatus.OK);

    } else {
      RestMessageObject errorRestMessage =
              new ErrorRestMessage(
                      "error",
                      "Missing objects");
      return new ResponseEntity<>(
              errorRestMessage,
              HttpStatus.UNAUTHORIZED);
    }
  }
}
