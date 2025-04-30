package com.oliveira.helpdesk.integration;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import com.oliveira.helpdesk.dto.AuthResponseDto;
import com.oliveira.helpdesk.dto.CreateUserDto;
import com.oliveira.helpdesk.dto.LoginRequestDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.UserRole;
import com.oliveira.helpdesk.repository.UserRepository;
import com.oliveira.helpdesk.utils.UserCreator;

@DisplayName("Integration tests for User Controller")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIT {

  @Autowired
  private TestRestTemplate testeRestTemplate;

  @LocalServerPort
  private int port;

  @Autowired
  private UserRepository userRepository;

  public String getBaseUrl() {
    return "http://localhost:" + port + "/api/v1/users";
  }

  public String getBaseUrlAuth() {
    return "http://localhost:" + port + "/api/v1/auth/login";
  }

  @Test
  @DisplayName("This method create a user and return a UserDto WhenSuccessful")
  void create_ReturnUserDto_WhenSuccessful() {
    String url = getBaseUrl() + "/register";

    CreateUserDto savedUser = UserCreator.createUserRequestDto();

    ResponseEntity<UserDto> response = testeRestTemplate.postForEntity(url, savedUser, UserDto.class);

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().id()).isNotNull();

  }

  @Test
  @DisplayName("This method create a support user and return a UserDto WhenSuccessful")
  void createSupportUser_ReturnUserDto_WhenSuccessful() {

    String url = getBaseUrl() + "/registerSupportAttendent";

    CreateUserDto savedUser = UserCreator.createUserRequestDto();

    HttpEntity<CreateUserDto> request = new HttpEntity<>(savedUser, this.getHeaderWithToken("admin", "password"));

    ResponseEntity<UserDto> response = testeRestTemplate.postForEntity(url, request, UserDto.class);

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().id()).isNotNull();

  }

  @Test
  @DisplayName("This method update a user and Return a UserDto WhenSuccessful")
  void update_ReturnUserDto_WhenSuccessful() {

    String url = getBaseUrl() + "/{id}";

    UserEntity savedUser = this.userRepository.save(UserCreator.createUserToBeSaved());

    savedUser.setName("NEW NAME");

    HttpEntity<UserEntity> request = new HttpEntity<>(savedUser, this.getHeaderWithToken("admin", "password"));

    ResponseEntity<UserDto> response = testeRestTemplate.exchange(
        url, HttpMethod.PUT, request, UserDto.class,
        savedUser.getId());

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody().name()).isEqualTo(savedUser.getName());

  }

  @Test
  @DisplayName("This method find a user by a provide id and Return a UserDto WhenSuccessful")
  void findById_ReturnUserDto_WhenSuccessful() {

    // CREATE AND SAVE IN DATABASE
    UserEntity savedUser = this.userRepository.save(UserCreator.createUserToBeSaved());
    UUID exepctedId = savedUser.getId();

    // CREATE A HEADER WITH TOKEN
    HttpEntity<Void> requestEntity = new HttpEntity<>(this.getHeaderWithToken("admin", "password"));

    // CALL TO THE API PASSING ID AND HEADER
    String url = getBaseUrl() + "/" + exepctedId;

    UserDto response = testeRestTemplate.exchange(url,
        HttpMethod.GET, requestEntity, UserDto.class).getBody();

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.id()).isNotNull().isEqualTo(exepctedId);

  }

  @Test
  @DisplayName("This method find a user by a provide username and Return a UserDto WhenSuccessful")
  void findByUsername_ReturnUserDto_WhenSuccessful() {

    // CREATE AND SAVE IN DATABASE
    UserEntity savedUser = this.userRepository.save(UserCreator.createUserToBeSaved());
    String exepctedUsername = savedUser.getUsername();

    // CREATE A HEADER WITH TOKEN
    HttpEntity<Void> requestEntity = new HttpEntity<>(this.getHeaderWithToken("admin", "password"));

    // CALL TO THE API PASSING ID AND HEADER
    String url = getBaseUrl() + "/GetUserByUsername/" + exepctedUsername;

    UserDto response = testeRestTemplate.exchange(url,
        HttpMethod.GET, requestEntity, UserDto.class).getBody();

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.id()).isNotNull().isEqualTo(savedUser.getId());
    Assertions.assertThat(response.username()).isNotNull().isEqualTo(exepctedUsername);

  }

  @Test
  @DisplayName("This method delete a user by a provide id and return void WhenSuccessful")
  void delete_ReturnUserDto_WhenSuccessful() {

    String url = getBaseUrl() + "/{id}";

    UserEntity savedUser = this.userRepository.save(UserCreator.createUserToBeSaved());
    savedUser.setUsername("test");
    savedUser.setPassword(new BCryptPasswordEncoder().encode("password"));
    savedUser.setRole(UserRole.CUSTOMER);
    this.userRepository.save(savedUser);

    HttpEntity<Void> requestEntity = new HttpEntity<>(this.getHeaderWithToken("test", "password"));

    ResponseEntity<Void> response = testeRestTemplate.exchange(
        url, HttpMethod.DELETE, requestEntity, Void.class,
        savedUser.getId());

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

  }

  HttpHeaders getHeaderWithToken(String username, String password) {

    String url = getBaseUrlAuth();

    LoginRequestDto request = new LoginRequestDto(username, password);

    ResponseEntity<AuthResponseDto> response = testeRestTemplate.postForEntity(url, request, AuthResponseDto.class);

    HttpHeaders headers = new HttpHeaders();

    headers.set("Authorization", "Bearer " + response.getBody().accessToken());

    return headers;

  }

}
