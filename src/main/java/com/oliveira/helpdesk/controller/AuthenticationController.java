package com.oliveira.helpdesk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oliveira.helpdesk.dto.AuthResponseDto;
import com.oliveira.helpdesk.dto.LoginRequestDto;
import com.oliveira.helpdesk.dto.UserDto;
import com.oliveira.helpdesk.mapper.UserMapper;
import com.oliveira.helpdesk.security.CustomUserDetails;
import com.oliveira.helpdesk.security.JwtUtil;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@OpenAPIDefinition
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

  private final JwtUtil jwtUtil;

  private final AuthenticationManager authManager;

  private final UserMapper mapper;

  public AuthenticationController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserMapper mapper) {
    this.jwtUtil = jwtUtil;
    this.authManager = authenticationManager;
    this.mapper = mapper;

  }

  @Operation(description = "This method do a login in the system and the return is a bearer token to be used in all endpoints.", method = "POST")
  @PostMapping(value = "/login")
  public ResponseEntity<AuthResponseDto> doLogin(@RequestBody @Valid LoginRequestDto request) {

    try {
      Authentication authentication = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.username(),
              request.password()));
      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      AuthResponseDto tokenResponse = jwtUtil.generateToken(userDetails.getId(), userDetails.getUsername());

      // System.out.println("================================");
      // System.out.println(authentication.getAuthorities());

      return ResponseEntity.ok().body(tokenResponse);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @Operation(description = "This method return the user logged in the system.")
  @GetMapping
  public ResponseEntity<UserDto> getUserLogged(Authentication authentication) {

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    return ResponseEntity.ok().body(mapper.toDto(userDetails.getUser()));

  }
}
