package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.LogEntry;
import java.sql.Timestamp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

  // GitHub username of app developer
  // read from environment variable CHAT_APP_UNIQUE_ID
  private String chatAppUniqueId;
  // Heroku URL address of an other chat application
  // read from environment variable CHAT_APP_PEER_ADDRESSS
  private String chatAppPeerAddress;

  public MainController() {
    this.chatAppUniqueId = System.getenv("CHAT_APP_UNIQUE_ID");
    this.chatAppPeerAddress = System.getenv("CHAT_APP_PEER_ADDRESSS");
  }

  @RequestMapping("")
  public String showMainPage(Model model) {
    String currentLogLevel = System.getenv("CHAT_APP_LOGLEVEL");

    if (currentLogLevel != null && currentLogLevel.equals("INFO")) {

      System.out.println(
              new LogEntry("/", "GET", "INFO", new Timestamp(System.currentTimeMillis()),
                      "/"));
    } else {
      System.out.println("No INFO in loglevel variable");
    }

    model.addAttribute("developedBy", "seed0forever");
    model.addAttribute("chatAppUniqueId", chatAppUniqueId);
    model.addAttribute("chatAppPeerAddress", chatAppPeerAddress);
    return "index";
  }

}
