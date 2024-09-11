package com.oliveira.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    logger.info("Getting information for user {}", username);

    User user = userService.findByUsername(username);
    if (user != null) {
      logger.warn("information for user {} fount", username);
      return new CustomUserDetails(user);
    }

    logger.error("Could not find the user {}", username);

    throw new UsernameNotFoundException("Coul not find user");

  }

}
