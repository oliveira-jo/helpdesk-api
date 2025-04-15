package com.oliveira.helpdesk.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.UserRole;
import com.oliveira.helpdesk.utils.UserCreator;

@DataJpaTest
@DisplayName("User Repository Test")
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("save persists user when successful")
  void save_PersistUser_WhenSucessfull() {

    UserEntity userToBeSaved = UserCreator.createUserToBeSaved();
    UserEntity userSaved = this.userRepository.save(userToBeSaved);

    Assertions.assertThat(userSaved).isNotNull();
    Assertions.assertThat(userSaved.getId()).isNotNull();
    Assertions.assertThat(userSaved.getName()).isEqualTo(userToBeSaved.getName());
    Assertions.assertThat(userSaved.isActive()).isEqualTo(true);
    Assertions.assertThat(userSaved.getEmail()).isEqualTo(userToBeSaved.getEmail());
    Assertions.assertThat(userSaved.getPassword()).isEqualTo(userToBeSaved.getPassword());

  }

  @Test
  @DisplayName("save updates user when successful")
  void save_UpdateUser_WhenSucessfull() {

    UserEntity userToBeSaved = UserCreator.createUserToBeSaved();
    UserEntity userSaved = this.userRepository.save(userToBeSaved);

    userSaved.setName("Updated Name");
    UserEntity userUpdated = this.userRepository.save(userSaved);

    Assertions.assertThat(userUpdated).isNotNull();

  }

  @Test
  @DisplayName("delete removes user when successful")
  void findByUsername_ReturnsEmptyOptional_WhenUsernameDoesNotExist() {

    Optional<UserEntity> userFound = this.userRepository.findByUsername("nonexistentUsername");

    Assertions.assertThat(userFound).isEmpty();
    Assertions.assertThat(userFound).isNotPresent();
  }

  @Test
  @DisplayName("findByUsername returns user when successful")
  void findByRole_ReturnsUser_WhenSuccessful() {

    UserEntity userToBeSaved = UserCreator.createUserToBeSaved();
    userToBeSaved.setRole(UserRole.ADMIN);

    this.userRepository.save(userToBeSaved);

    Optional<UserEntity> userFound = this.userRepository.findByRole(UserRole.ADMIN);

    Assertions.assertThat(userFound).isPresent();
    Assertions.assertThat(userFound.get().getRole()).isEqualTo(UserRole.ADMIN);
  }

  @Test
  @DisplayName("findByRole returns empty optional when role does not exist")
  void findByRole_ReturnsEmptyOptional_WhenRoleDoesNotExist() {

    Optional<UserEntity> userFound = this.userRepository.findByRole(UserRole.SUPPORT_ATTENDANT);

    Assertions.assertThat(userFound).isEmpty();
    Assertions.assertThat(userFound).isNotPresent();
  }

}
