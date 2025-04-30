package com.oliveira.helpdesk.controller;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oliveira.helpdesk.dto.CreateUserDto;
import com.oliveira.helpdesk.dto.NumberUsersDto;
import com.oliveira.helpdesk.dto.UpdateUserDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.mapper.UserMapper;
import com.oliveira.helpdesk.service.UserService;
import com.oliveira.helpdesk.utils.UserCreator;
import com.oliveira.helpdesk.domain.User;

@DisplayName("Unit Test for User Controller")
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserService userServiceMock;

  @Mock
  private UserMapper userMapperMock;

  @BeforeEach
  void setUp() {
    // CREATE
    BDDMockito.when(userServiceMock.createUser(ArgumentMatchers.any(User.class)))
        .thenReturn(UserCreator.createUserDomainToBeSaved());
    BDDMockito.when(userMapperMock.toDomain(ArgumentMatchers.any(CreateUserDto.class)))
        .thenReturn(UserCreator.createUserDomainToBeSaved());
    BDDMockito.when(userMapperMock.toDto(ArgumentMatchers.any(User.class)))
        .thenReturn(UserCreator.createUserResponseDto());
    // UPDATE
    BDDMockito
        .when(userServiceMock.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UpdateUserDto.class),
            ArgumentMatchers.any(Authentication.class)))
        .thenReturn(UserCreator.createValidUpdateUser());
    // FIND_BY_ID
    BDDMockito.when(userServiceMock.findById(ArgumentMatchers.any(UUID.class)))
        .thenReturn(UserCreator.createValidUserDomain());
    // FIND_ALL_USERS
    BDDMockito.when(userServiceMock.findAllUsers(ArgumentMatchers.any(Authentication.class)))
        .thenReturn(List.of(UserCreator.createUserResponseDto()));
    // FIND_BY_USERNAME
    BDDMockito.when(userServiceMock.findByUsername(ArgumentMatchers.any(String.class)))
        .thenReturn(UserCreator.createValidUserDomain());
    // DELETE
    BDDMockito.willDoNothing().given(userServiceMock)
        .delete(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(Authentication.class));
    // USERS NUMBER
    BDDMockito.when(this.userServiceMock.numberOfUsers(ArgumentMatchers.any(Authentication.class)))
        .thenReturn(new NumberUsersDto(1, 1, 1));

  }

  @Test
  @DisplayName("create user and returns UserDTO WhenSuccessfull")
  void create_ReturnsUserDTO__WhenSuccessfull() {

    ResponseEntity<UserDto> response = this.userController.create(UserCreator.createUserRequestDto());

    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().active()).isEqualTo(true);
    Assertions.assertThat(response.getBody().username()).isEqualTo(UserCreator.createValidUser().getUsername());
    Assertions.assertThat(response.getBody().name()).isEqualTo(UserCreator.createValidUser().getName());
    Assertions.assertThat(response.getBody().email()).isEqualTo(UserCreator.createValidUser().getEmail());
    Assertions.assertThat(response.getBody().id()).isEqualTo(UserCreator.createValidUser().getId());

  }

  @Test
  @DisplayName("update user and returns UserDTO WhenSuccessfull")
  void update_ReturnsUserDTO_WhenSuccessfull() {

    Authentication authentication = BDDMockito.mock(Authentication.class);

    BDDMockito.when(this.userMapperMock.toDto(ArgumentMatchers.any(User.class)))
        .thenReturn(UserCreator.createUpdateUserDto());

    UserDto response = this.userController.update(
        UserCreator.createValidUpdateUser().getId(),
        UserCreator.createValidUpdateUserDto(),
        authentication).getBody();

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.id()).isEqualTo(UserCreator.createValidUpdateUser().getId());
    Assertions.assertThat(response.username()).isEqualTo(UserCreator.createValidUpdateUser().getUsername());
    Assertions.assertThat(response.name()).isEqualTo(UserCreator.createValidUpdateUser().getName());
    Assertions.assertThat(response.email()).isEqualTo(UserCreator.createValidUpdateUser().getEmail());
    Assertions.assertThat(response.active()).isEqualTo(true);

  }

  @Test
  @DisplayName("findById a user by a provide id and returns a User WhenSuccessfull")
  void findById_ReturnsUser_WhenSuccessfull() {

    UUID expectedId = UserCreator.createValidUser().getId();
    ResponseEntity<UserDto> response = this.userController.findById(UserCreator.createValidUser().getId());

    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().id()).isEqualTo(expectedId);

  }

  @Test
  @DisplayName("findAllUsers returns a list of Users WhenSuccessfull for admin")
  void findAllUsers_ReturnsListOfUsers_WhenSuccessfull() {

    UUID expectedId = UserCreator.createValidUser().getId();
    Authentication authentication = BDDMockito.mock(Authentication.class);
    ResponseEntity<List<UserDto>> response = this.userController.findAllUsers(authentication);

    Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty().hasSize(1);
    Assertions.assertThat(response.getBody().get(0).id()).isEqualTo(expectedId);

  }

  @Test
  @DisplayName("findByUsername a user by a provide username and returns a User WhenSuccessfull")
  void findByUsername_ReturnsUser_WhenSuccessfull() {

    String expectedUsername = UserCreator.createValidUser().getUsername();
    ResponseEntity<UserDto> response = this.userController.findByUsername(UserCreator.createValidUser().getUsername());

    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().username()).isEqualTo(expectedUsername);

  }

  @Test
  @DisplayName("delete returns void if user was deleted WhenSuccessfull")
  void delete_ReturnsVoid_WhenSuccessfull() {

    Assertions
        .assertThatCode(
            () -> userController.delete(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(Authentication.class)))
        .doesNotThrowAnyException();

    ResponseEntity<Void> response = this.userController.delete(ArgumentMatchers.any(UUID.class),
        ArgumentMatchers.any(Authentication.class));

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

  }

  @Test
  @DisplayName("numberOfUsers returns NumberUsersDto that have the quantiti of Users sabed in the system, retuns WhenSuccessfull")
  void numberOfUsers_ReturnsNumverUsersDTO_WhenSuccessfull() {

    Authentication authentication = BDDMockito.mock(Authentication.class);

    NumberUsersDto response = this.userController.numberOfUsers(authentication).getBody();

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.nAdmins()).isEqualTo(1);
    Assertions.assertThat(response.nAttendents()).isEqualTo(1);
    Assertions.assertThat(response.nDefaultUsers()).isEqualTo(1);

  }

}
