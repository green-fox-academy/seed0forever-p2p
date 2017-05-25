package com.greenfox.seed0forever.p2pchat.service.async;

import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.ErrorRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.RestMessageObject;
import com.greenfox.seed0forever.p2pchat.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AsyncBroadcastService {

  private final LogService logService;
  private final RestTemplate restTemplate;

  // Heroku URL address of an other chat application
  // read from environment variable CHAT_APP_PEER_ADDRESSS
  private String chatAppPeerAddress;

  @Autowired
  public AsyncBroadcastService(
          LogService logService,
          RestTemplateBuilder restTemplateBuilder) {
    this.logService = logService;
    this.restTemplate = restTemplateBuilder.build();
    this.chatAppPeerAddress =
            System.getenv("CHAT_APP_PEER_ADDRESS");
  }

  @Async
  public void forwardMessage(ChatRestMessage chatRestMessage) {
    String url = chatAppPeerAddress;
    RestMessageObject messagePostResponse;
    try {
      messagePostResponse = restTemplate
              .postForObject(url, chatRestMessage, ErrorRestMessage.class);

      logService.printLogIfNeeded("[AsyncBroadcastService]",
              "POST",
              "INFO",
              "sent request to "
                      + url
                      + ", message = " + chatRestMessage.toString()
                      + ", response = " + messagePostResponse.toString());

    } catch (RestClientException exception) {
      System.out.println(
              "RestTemplate exception caught: "
                      + exception.toString());
    }
  }
}
