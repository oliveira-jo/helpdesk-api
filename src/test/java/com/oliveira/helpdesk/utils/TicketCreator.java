package com.oliveira.helpdesk.utils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.oliveira.helpdesk.domain.Ticket;
import com.oliveira.helpdesk.domain.TicketInteraction;
import com.oliveira.helpdesk.dto.CreateTicketDto;
import com.oliveira.helpdesk.dto.CreateTicketInteractionDto;
import com.oliveira.helpdesk.dto.StatusResponseDto;
import com.oliveira.helpdesk.dto.TicketDto;
import com.oliveira.helpdesk.dto.TicketInteractionDto;
import com.oliveira.helpdesk.dto.UpdateTicketDto;
import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.entity.TicketInteractionEntity;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.TicketStatus;

public class TicketCreator {

  private static UUID TICKET_ID = UUID.randomUUID();

  private static final String TICKET_SUBJECT = "Test-Ticket";
  private static final String TICKET_DESCRIPTION = "Test Ticket Description";
  private static final Date TICKET_DATA = new Date();
  private static final UserEntity TICKET_CREATED_BY = UserCreator.createValidUser();
  private static final TicketStatus TICKET_STATUS = TicketStatus.OPEN;

  private static UUID TICKET_INTERACTION_ID = UUID.randomUUID();

  public static Ticket createTicketToBeSaved() {
    Ticket ticket = new Ticket();
    ticket.setSubject(TICKET_SUBJECT);
    ticket.setDescription(TICKET_DESCRIPTION);
    return ticket;

  }

  public static TicketEntity createTicketEntityToBeSaved() {
    TicketEntity ticket = new TicketEntity();
    ticket.setSubject(TICKET_SUBJECT);
    ticket.setDescription(TICKET_DESCRIPTION);
    ticket.setStatus(TICKET_STATUS);
    ticket.setCreatedAt(TICKET_DATA);
    ticket.setCreatedBy(TICKET_CREATED_BY);
    return ticket;

  }

  public static Ticket createValidTicket() {
    Ticket ticket = new Ticket();
    ticket.setId(TICKET_ID);
    ticket.setSubject(TICKET_SUBJECT);
    ticket.setDescription(TICKET_DESCRIPTION);
    ticket.setCreatedAt(TICKET_DATA);
    ticket.setCreatedBy(TICKET_CREATED_BY);
    ticket.setStatus(TICKET_STATUS);
    return ticket;

  }

  public static TicketEntity createValidTicketEntity() {
    TicketEntity ticket = new TicketEntity();
    ticket.setId(TICKET_ID);
    ticket.setSubject(TICKET_SUBJECT);
    ticket.setDescription(TICKET_DESCRIPTION);
    ticket.setCreatedAt(TICKET_DATA);
    ticket.setCreatedBy(TICKET_CREATED_BY);
    ticket.setStatus(TICKET_STATUS);
    return ticket;

  }

  public static CreateTicketDto createTicketRequestDto() {
    return new CreateTicketDto(TICKET_SUBJECT, TICKET_DESCRIPTION, null);

  }

  public static TicketDto createTicketResponseDto() {
    return new TicketDto(TICKET_ID, null, TICKET_SUBJECT, TICKET_DESCRIPTION,
        TICKET_STATUS, null, TICKET_DATA, TICKET_CREATED_BY, TICKET_DATA);
  }

  public static Ticket createValidUpdateTicket() {
    Ticket ticket = new Ticket();
    ticket.setId(TICKET_ID);
    ticket.setSubject("test-ticket-update");
    ticket.setDescription("test-description-update");
    ticket.setCreatedAt(TICKET_DATA);
    ticket.setCreatedBy(TICKET_CREATED_BY);
    ticket.setStatus(TICKET_STATUS);
    return ticket;

  }

  public static TicketEntity createValidUpdateTicketEntity() {
    TicketEntity ticket = new TicketEntity();
    ticket.setId(TICKET_ID);
    ticket.setSubject("test-ticket-update");
    ticket.setDescription("test-description-update");
    ticket.setCreatedAt(TICKET_DATA);
    ticket.setCreatedBy(TICKET_CREATED_BY);
    ticket.setStatus(TICKET_STATUS);
    return ticket;

  }

  public static UpdateTicketDto createUpdateTicketDto() {
    return new UpdateTicketDto("test-ticket-update", "test-description-update", TICKET_STATUS, null);

  }

  public static TicketDto createUpdateTicketDtoUpdated() {
    return new TicketDto(TICKET_ID, null, "test-ticket-update",
        "test-description-update", TICKET_STATUS, null, TICKET_DATA, TICKET_CREATED_BY, TICKET_DATA);

  }

  public static StatusResponseDto createStatusResponseDto() {
    return new StatusResponseDto(1, 1, 1, 1, 1);

  }

  public static List<TicketInteraction> createListTicketInteractions() {
    return List.of(
        new TicketInteraction(TICKET_INTERACTION_ID, createValidTicketEntity(), UserCreator.createValidUser(),
            "message-1"));

  }

  public static List<TicketInteractionDto> createListTicketInteractionsDto() {
    return List.of(
        new TicketInteractionDto(TICKET_INTERACTION_ID, "message-1", TICKET_STATUS, null,
            UserCreator.createUserResponseDto(), TICKET_DATA));
  }

  public static TicketInteraction createValidTicketInteraction() {
    TicketInteraction ticketInteraction = new TicketInteraction();
    ticketInteraction.setId(TICKET_INTERACTION_ID);
    ticketInteraction.setTicket(createValidTicketEntity());
    ticketInteraction.setSentByUser(UserCreator.createValidUser());
    ticketInteraction.setMessage("message-1");
    ticketInteraction.setCreatedAt(TICKET_DATA);
    ticketInteraction.setCreatedBy(UserCreator.createValidUser());
    return ticketInteraction;

  }

  public static CreateTicketInteractionDto createTicketInteractionDto() {
    return new CreateTicketInteractionDto("message-1-dto", null);

  }

  public static TicketInteractionEntity createValidTicketInteractionEntity() {
    TicketInteractionEntity ticketInteraction = new TicketInteractionEntity();
    ticketInteraction.setTicket(createValidTicketEntity());
    ticketInteraction.setSentByUser(UserCreator.createValidUser());
    ticketInteraction.setMessage("message-1");
    ticketInteraction.setStatus(TICKET_STATUS);
    ticketInteraction.setCreatedAt(TICKET_DATA);
    ticketInteraction.setCreatedBy(UserCreator.createValidUser());
    return ticketInteraction;

  }

}
