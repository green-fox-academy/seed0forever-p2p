package com.greenfox.seed0forever.p2pchat.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "chat_message")
public class Message {

  private static final long RANDOM_ID_LOWER_BOUND_INCLUSIVE = 1000000L;
  private static final long RANDOM_ID_UPPER_BOUND_EXCLUSIVE = 10000000L;

  @Id
  @Min(1000000L)
  @Max(9999999L)
  private long id;

  @NotNull
  @Size(min = 1)
  private String username;

  @NotNull
  @Size(min = 1)
  private String text;

  @Valid
  @NotNull
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

  @Override
  public String toString() {
    return "Message{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", text='" + text + '\'' +
            ", timestamp=" + timestamp +
            '}';
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

  public void generateNewTimestampNow() {
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }
}
