package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.User;
import com.greenfox.seed0forever.p2pchat.service.LogService;
import com.greenfox.seed0forever.p2pchat.service.UserService;
import java.sql.Timestamp;
import java.util.List;
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
  private UserService userService;

  @Autowired
  public MainController(LogService logService, UserService userService) {
    super();
    this.logService = logService;
    this.userService = userService;

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

    printList(userService.listAllUsers());
    return "index";
  }

  private void printList(List<User> users) {
    for (User userElement : users) {
      System.out.println(userElement.toString());
    }
  }
}
