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

@Entity
@Table(name = "ticket_interactions")
public class TicketInteractionEntity {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "ticket_id")
  private TicketEntity ticket;

  @ManyToOne
  @JoinColumn(name = "sent_by_user_id")
  private UserEntity sentByUser;

  private String message;

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

  public UUID getId() {
    return id;
  }

  public TicketEntity getTicket() {
    return ticket;
  }

  public void setTicket(TicketEntity ticket) {
    this.ticket = ticket;
  }

  public UserEntity getSentByUser() {
    return sentByUser;
  }

  public void setSentByUser(UserEntity sentByUser) {
    this.sentByUser = sentByUser;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public TicketStatus getStatus() {
    return status;
  }

  public void setStatus(TicketStatus status) {
    this.status = status;
  }

  public UserEntity getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(UserEntity createdBy) {
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
