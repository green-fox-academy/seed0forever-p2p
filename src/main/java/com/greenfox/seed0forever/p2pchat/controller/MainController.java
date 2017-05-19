package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.Message;
import com.greenfox.seed0forever.p2pchat.model.User;
import com.greenfox.seed0forever.p2pchat.service.BroadcastService;
import com.greenfox.seed0forever.p2pchat.service.LogService;
import com.greenfox.seed0forever.p2pchat.service.MessageService;
import com.greenfox.seed0forever.p2pchat.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
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

  private BroadcastService broadcastService;
  private LogService logService;
  private UserService userService;
  private MessageService messageService;

  @Autowired
  public MainController(BroadcastService broadcastService, LogService logService,
          UserService userService,
          MessageService messageService) {
    super();
    this.broadcastService = broadcastService;
    this.logService = logService;
    this.userService = userService;
    this.messageService = messageService;

    this.chatAppUniqueId = System.getenv("CHAT_APP_UNIQUE_ID");
    this.chatAppPeerAddress = System.getenv("CHAT_APP_PEER_ADDRESS");
  }

  @RequestMapping("")
  public String showMainPage(Model model, Message messageToAdd) {
    logService.printLogIfNeeded(
            "/",
            "GET",
            "INFO",
            "/");

    // redirect to '/enter' if there is no user yet
    // or if current user has an empty name
    if (!userService.doesUserExist(1L)
            || userService.findUser(1L).hasEmptyName()) {
      return "redirect:/enter";

    } else {
      User currentUser = userService.findUser(1L);

      model.addAttribute("currentUser", currentUser);
      model.addAttribute("messageToAdd", messageToAdd);
      model.addAttribute("messageList", messageService.listAll());

      model.addAttribute("developedBy", "seed0forever");
      model.addAttribute("chatAppUniqueId", chatAppUniqueId);
      model.addAttribute("chatAppPeerAddress", chatAppPeerAddress);

      printList(userService.listAllUsers()); // TODO: remove (it's just for debug purposes)

      return "index";
    }
  }

  @PostMapping("/update")
  public String changeCurrentUser(User currentUser) {
    logService.printLogIfNeeded(
            "/update",
            "POST",
            "INFO",
            "received User fields: id="
                    + currentUser.getId()
                    + ", username="
                    + currentUser.getUsername());

    currentUser.setId(1L); // failsafe setting: should already have id=1L
    userService.addUser(currentUser);

    return "redirect:/";
  }

  @PostMapping("/save-message")
  public String saveMessage(Message message) {
    message.setUsername(userService.findUser(1L).getUsername());
    message.createAndSetNewTimestamp();

    messageService.saveWithoutIdCollision(message);

    broadcastService.forwardMessage(message);

    return "redirect:/";
  }

  private void printList(List<User> users) {
    for (User userElement : users) {
      System.out.println(userElement.toString());
    }
  }
}
