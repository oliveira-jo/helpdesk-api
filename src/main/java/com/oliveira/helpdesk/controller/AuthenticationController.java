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
import com.oliveira.helpdesk.security.CustomUserDetails;
import com.oliveira.helpdesk.security.JwtUtil;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

  private final JwtUtil jwtUtil;

  private final AuthenticationManager authManager;

  @Operation(description = "This method get a bearer token to be used in the system.", method = "POST")
  @PostMapping(value = "/login")
  public ResponseEntity<AuthResponseDto> doLogin(@RequestBody @Valid LoginRequestDto request) {

    try {
      Authentication authentication = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.username(),
              request.password()));
      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      AuthResponseDto tokenResponse = jwtUtil.generateToken(userDetails.getUsername());
      // System.out.println("================================");
      // System.out.println(authentication.getAuthorities());
      return ResponseEntity.ok().body(tokenResponse);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @Operation(description = "This method return the user logged in the system.")
  @GetMapping
  public ResponseEntity<CustomUserDetails> getUserLogged(Authentication authentication) {

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    return ResponseEntity.ok().body(userDetails);

  }
}
