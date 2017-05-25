package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.OkRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.RestMessageObject;
import com.greenfox.seed0forever.p2pchat.model.rest.StatusOkOrErrorRestMessage;
import com.greenfox.seed0forever.p2pchat.service.ChatRestMessageService;
import com.greenfox.seed0forever.p2pchat.service.LogService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

  @RequestMapping(produces = "application/json")
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RestMessageObject> handleInvalidMessageReceived(
          MethodArgumentNotValidException exception) {

    String missingFields = "Missing fields(s): ";
    List<FieldError> fieldErrors =
            exception.getBindingResult().getFieldErrors();

    missingFields += fieldErrors.get(0).getField();
    for (int i = 1; i < fieldErrors.size(); i++) {
      FieldError fieldError = fieldErrors.get(i);
      missingFields += ", ";
      missingFields += fieldError.getField();
    }

    return new ResponseEntity<>(
            new StatusOkOrErrorRestMessage("error", missingFields),
            HttpStatus.BAD_REQUEST);
  }

  @PostMapping(value = "/receive", consumes = "application/json")
  public ResponseEntity<?> receiveMessage(
          @Valid @RequestBody ChatRestMessage receivedRestMessage) {

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
      RestMessageObject statusOkOrErrorRestMessage =
              new StatusOkOrErrorRestMessage(
                      "error",
                      "Missing objects");
      return new ResponseEntity<>(
              statusOkOrErrorRestMessage,
              HttpStatus.BAD_REQUEST);
    }
  }
}
