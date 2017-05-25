package com.greenfox.seed0forever.p2pchat.repository;

import com.greenfox.seed0forever.p2pchat.model.Message;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

  @Override
  Message save(Message message);

  @Override
  Message findOne(Long id);

  @Override
  List<Message> findAll();

  List<Message> findAllByUsernameAndTimestamp(String username, Timestamp timestamp);

  List<Message> findAllByUsernameAndText(String username, String text);
}
