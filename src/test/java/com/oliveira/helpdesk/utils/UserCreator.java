package com.oliveira.helpdesk.utils;

import java.util.UUID;

import com.oliveira.helpdesk.entity.UserEntity;

public class UserCreator {

  public static UserEntity createUser() {

    String username = "testUser";
    UserEntity userEntity = new UserEntity();
    userEntity.setId(UUID.randomUUID());
    userEntity.setUsername(username);
    userEntity.setActive(true);

    return userEntity;

  }
}
