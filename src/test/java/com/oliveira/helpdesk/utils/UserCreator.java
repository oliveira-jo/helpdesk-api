package com.oliveira.helpdesk.utils;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.dto.CreateUserDto;
import com.oliveira.helpdesk.dto.UpdateUserDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.UserRole;

public class UserCreator {

  private static final String USER_USERNAME = "testUsername";
  private static final String USER_NAME = "testName";
  private static final String USER_EMAIL = "testUser@email.com";
  private static final Date USER_CREATION_DATE = new Date();

  private static final UUID USER_ID = UUID.randomUUID();
  private static final String USER_PASSWORD = new BCryptPasswordEncoder().encode("testPassword");

  public static UserEntity createUserToBeSaved() {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(USER_USERNAME);
    userEntity.setName(USER_NAME);
    userEntity.setActive(true);
    userEntity.setEmail(USER_EMAIL);
    userEntity.setPassword(USER_PASSWORD);

    userEntity.setCreatedAt(USER_CREATION_DATE);
    return userEntity;

  }

  public static User createUserDomainToBeSaved() {
    User userEntity = new User();
    userEntity.setUsername(USER_USERNAME);
    userEntity.setName(USER_NAME);
    userEntity.setActive(true);
    userEntity.setEmail(USER_EMAIL);
    userEntity.setPassword(USER_PASSWORD);

    userEntity.setCreatedAt(new Date());
    return userEntity;

  }

  public static User createValidUserDomain() {
    User userEntity = new User();
    userEntity.setId(USER_ID);
    userEntity.setUsername(USER_USERNAME);
    userEntity.setName(USER_NAME);
    userEntity.setActive(true);
    userEntity.setEmail(USER_EMAIL);
    userEntity.setPassword(USER_PASSWORD);

    userEntity.setCreatedAt(new Date());
    return userEntity;

  }

  public static CreateUserDto createUserRequestDto() {
    return new CreateUserDto(
        USER_USERNAME,
        USER_PASSWORD,
        USER_NAME,
        USER_EMAIL);

  }

  public static UserDto createUserResponseDto() {
    return new UserDto(
        USER_ID,
        USER_USERNAME,
        USER_NAME,
        USER_EMAIL,
        true,
        UserRole.ADMIN,
        USER_CREATION_DATE);

  }

  public static UserEntity createValidUser() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(USER_ID);
    userEntity.setUsername(USER_USERNAME);
    userEntity.setName(USER_NAME);
    userEntity.setRole(UserRole.ADMIN);
    userEntity.setActive(true);
    userEntity.setEmail(USER_EMAIL);
    userEntity.setPassword(USER_PASSWORD);
    return userEntity;

  }

  public static User createValidUpdateUser() {
    User userEntity = new User();
    userEntity.setId(USER_ID);
    userEntity.setUsername("testUsername_updated");
    userEntity.setName("testName_Updated");
    userEntity.setActive(true);
    userEntity.setEmail(USER_EMAIL);
    userEntity.setPassword(USER_PASSWORD);
    return userEntity;

  }

  public static UserEntity createUpdateUser() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(USER_ID);
    userEntity.setUsername("testUsername_updated");
    userEntity.setName("testName_Updated");
    userEntity.setRole(UserRole.ADMIN);
    userEntity.setActive(true);
    userEntity.setEmail(USER_EMAIL);
    userEntity.setPassword(USER_PASSWORD);
    return userEntity;

  }

  public static UpdateUserDto createValidUpdateUserDto() {
    return new UpdateUserDto("testUsername_updated", USER_PASSWORD, "testName_Updated", USER_EMAIL, true);

  }

  public static UserDto createUpdateUserDto() {
    return new UserDto(USER_ID, "testUsername_updated", "testName_Updated", USER_EMAIL, true, null, USER_CREATION_DATE);

  }
}
