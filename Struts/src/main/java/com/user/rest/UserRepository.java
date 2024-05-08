package com.user.rest;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
  private static Map<String, User> map = new HashMap<String, User>();

  public UserRepository() { }

  public User getUserById(String id) {
    return map.get(id);
  }

  public Map<String, User> findAllUser() {
    return map;
  }

  public void addUser(User user) {
    map.put(String.valueOf(user.getId()), user);
  }

  public void updateUser(User e) {
    map.put(String.valueOf(e.getId()), e);
    System.out.println(map);
  }

  public void deleteUser(String id) {
    map.remove(id);
  }
}
