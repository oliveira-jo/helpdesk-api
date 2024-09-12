package com.oliveira.helpdesk.entity;

import java.util.Date;
import java.util.UUID;

import com.oliveira.helpdesk.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

}
