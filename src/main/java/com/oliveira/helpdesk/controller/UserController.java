package com.oliveira.helpdesk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.dto.CreateUserDto;
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

  @Operation(description = "[PUBLIC] This method creates a new user with role CUSTOMER in the system. ")
  @PostMapping("/register")
  public ResponseEntity<UserDto> create(@RequestBody CreateUserDto request) {

    User domain = mapper.toDomain(request);
    UserDto createUser = mapper.toDto(userService.createUser(domain));
    return ResponseEntity.ok().body(createUser);

  }

  @Operation(description = "[PRIVATE] This method creates a new user with role SUPPORT_ATTENDENT in the system. Justo for admin")
  @PostMapping("/registerSupportAttendent")
  public ResponseEntity<UserDto> createSupportUser(@RequestBody CreateUserDto request) {

    User domain = mapper.toDomain(request);
    UserDto createUser = mapper.toDto(userService.createSupportUser(domain));
    return ResponseEntity.ok().body(createUser);

  }
}
