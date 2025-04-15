package com.oliveira.helpdesk.utils;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oliveira.helpdesk.entity.UserEntity;

public class UserCreator {

  private static final UUID USER_ID = UUID.randomUUID();
  private static final String USER_PASSWORD = new BCryptPasswordEncoder().encode("testPassword");

  public static UserEntity createUserToBeSaved() {

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("testUsername");
    userEntity.setName("testName");
    userEntity.setActive(true);
    userEntity.setEmail("testUser@email.com");
    userEntity.setPassword(USER_PASSWORD);

    userEntity.setCreatedAt(new Date());
    return userEntity;

  }

  public static UserEntity createValidUser() {

    UserEntity userEntity = new UserEntity();
    userEntity.setId(USER_ID);
    userEntity.setUsername("testUsername");
    userEntity.setName("testName");
    userEntity.setActive(true);
    userEntity.setEmail("testUser@email.com");
    userEntity.setPassword(USER_PASSWORD);
    return userEntity;

  }

  public static UserEntity createValidUpdateUser() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(USER_ID);
    userEntity.setUsername("testUsername_updated");
    userEntity.setName("testName_Updated");
    userEntity.setActive(true);
    userEntity.setEmail("testUser@email.com");
    userEntity.setPassword(USER_PASSWORD);
    return userEntity;
  }
}
