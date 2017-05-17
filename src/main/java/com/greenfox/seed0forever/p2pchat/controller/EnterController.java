package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.User;
import com.greenfox.seed0forever.p2pchat.service.LogService;
import com.greenfox.seed0forever.p2pchat.service.UserService;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enter")
public class EnterController {

  private static final String EMPTY_USERNAME_FIELD_ERROR = "The username field is empty";

  private UserService userService;
  private LogService logService;

  @Autowired
  public EnterController(UserService userService, LogService logService) {
    super();
    this.userService = userService;
    this.logService = logService;
  }

  @GetMapping("")
  public String enterUsername(Model model, User user) {
    logService.printLogIfNeeded("/enter", "GET", "INFO",
            new Timestamp(System.currentTimeMillis()), "");

    model.addAttribute("userToAdd", user);
    return "enter-username";
  }

  @PostMapping("")
  public String saveUserName(User postedUser) {
    logService.printLogIfNeeded("/enter", "POST", "INFO",
            new Timestamp(System.currentTimeMillis()),
            "received User fields: id="
                    + postedUser.getId()
                    + ", username="
                    + postedUser.getUsername());

    if (postedUser.getUsername() != null
            && userService.doesUserExist(postedUser.getUsername())) {
      return "redirect:/";

    } else {
      userService.addUser(postedUser);
    }
    return "redirect:/";
  }

}
