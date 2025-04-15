package com.oliveira.helpdesk.utils;

import java.util.Date;
import java.util.UUID;

import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.enums.TicketStatus;

public class TicketCreator {

  private static final UUID TICKET_ID = UUID.randomUUID();

  public static TicketEntity createTicketToBeSaved() {

    TicketEntity ticketToBeSaved = new TicketEntity();
    ticketToBeSaved.setSubject("Test Ticket");
    ticketToBeSaved.setDescription("Test Ticket Description");

    ticketToBeSaved.setCreatedAt(new Date());
    ticketToBeSaved.setSupportUser(UserCreator.createValidUser());
    ticketToBeSaved.setCreatedBy(UserCreator.createValidUser());

    ticketToBeSaved.setStatus(TicketStatus.OPEN);

    return ticketToBeSaved;

  }

  public static TicketEntity createValidTicket() {

    TicketEntity validTicket = new TicketEntity();
    validTicket.setId(TICKET_ID);
    validTicket.setSubject("Test Ticket");
    validTicket.setDescription("Test Ticket Description");
    validTicket.setStatus(TicketStatus.OPEN);

    return validTicket;

  }

  public static TicketEntity createValidUpdateTicket() {

    TicketEntity ticketUpdateValid = new TicketEntity();
    ticketUpdateValid.setId(TICKET_ID);
    ticketUpdateValid.setSubject("Test Ticket Updated");
    ticketUpdateValid.setDescription("Test Ticket Description Updated");
    ticketUpdateValid.setStatus(TicketStatus.IN_PROGRESS);

    return ticketUpdateValid;

  }

}
