package com.oliveira.helpdesk.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket_attachments")
public class TicketAttachmentEntity {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "ticket_id")
  private TicketEntity ticket;

  @ManyToOne
  @JoinColumn(name = "ticket_interaction_id")
  private TicketInteractionEntity ticketInteraction;

  private String filename;

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

  public TicketInteractionEntity getTicketInteraction() {
    return ticketInteraction;
  }

  public void setTicketInteraction(TicketInteractionEntity ticketInteraction) {
    this.ticketInteraction = ticketInteraction;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
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
