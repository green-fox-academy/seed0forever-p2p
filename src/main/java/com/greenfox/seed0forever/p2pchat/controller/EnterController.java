package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.User;
import com.greenfox.seed0forever.p2pchat.repository.UserRepository;
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

  @Autowired
  UserRepository enterControllerUserRepository;

  @GetMapping("")
  public String enterUsername(Model model) {
    User currentUser = enterControllerUserRepository.findOne(1L);

    boolean isUserExist = (currentUser != null);
    boolean isUsernameSet = isUserExist
            ? (currentUser.getUsername() != null)
            : false;

    if (isUserExist) {
      model.addAttribute("currentUser", currentUser);
    } else {
      model.addAttribute("currentUser", new User());
    }

    return "enter-username";
  }

  @PostMapping("")
  public String saveUserName(User postedUser) {
    enterControllerUserRepository.save(postedUser);
    return "redirect:/";
  }

}
