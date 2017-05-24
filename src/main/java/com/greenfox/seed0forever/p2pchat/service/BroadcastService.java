package com.greenfox.seed0forever.p2pchat.service;

import com.greenfox.seed0forever.p2pchat.model.ChatClient;
import com.greenfox.seed0forever.p2pchat.model.Message;
import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class BroadcastService {

  // GitHub username of app developer
  // read from environment variable CHAT_APP_UNIQUE_ID
  private String chatAppUniqueId;
  // Heroku URL address of an other chat application
  // read from environment variable CHAT_APP_PEER_ADDRESSS
  private String chatAppPeerAddress;

  private final ChatClient chatClient;
  private final RestTemplate restTemplate;
  private final LogService logService;

  @Autowired
  public BroadcastService(
          LogService logService,
          RestTemplateBuilder restTemplateBuilder) {

    this.chatAppUniqueId =
            System.getenv("CHAT_APP_UNIQUE_ID");

    this.chatAppPeerAddress =
            System.getenv("CHAT_APP_PEER_ADDRESS");

    this.logService = logService;
    this.chatClient = new ChatClient(chatAppUniqueId);
    this.restTemplate = restTemplateBuilder.build();
  }

  public void forwardMessage(Message message) {

    ChatRestMessage chatRestMessage =
            new ChatRestMessage(
                    message,
                    chatClient);

    forwardMessage(chatRestMessage);
  }

  public void forwardMessage(ChatRestMessage chatRestMessage) {
    String url = chatAppPeerAddress;

    try {
      ResponseEntity<String> responseOfRestTemplate = restTemplate
              .postForEntity(
                      url, chatRestMessage, String.class);
    } catch (RestClientException e) {
      System.out.println("RestTemplate exception caught");
    }

    logService.printLogIfNeeded(
            "/save-message",
            "POST response",
            "INFO",
            "sent POST request to "
                    + url
                    + ", message = " + chatRestMessage.toString()
//                    + ", response = " + responseOfRestTemplate.toString()
    );
  }
}
