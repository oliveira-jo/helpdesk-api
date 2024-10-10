package com.oliveira.helpdesk.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.TicketStatus;

import lombok.Data;

@Data
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
}
