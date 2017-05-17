package com.greenfox.seed0forever.p2pchat.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LogEntry {

  // format:  date time logLevel "Request" path method requestData
  // example: 2017-05-16 21:47:19.040 INFO Request /message POST text=apple
  private static final String TO_STRING_FORMAT = "%s %s Request %s %s %s";
  private static final String SIMPLE_DATE_FORMAT_FOR_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

  private String path;
  private String method;
  private String logLevel;
  private Timestamp timestamp;
  private String requestData;

  public LogEntry(String path, String method, String logLevel, Timestamp timestamp,
          String requestData) {
    this.path = path;
    this.method = method;
    this.logLevel = logLevel;
    this.timestamp = timestamp;
    this.requestData = requestData;
  }

  @Override
  public String toString() {
    String formattedTimeStamp = new SimpleDateFormat(
            SIMPLE_DATE_FORMAT_FOR_TIMESTAMP)
            .format(timestamp);

    return String.format(TO_STRING_FORMAT,
            formattedTimeStamp,
            logLevel, path, method, requestData);
  }
}
