package com.greenfox.seed0forever.p2pchat.controller;

import com.greenfox.seed0forever.p2pchat.model.LogEntry;
import java.sql.Timestamp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

  @RequestMapping("")
  public String showMainPage(Model model) {
    String currentLogLevel = System.getenv("CHAT_APP_LOGLEVEL");

    if (currentLogLevel.equals("INFO")) {

      System.out.println(
              new LogEntry("/", "GET", "INFO", new Timestamp(System.currentTimeMillis()),
                      "/"));
    }

    model.addAttribute("developedBy", "seed0forever");
    return "index";
  }

}
