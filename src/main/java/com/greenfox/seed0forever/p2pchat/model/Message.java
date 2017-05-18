package com.greenfox.seed0forever.p2pchat.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chat_message")
public class Message {

  private static final long RANDOM_ID_LOWER_BOUND_INCLUSIVE = 1000000L;
  private static final long RANDOM_ID_UPPER_BOUND_EXCLUSIVE = 10000000L;

  @Id
  private long id;

  private String username;
  private String text;
  private Timestamp timestamp;

  public Message() {
    this.id = generateRandomId();
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }
  public Message(String username, String text) {
    this();

    this.username = username;
    this.text = text;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  private long generateRandomId() {
    long range = RANDOM_ID_UPPER_BOUND_EXCLUSIVE
            - RANDOM_ID_LOWER_BOUND_INCLUSIVE;

    long randomId = RANDOM_ID_LOWER_BOUND_INCLUSIVE + (long) (Math.random() * range);
    return randomId;
  }

  public void generateAndSetRandomId() {
    this.id = generateRandomId();
  }

  public void createAndSetNewTimestamp() {
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }
}
