package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.service.LogService;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
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

  private LogService logService;

  @Autowired
  public MainController(LogService logService) {
    super();
    this.logService = logService;

    this.chatAppUniqueId = System.getenv("CHAT_APP_UNIQUE_ID");
    this.chatAppPeerAddress = System.getenv("CHAT_APP_PEER_ADDRESSS");
  }

  @RequestMapping("")
  public String showMainPage(Model model) {
    logService.printLogIfNeeded("/", "GET", "INFO",
            new Timestamp(System.currentTimeMillis()), "/");

    model.addAttribute("developedBy", "seed0forever");
    model.addAttribute("chatAppUniqueId", chatAppUniqueId);
    model.addAttribute("chatAppPeerAddress", chatAppPeerAddress);
    return "index";
  }
}
