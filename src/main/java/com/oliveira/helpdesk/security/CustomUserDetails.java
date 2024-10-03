package com.oliveira.helpdesk.security;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.oliveira.helpdesk.domain.User;
import com.oliveira.helpdesk.enums.UserRole;

public class CustomUserDetails implements UserDetails {

  private User user;

  public CustomUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.user.role() == UserRole.ADMIN)
      return List.of(
          new SimpleGrantedAuthority("ROLE_ADMIN"),
          new SimpleGrantedAuthority("SUPPORT_ATTENDANT"),
          new SimpleGrantedAuthority("ROLE_CUSTOMER"));

    else if (this.user.role() == UserRole.SUPPORT_ATTENDANT)
      return List.of(
          new SimpleGrantedAuthority("SUPPORT_ATTENDANT"),
          new SimpleGrantedAuthority("ROLE_CUSTOMER"));

    else
      return List.of(
          new SimpleGrantedAuthority("ROLE_CUSTOMER"));

    // return List.of();
  }

  public User getUser() {
    return this.user;
  }

  public UUID getId() {
    return user.id();
  }

  @Override
  public String getPassword() {
    return user.password();
  }

  @Override
  public String getUsername() {
    return user.username();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
