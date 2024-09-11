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
import lombok.Data;

@Data
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

}
