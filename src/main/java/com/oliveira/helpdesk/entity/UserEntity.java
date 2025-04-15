package com.oliveira.helpdesk.entity;

import java.util.Date;
import java.util.UUID;

import com.oliveira.helpdesk.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue
  private UUID id;

  private String username;

  private String password;

  private String name;

  private String email;

  private boolean active;

  public UserEntity() {
  }

  @Column(name = "role")
  private UserRole role;

  @Column(name = "created_by")
  private UUID createdBy;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_by")
  private UUID updatedBy;

  @Column(name = "updated_at")
  private Date updateAt;

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public UUID getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(UUID createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public UUID getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(UUID updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Date getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(Date updateAt) {
    this.updateAt = updateAt;
  }

}
