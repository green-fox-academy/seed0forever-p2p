package com.greenfox.seed0forever.p2pchat.service;

import com.greenfox.seed0forever.p2pchat.model.LogEntry;
import java.sql.Timestamp;
import org.springframework.stereotype.Service;

@Service
public class LogService {

  public void printLogIfNeeded(
          String path,
          String method,
          String eventLogLevel,
          Timestamp timestamp,
          String requestData) {

    String systemLogLevel = System.getenv("CHAT_APP_LOGLEVEL");

    if (systemLogLevel != null && systemLogLevel.equalsIgnoreCase("INFO")) {

      System.out.println(
              new LogEntry(
                      path,
                      method,
                      eventLogLevel,
                      timestamp,
                      requestData));

    } else if (eventLogLevel.equalsIgnoreCase("ERROR")) {

      System.out.println(
              new LogEntry(
                      path,
                      method,
                      eventLogLevel,
                      timestamp,
                      requestData));

    } else {
      System.out.printf("[debug]: LogService was called with a %s level event%n", eventLogLevel);
      System.out.printf("[debug]: but system environment CHAT_APP_LOGLEVEL is %s%n", systemLogLevel);
    }
  }
}
