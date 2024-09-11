package com.oliveira.helpdesk.entity;

import java.util.Date;
import java.util.UUID;

import com.oliveira.helpdesk.enums.TicketStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tickets")
public class TicketEntity {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "support_user_id")
  private UserEntity supportUser;

  private String subject;

  private String description;

  // Save the name of enums not the number in the BD
  @Enumerated(EnumType.STRING)
  private TicketStatus status;

  @ManyToOne
  @JoinColumn(name = "created_by")
  private UserEntity createdBy;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "updated_by")
  private UUID updatedBy;

  @Column(name = "updated_at")
  private Date updateAt;

}
