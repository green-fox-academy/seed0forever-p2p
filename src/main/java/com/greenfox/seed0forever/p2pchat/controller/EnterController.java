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
  public String enterUsername(Model model, User userToAdd) {
    logService.printLogIfNeeded(
            "/enter",
            "GET",
            "INFO",
            "/");

    // redirect to main page if a user with a non-empty name already exists
    if (userService.doesUserExist(1L)
            && !userService.findUser(1L).hasEmptyName()) {
      return "redirect:/";
    }

    userToAdd.setId(1L); // we want to have just one User in the database
    model.addAttribute("userToAdd", userToAdd);
    return "enter-username";
  }

  @PostMapping("")
  public String saveUserName(User userToAdd) {
    logService.printLogIfNeeded(
            "/enter",
            "POST",
            "INFO",
            "received User fields: id="
                    + userToAdd.getId()
                    + ", username="
                    + userToAdd.getUsername());

    if (userToAdd.hasEmptyName()) {
      return "redirect:/enter";

    } else {
      userToAdd.setId(1L); // failsafe setting: should already have id=1L
      userService.addUser(userToAdd);
    }
    return "redirect:/";
  }
}
