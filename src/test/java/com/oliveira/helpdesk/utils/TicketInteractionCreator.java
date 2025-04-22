package com.oliveira.helpdesk.utils;

import java.util.Date;

import com.oliveira.helpdesk.domain.TicketInteraction;
import com.oliveira.helpdesk.entity.TicketInteractionEntity;
import com.oliveira.helpdesk.enums.TicketStatus;

public class TicketInteractionCreator {

  // private static final UUID TICKET_INTERACTION_ID = UUID.randomUUID();

  public static TicketInteractionEntity createTicketToBeSaved() {

    TicketInteractionEntity ticketInteractionToBeSaved = new TicketInteractionEntity();

    ticketInteractionToBeSaved.setTicket(TicketCreator.createValidTicketEntity());
    ticketInteractionToBeSaved.setSentByUser(UserCreator.createValidUser());

    ticketInteractionToBeSaved.setMessage("Ticket Interaction Message");
    ticketInteractionToBeSaved.setCreatedAt(new Date());
    ticketInteractionToBeSaved.setCreatedBy(UserCreator.createValidUser());
    ticketInteractionToBeSaved.setStatus(TicketStatus.OPEN);

    return ticketInteractionToBeSaved;

  }

  public static TicketInteractionEntity createValidTicket() {

    TicketInteractionEntity ticketInteractionToBeSaved = new TicketInteractionEntity();

    ticketInteractionToBeSaved.setTicket(TicketCreator.createValidTicketEntity());
    ticketInteractionToBeSaved.setSentByUser(UserCreator.createValidUser());

    ticketInteractionToBeSaved.setMessage("Ticket Interaction Message");
    ticketInteractionToBeSaved.setCreatedAt(new Date());
    ticketInteractionToBeSaved.setCreatedBy(UserCreator.createValidUser());
    ticketInteractionToBeSaved.setStatus(TicketStatus.OPEN);

    return ticketInteractionToBeSaved;

  }

  public static TicketInteraction createValidUpdateTicket() {

    TicketInteraction ticketInteractionToBeSaved = new TicketInteraction();

    ticketInteractionToBeSaved.setTicket(TicketCreator.createValidTicketEntity());

    ticketInteractionToBeSaved.setMessage("Ticket Interaction Message Updated");
    ticketInteractionToBeSaved.setCreatedAt(new Date());
    ticketInteractionToBeSaved.setCreatedBy(UserCreator.createValidUser());
    ticketInteractionToBeSaved.setStatus(TicketStatus.IN_PROGRESS);

    return ticketInteractionToBeSaved;

  }

}
