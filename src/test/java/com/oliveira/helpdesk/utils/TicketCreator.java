package com.oliveira.helpdesk.utils;

import java.util.List;
import java.util.UUID;

import com.oliveira.helpdesk.domain.Attachment;
import com.oliveira.helpdesk.domain.Ticket;
import com.oliveira.helpdesk.entity.TicketEntity;

public class TicketCreator {

  private static final UUID TICKET_ID = UUID.randomUUID();

  public static Ticket createTicket() {

    Ticket newTicket = new Ticket();
    newTicket.setId(TICKET_ID);
    newTicket.setSubject("Test Ticket");
    newTicket.setAttachments(List.of(new Attachment("file.txt", "base64content")));

    return newTicket;

  }

  public static TicketEntity createTicketEntity() {

    TicketEntity ticketEntity = new TicketEntity();
    ticketEntity.setId(TICKET_ID);
    ticketEntity.setSubject("Test Ticket");

    return ticketEntity;

  }
}
