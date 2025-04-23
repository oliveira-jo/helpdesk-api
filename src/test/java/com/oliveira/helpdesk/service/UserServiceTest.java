package com.oliveira.helpdesk.service;

import java.util.List;
import java.util.Optional;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.dto.NumberUsersDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.UserRole;
import com.oliveira.helpdesk.mapper.UserMapper;
import com.oliveira.helpdesk.repository.UserRepository;
import com.oliveira.helpdesk.utils.UserCreator;

@DisplayName("User Service Test")
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepositoryMock;

  @Mock
  private UserMapper userMapperMock;

  @Mock
  private Authentication authenticationMock;

  @BeforeEach
  void setUp() {

    BDDMockito.when(this.userRepositoryMock.findByUsername(ArgumentMatchers.any()))
        .thenReturn(Optional.of(UserCreator.createValidUser()));

    BDDMockito.when(this.userMapperMock.toEntity(ArgumentMatchers.any(User.class)))
        .thenReturn(UserCreator.createUserToBeSaved());

    BDDMockito.when(this.userRepositoryMock.save(ArgumentMatchers.any(UserEntity.class)))
        .thenReturn(UserCreator.createValidUser());

    BDDMockito.when(this.userMapperMock.toDomain(ArgumentMatchers.any(UserEntity.class)))
        .thenReturn(UserCreator.createValidUserDomain());

    BDDMockito.when(this.userRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
        .thenReturn(Optional.of(UserCreator.createValidUser()));

  }

  @Test
  @DisplayName("createUser returns User WhenSuccessfull")
  void createUser_ReturnsUser__WhenSuccessfull() {

    BDDMockito.when(this.userRepositoryMock.findByUsername(ArgumentMatchers.anyString()))
        .thenReturn(Optional.empty());

    User response = this.userService.createUser(UserCreator.createUserDomainToBeSaved());

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getId()).isEqualTo(UserCreator.createValidUser().getId());
    Assertions.assertThat(response.getName()).isEqualTo(UserCreator.createValidUser().getName());
    Assertions.assertThat(response.getUsername()).isEqualTo(UserCreator.createValidUser().getUsername());
    Assertions.assertThat(response.getEmail()).isEqualTo(UserCreator.createValidUser().getEmail());
    Assertions.assertThat(response.isActive()).isEqualTo(UserCreator.createValidUser().isActive());
    Assertions.assertThat(response.getPassword()).isEqualTo(UserCreator.createValidUser().getPassword());

  }

  @Test
  @DisplayName(" update user returns User WhenSuccessfull")
  void update_ReturnsUser_WhenSuccessfull() {

    BDDMockito.when(this.userRepositoryMock.save(ArgumentMatchers.any()))
        .thenReturn(UserCreator.createUpdateUser());

    BDDMockito.when(this.userMapperMock.toDomain(ArgumentMatchers.any(UserEntity.class)))
        .thenReturn(UserCreator.createValidUpdateUser());

    User updatedUser = this.userService.update(
        UserCreator.createUpdateUser().getId(), UserCreator.createValidUpdateUserDto(),
        authenticationMock);

    Assertions.assertThat(updatedUser).isNotNull();
    Assertions.assertThat(updatedUser.getId()).isEqualTo(UserCreator.createValidUpdateUser().getId());
    Assertions.assertThat(updatedUser.getName()).isEqualTo(UserCreator.createValidUpdateUser().getName());
    Assertions.assertThat(updatedUser.getUsername()).isEqualTo(UserCreator.createValidUpdateUser().getUsername());
    Assertions.assertThat(updatedUser.getEmail()).isEqualTo(UserCreator.createValidUpdateUser().getEmail());
    Assertions.assertThat(updatedUser.isActive()).isEqualTo(UserCreator.createValidUpdateUser().isActive());
    Assertions.assertThat(updatedUser.getPassword()).isEqualTo(UserCreator.createValidUpdateUser().getPassword());

  }

  @Test
  @DisplayName("findByUsername returns a User WhenSuccessfull")
  void findByUsername_ReturnsUser_WhenSuccessfull() {

    User response = userService.findByUsername(UserCreator.createValidUserDomain().getUsername());

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getId()).isEqualTo(UserCreator.createValidUserDomain().getId());
    Assertions.assertThat(response.getName()).isEqualTo(UserCreator.createValidUserDomain().getName());
    Assertions.assertThat(response.getUsername()).isEqualTo(UserCreator.createValidUserDomain().getUsername());
    Assertions.assertThat(response.getEmail()).isEqualTo(UserCreator.createValidUserDomain().getEmail());
    Assertions.assertThat(response.getPassword()).isEqualTo(UserCreator.createValidUserDomain().getPassword());
    Assertions.assertThat(response.isActive()).isEqualTo(UserCreator.createValidUserDomain().isActive());

  }

  @Test
  @DisplayName("findById returns a User WhenSuccessfull")
  void findById_ReturnsUser_WhenSuccessfull() {

    User response = userService.findById(UserCreator.createValidUserDomain().getId());

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getId()).isEqualTo(UserCreator.createValidUserDomain().getId());
    Assertions.assertThat(response.getName()).isEqualTo(UserCreator.createValidUserDomain().getName());
    Assertions.assertThat(response.getUsername()).isEqualTo(UserCreator.createValidUserDomain().getUsername());
    Assertions.assertThat(response.getEmail()).isEqualTo(UserCreator.createValidUserDomain().getEmail());
    Assertions.assertThat(response.getPassword()).isEqualTo(UserCreator.createValidUserDomain().getPassword());
    Assertions.assertThat(response.isActive()).isEqualTo(UserCreator.createValidUserDomain().isActive());

  }

  @Test
  @DisplayName("findAllUsers returns a list of UsersDto WhenSuccessfull foradmin")
  void findAllUsers_ReturnsListOfUserDto_WhenSuccessfull() {

    BDDMockito.when(this.userMapperMock.toDto(ArgumentMatchers.any(List.class)))
        .thenReturn(List.of(UserCreator.createUserResponseDto()));

    List<UserDto> response = userService.findAllUsers(authenticationMock);

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response).isNotEmpty();
    Assertions.assertThat(response.size()).isEqualTo(1);
    Assertions.assertThat(response.get(0).id()).isEqualTo(UserCreator.createValidUser().getId());
    Assertions.assertThat(response.get(0).name()).isEqualTo(UserCreator.createValidUser().getName());
    Assertions.assertThat(response.get(0).username()).isEqualTo(UserCreator.createValidUser().getUsername());
    Assertions.assertThat(response.get(0).email()).isEqualTo(UserCreator.createValidUser().getEmail());
    Assertions.assertThat(response.get(0).active()).isEqualTo(UserCreator.createValidUser().isActive());

  }

  @Test
  @DisplayName(" delete do nothing if user was deleted WhenSuccessfull")
  void delete_doNothingReturnVoid_WhenSuccessfull() {

    UserEntity userToDelete = UserCreator.createValidUser();
    userToDelete.setRole(UserRole.CUSTOMER);

    BDDMockito.when(this.userRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
        .thenReturn(Optional.of(userToDelete));

    UUID userId = UserCreator.createValidUser().getId();

    BDDMockito.doNothing().when(this.userRepositoryMock).delete(ArgumentMatchers.any());

    Assertions.assertThatCode(() -> this.userService.delete(userId, authenticationMock))
        .doesNotThrowAnyException();

    BDDMockito.verify(this.userRepositoryMock, BDDMockito.times(1)).delete(ArgumentMatchers.any());

  }

  @Test
  @DisplayName("numberOfUsers returns NumberUsersDto, JUST to admin, WhenSuccessfull")

  void numberOfUsers_ReturnsNumberUsersDTO_WhenSuccessfull() {

    BDDMockito.when(this.userService.findAllUsers(authenticationMock))
        .thenReturn(List.of(UserCreator.createUserResponseDto()));

    NumberUsersDto response = this.userService.numberOfUsers(authenticationMock);

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.nAdmins()).isEqualTo(1);
    Assertions.assertThat(response.nAttendents()).isEqualTo(0);
    Assertions.assertThat(response.nDefaultUsers()).isEqualTo(0);

  }

}
