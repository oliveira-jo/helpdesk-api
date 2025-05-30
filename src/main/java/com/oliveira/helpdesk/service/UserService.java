package com.oliveira.helpdesk.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.dto.NumberUsersDto;
import com.oliveira.helpdesk.dto.UpdateUserDto;
import com.oliveira.helpdesk.dto.UpdateUserPasswordDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.UserRole;
import com.oliveira.helpdesk.exception.AuthorizationException;
import com.oliveira.helpdesk.exception.BusinessException;
import com.oliveira.helpdesk.mapper.UserMapper;
import com.oliveira.helpdesk.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final UserMapper mapper;

  public UserService(UserRepository userRepository, UserMapper mapper) {
    this.userRepository = userRepository;
    this.mapper = mapper;

  }

  public User createUser(User newUser) {

    Optional<UserEntity> existentUser = userRepository.findByUsername(newUser.getUsername());
    if (existentUser.isPresent()) {
      throw new BusinessException("This username is already in use in the system!");
    }

    UserEntity entity = mapper.toEntity(newUser);
    entity.setCreatedAt(new Date());
    entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
    entity.setActive(true);

    entity.setRole(UserRole.CUSTOMER);
    entity = userRepository.save(entity);

    return mapper.toDomain(entity);

  }

  public User createSupportUser(User newUser) {

    Optional<UserEntity> existentUser = userRepository.findByUsername(newUser.getUsername());
    if (existentUser.isPresent()) {
      throw new BusinessException("This username is already in use in the system!");
    }

    UserEntity entity = mapper.toEntity(newUser);
    entity.setCreatedAt(new Date());
    entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
    entity.setActive(true);

    entity.setRole(UserRole.SUPPORT_ATTENDANT);
    entity = userRepository.save(entity);

    return mapper.toDomain(entity);

  }

  public User update(UUID id, UpdateUserDto data, Authentication authentication) {

    Optional<UserEntity> userAuthenticated = userRepository.findByUsername(authentication.getName());
    if (!userAuthenticated.isPresent()) {
      throw new BusinessException("User not authenticated in the system with provide name");
    }

    // UPDATE entity
    var entity = this.userRepository.findById(id)
        .orElseThrow(() -> new BusinessException("User not found in the system with this id"));

    if (userAuthenticated.get().getRole().equals(UserRole.ADMIN)
        || userAuthenticated.get().equals(entity)) {

      if (!data.name().isEmpty())
        entity.setName(data.name());
      if (!data.username().isEmpty())
        entity.setUsername(data.username());
      if (!data.email().isEmpty())
        entity.setEmail(data.email());
      if (data.active() != null)
        entity.setActive(data.active());

      this.userRepository.save(entity);

      return mapper.toDomain(entity);

    } else {
      throw new BusinessException("Unauthorized to update ticket: " + id);

    }
  }

  public User updatePassword(UUID id, UpdateUserPasswordDto data, Authentication authentication) {

    // IS AUTHENTICATED
    UserEntity userAuthenticated = userRepository.findByUsername(authentication.getName()).orElse(null);
    if (userAuthenticated == null) {
      throw new BusinessException("User NOT AUTHENTICATED in the system");
    }
    // EXIST USER WITH THIS ID
    var entity = this.userRepository.findById(id)
        .orElseThrow(() -> new BusinessException("User NOT FOUND in the system with this id"));

    if (userAuthenticated.getRole().equals(UserRole.ADMIN)
        || userAuthenticated.equals(entity)) {

      if (data.newPassword().isEmpty()) {
        throw new BusinessException("Password cannot be empty!");

        // MATCH NEW PASSWORD WITH CURRENT PASSWORD
      } else if (new BCryptPasswordEncoder().matches(data.currentPassword(), entity.getPassword())) {

        entity.setPassword(new BCryptPasswordEncoder().encode(data.newPassword()));
        this.userRepository.save(entity);
        return mapper.toDomain(entity);

      } else {
        throw new BusinessException("Actual password cannot match!");

      }

    } else {
      throw new BusinessException("Unauthorized to update User Password: " + id);

    }
  }

  public User findByUsername(String username) {

    Optional<UserEntity> entity = userRepository.findByUsername(username);
    if (!entity.isPresent()) {
      throw new AuthorizationException("User not found in the system with this name");
    }

    return mapper.toDomain(entity.get());

  }

  public User findById(UUID id) {

    Optional<UserEntity> entity = userRepository.findById(id);
    if (!entity.isPresent()) {
      throw new AuthorizationException("User not found in the system with this id");
    }

    return mapper.toDomain(entity.get());

  }

  public List<UserDto> findAllUsers(Authentication authentication) {

    Optional<UserEntity> userAuthenticated = userRepository.findByUsername(authentication.getName());
    if (!userAuthenticated.isPresent()) {
      throw new BusinessException("User not authenticated *************************************");
    }

    if (userAuthenticated.get().getRole().equals(UserRole.ADMIN)) {
      return mapper.toDto(this.userRepository.findAll());

    } else {
      throw new BusinessException("Unauthorized");

    }
  }

  public void delete(UUID id, Authentication authentication) {

    Optional<UserEntity> userAuthenticated = userRepository.findByUsername(authentication.getName());
    if (!userAuthenticated.isPresent()) {
      throw new BusinessException("User not authenticated");
    }

    Optional<UserEntity> deleteUser = userRepository.findById(id);
    if (!deleteUser.isPresent()) {
      throw new BusinessException("User not found for provide id");
    }

    if (deleteUser.get().getRole().equals(UserRole.ADMIN)) {
      throw new BusinessException("Unauthorized to delete ADMIN");
    }

    if (deleteUser.get().getRole().equals(UserRole.SUPPORT_ATTENDANT)) {
      throw new BusinessException("Unauthorized to delete SUPPORT_ATTENDANT");
    }

    if (userAuthenticated.get().getRole().equals(UserRole.ADMIN)
        || userAuthenticated.equals(deleteUser)) {

      this.userRepository.delete(deleteUser.get());

    } else {
      throw new BusinessException("Unauthorized to delete user: " + deleteUser.get().getUsername());

    }

  }

  public NumberUsersDto numberOfUsers(Authentication authentication) {

    Optional<UserEntity> user = userRepository.findByUsername(authentication.getName());
    if (!user.isPresent()) {
      throw new BusinessException("User not found or not authenticated");
    }

    var userAdmin = 0;
    var userAttendent = 0;
    var defaultUser = 0;

    if (user.get().getRole().equals(UserRole.ADMIN)) {

      var newUsers = this.findAllUsers(authentication);

      for (UserDto data : newUsers) {
        if (data.role().equals(UserRole.ADMIN))
          userAdmin++;
        if (data.role().equals(UserRole.SUPPORT_ATTENDANT))
          userAttendent++;
        if (data.role().equals(UserRole.CUSTOMER))
          defaultUser++;
      }

    }
    return new NumberUsersDto(userAdmin, userAttendent, defaultUser);
  }

}
