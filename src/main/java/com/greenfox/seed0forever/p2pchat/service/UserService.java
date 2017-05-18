package com.greenfox.seed0forever.p2pchat.service;

import com.greenfox.seed0forever.p2pchat.model.User;
import com.greenfox.seed0forever.p2pchat.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void addUser(User user) {
    userRepository.save(user);
  }

  public List<User> listAllUsers() {
    return userRepository.findAll();
  }

  public boolean doesUserExist(String username) {
    return userRepository
            .findFirstByUsernameIgnoreCase(username)
            != null;
  }

}
