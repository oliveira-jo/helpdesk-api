package com.oliveira.helpdesk.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.TicketStatus;

public class TicketInteraction {

  private UUID id;
  private UUID userId;
  private UUID ticketId;
  private TicketEntity ticket;
  private TicketStatus status;
  private UserEntity sentByUser;
  private String message;
  private UserEntity createdBy;
  private Date createdAt;
  private UUID updatedBy;
  private Date updateAt;
  private List<Attachment> attachments;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public UUID getTicketId() {
    return ticketId;
  }

  public void setTicketId(UUID ticketId) {
    this.ticketId = ticketId;
  }

  public TicketEntity getTicket() {
    return ticket;
  }

  public void setTicket(TicketEntity ticket) {
    this.ticket = ticket;
  }

  public TicketStatus getStatus() {
    return status;
  }

  public void setStatus(TicketStatus status) {
    this.status = status;
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

  public List<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<Attachment> attachments) {
    this.attachments = attachments;
  }

}
