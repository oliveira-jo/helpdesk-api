package com.oliveira.helpdesk.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.dto.UpdateUserDto;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.UserRole;
import com.oliveira.helpdesk.exception.AuthorizationException;
import com.oliveira.helpdesk.exception.BusinessException;
import com.oliveira.helpdesk.mapper.UserMapper;
import com.oliveira.helpdesk.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  private final UserMapper mapper;

  public User createUser(User newUser) {

    Optional<UserEntity> existentUser = userRepository.findByUsername(newUser.username());
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

    Optional<UserEntity> existentUser = userRepository.findByUsername(newUser.username());
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

  public User update(UUID id, UpdateUserDto data) {

    var entity = this.userRepository.findById(id)
        .orElseThrow(() -> new BusinessException("User not found in the system with this id"));

    if (!data.name().isEmpty())
      entity.setName(data.name());
    if (!data.username().isEmpty())
      entity.setUsername(data.username());
    if (!data.password().isEmpty())
      entity.setPassword(new BCryptPasswordEncoder().encode(data.password()));
    if (!data.email().isEmpty())
      entity.setEmail(data.email());
    if (data.active() != null)
      entity.setActive(data.active());

    this.userRepository.save(entity);

    return mapper.toDomain(entity);

  }

  public User findByUsername(String username) {
    UserEntity entity = userRepository.findByUsername(username).orElse(null);
    if (entity == null) {
      throw new AuthorizationException("User not found in the system with this name");
    }
    return mapper.toDomain(entity);
  }

  public User findById(UUID id) {
    UserEntity entity = userRepository.findById(id).orElse(null);
    if (entity == null) {
      throw new AuthorizationException("User not found in the system with this id");
    }
    return mapper.toDomain(entity);
  }

  public void deleteUser(UUID id) {
    UserEntity entity = userRepository.findById(id).orElse(null);
    if (entity == null) {
      throw new AuthorizationException("User not found in the system with this id");
    }
    this.userRepository.delete(entity);
  }

}
