package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.ChatClient;
import com.greenfox.seed0forever.p2pchat.model.Message;
import com.greenfox.seed0forever.p2pchat.model.User;
import com.greenfox.seed0forever.p2pchat.model.rest.ChatRestMessage;
import com.greenfox.seed0forever.p2pchat.model.rest.OkRestMessage;
import com.greenfox.seed0forever.p2pchat.service.LogService;
import com.greenfox.seed0forever.p2pchat.service.MessageService;
import com.greenfox.seed0forever.p2pchat.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

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
  private MessageService messageService;
  private RestTemplate restTemplate;

  @Autowired
  public MainController(LogService logService, UserService userService,
          MessageService messageService) {
    super();
    this.logService = logService;
    this.userService = userService;
    this.messageService = messageService;
    this.restTemplate = new RestTemplate();

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
    messageService.save(message);

    ChatRestMessage sentMessage = new ChatRestMessage(message,
            new ChatClient(chatAppUniqueId));

    String url = chatAppPeerAddress;
    OkRestMessage testPostResponse = restTemplate
            .postForObject(url, sentMessage, OkRestMessage.class);

    logService.printLogIfNeeded(
            "/save-message",
            "POST response",
            "INFO",
            "sent POST request to "
                    + url
                    + ", message = " + sentMessage.toString()
                    + ", response = " + testPostResponse.toString());

    return "redirect:/";
  }

  private void printList(List<User> users) {
    for (User userElement : users) {
      System.out.println(userElement.toString());
    }
  }
}
