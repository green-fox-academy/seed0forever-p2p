package com.greenfox.seed0forever.p2pchat.service;

import com.greenfox.seed0forever.p2pchat.model.Message;
import com.greenfox.seed0forever.p2pchat.repository.MessageRepository;
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

  public void save(Message message) {
    while (messageRepository.exists(message.getId())) {
      message.generateAndSetRandomId();
    }
    messageRepository.save(message);
  }

  public List<Message> listAll() {
    return messageRepository.findAll();
  }
}
