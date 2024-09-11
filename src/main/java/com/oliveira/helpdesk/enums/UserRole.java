package com.oliveira.helpdesk.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
  ADMIN("admin"), // 0
  SUPPORT_ATTENDANT("support_attendant"), // 1
  CUSTOMER("customer"); // 2

  private String role;

  UserRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }

  @Override
  public String getAuthority() {
    return this.role;
  }

}
