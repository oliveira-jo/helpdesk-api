package com.oliveira.helpdesk.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.dto.CreateUserDto;
import com.oliveira.helpdesk.dto.NumberUsersDto;
import com.oliveira.helpdesk.dto.UpdateUserDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.mapper.UserMapper;
import com.oliveira.helpdesk.service.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {

  private final UserService userService;

  private final UserMapper mapper;

  @Operation(description = "[PUBLIC] This method CREATE a new user with role CUSTOMER in the system.", method = "POST")
  @PostMapping("/register")
  public ResponseEntity<UserDto> create(@RequestBody CreateUserDto request) {

    User domain = mapper.toDomain(request);
    UserDto createUser = mapper.toDto(userService.createUser(domain));
    return ResponseEntity.ok().body(createUser);

  }

  @Operation(description = "[PRIVATE] This method CREATE a new user with role SUPPORT_ATTENDENT in the system. Just for admin", method = "POST")
  @PostMapping("/registerSupportAttendent")
  public ResponseEntity<UserDto> createSupportUser(@RequestBody CreateUserDto request) {

    User domain = mapper.toDomain(request);
    UserDto createUser = mapper.toDto(userService.createSupportUser(domain));
    return ResponseEntity.ok().body(createUser);

  }

  @Operation(description = "This method UPDATE a user by a provide id in the system.", method = "PUT")
  @PutMapping(value = "/{id}")
  public ResponseEntity<UserDto> update(@PathVariable(name = "id") UUID id, @RequestBody UpdateUserDto request,
      Authentication authentication) {

    UserDto user = mapper.toDto(this.userService.update(id, request, authentication));
    return ResponseEntity.ok().body(user);

  }

  @Operation(description = "This method FIND a user by a provide id in the system", method = "GET")
  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDto> findById(@PathVariable(name = "id") UUID id) {

    UserDto user = mapper.toDto(userService.findById(id));
    return ResponseEntity.ok().body(user);

  }

  @Operation(description = "[PRIVATE] This method FIND all user of system", method = "GET")
  @GetMapping(value = "/GetUsers")
  public ResponseEntity<List<UserDto>> findAllUsers(Authentication authentication) {

    List<UserDto> users = userService.findAllUsers(authentication);
    return ResponseEntity.ok().body(users);

  }

  @Operation(description = "This method FIND a user in the system passsing a username with parameter", method = "GET")
  @GetMapping(value = "GetUserByUsername")
  public ResponseEntity<UserDto> findByUsername(@RequestBody String username) {

    UserDto user = mapper.toDto(userService.findByUsername(username));
    return ResponseEntity.ok().body(user);

  }

  @Operation(description = "This method DELETE a user by a provide id in the system", method = "DELETE")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<UserDto> delete(@PathVariable("id") UUID id, Authentication authentication) {

    this.userService.delete(id, authentication);
    return ResponseEntity.ok().build();

  }

  @Operation(description = "This method returs the numbers of users by Role saved in the DB", method = "GET")
  @GetMapping(value = "/numberOfUsers")
  public ResponseEntity<NumberUsersDto> numberOfUsers(Authentication authentication) {

    NumberUsersDto data = this.userService.numberOfUsers(authentication);
    return ResponseEntity.ok().body(data);

  }
}
