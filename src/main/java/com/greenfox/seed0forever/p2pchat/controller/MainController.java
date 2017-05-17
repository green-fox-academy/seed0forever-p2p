package com.greenfox.seed0forever.p2pchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

  @RequestMapping("")
  public String showMainPage(Model model) {
    model.addAttribute("developedBy", "seed0forever");
    return "index";
  }

}
