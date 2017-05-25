package com.greenfox.seed0forever.p2pchat.service;

import com.greenfox.seed0forever.p2pchat.model.Message;
import com.greenfox.seed0forever.p2pchat.repository.MessageRepository;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private MessageRepository messageRepository;

  @Autowired
  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public void saveWithoutIdCollision(Message message) {
    Message copyOfMessage = copyMessage(message);

    while (messageRepository.exists(copyOfMessage.getId())) {
      copyOfMessage.generateAndSetRandomId();
    }
    messageRepository.save(copyOfMessage);
  }

  private Message copyMessage(Message message) {
    long receivedId = message.getId();
    String receivedUsername = message.getUsername();
    String receivedText = message.getText();
    Timestamp receivedTimestamp = new Timestamp(message.getTimestamp().getTime());

    Message copyOfMessage = new Message();
    copyOfMessage.setId(receivedId);
    copyOfMessage.setUsername(receivedUsername);
    copyOfMessage.setText(receivedText);
    copyOfMessage.setTimestamp(receivedTimestamp);

    return copyOfMessage;
  }

  public List<Message> listAll() {
    return messageRepository.findAll();
  }

  public boolean existsByUserAndTime(String username, Timestamp timestamp) {
    List<Message> filteredMessages = messageRepository
            .findAllByUsernameAndTimestamp(username, timestamp);
    boolean messageExists =
            filteredMessages != null
                    && filteredMessages.size() >= 1;
    return messageExists;
  }

  public boolean existByUserAndText(String username, String text) {
    List<Message> filteredMessages = messageRepository
            .findAllByUsernameAndText(username, text);
    boolean messageExists =
            filteredMessages != null
                    && filteredMessages.size() >= 1;
    return messageExists;
  }
}
