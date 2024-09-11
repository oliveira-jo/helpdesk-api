package com.oliveira.helpdesk.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.TicketStatus;

import lombok.Data;

@Data
public class Ticket {

  private UUID id;

  private User supportUser;

  private String subject;

  private String description;

  private TicketStatus status;

  private UUID createdByUserId;

  private UserEntity createdBy;

  private List<Attachment> attachments;

  private Date createdAt;

  private UUID updatedBy;

  private Date updateAt;

}
