package com.oliveira.helpdesk.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oliveira.helpdesk.domain.Ticket;
import com.oliveira.helpdesk.domain.TicketInteraction;
import com.oliveira.helpdesk.dto.CreateTicketDto;
import com.oliveira.helpdesk.dto.CreateTicketInteractionDto;
import com.oliveira.helpdesk.dto.TicketDto;
import com.oliveira.helpdesk.dto.TicketInteractionDto;
import com.oliveira.helpdesk.mapper.TicketMapper;
import com.oliveira.helpdesk.service.TicketService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/tickets")
public class TicketController {

  private final TicketService ticketService;

  private final TicketMapper mapper;

  @Operation(description = "This method creates a new support ticket in the system")
  @PostMapping
  public ResponseEntity<TicketDto> create(@RequestBody CreateTicketDto request, Authentication authentication) {

    Ticket domain = mapper.toDomain(request);
    TicketDto createTicket = mapper.toDto(ticketService.createTicket(domain, authentication.getName()));
    return ResponseEntity.ok().body(createTicket);

  }

  @Operation(description = "This method creates a new support ticket interaction in the system")
  @PostMapping(value = "/{id}/interaction")
  public ResponseEntity<TicketDto> createTicketInteraction(@PathVariable(name = "id") UUID ticketId,
      @RequestBody CreateTicketInteractionDto request, Authentication authentication) {

    TicketInteraction domain = mapper.toDomain(request);
    domain.setTicketId(ticketId);

    TicketDto updatedTicket = mapper.toDto(ticketService.ticketInteraction(domain, authentication.getName()));

    return ResponseEntity.ok().body(updatedTicket);

  }

  @Operation(description = "This method list alll tickets")
  @GetMapping
  public ResponseEntity<List<TicketDto>> listAllTickets(Authentication authentication) {

    List<TicketDto> tickets = mapper.toDto(ticketService.listAll(authentication));

    return ResponseEntity.ok().body(tickets);

  }

  @Operation(description = "This method returns a ticket from the sytem by provider id")
  @GetMapping(value = "/{id}")
  public ResponseEntity<TicketDto> getById(@PathVariable(name = "id") UUID ticketId,
      Authentication authentication) {

    TicketDto ticket = mapper.toDto(ticketService.getById(ticketId, authentication));

    return ResponseEntity.ok().body(ticket);

  }

  @Operation(description = "This method returns all interactions of a ticket from the sytem by provider ticket id")
  @GetMapping(value = "/{id}/interactions")
  public ResponseEntity<List<TicketInteractionDto>> getInteractionsByTicketId(@PathVariable(name = "id") UUID ticketId,
      Authentication authentication) {

    List<TicketInteractionDto> titcketInteractions = mapper
        .toTicketInteractionsDto(ticketService.getInteractionsByTicketId(ticketId, authentication));

    return ResponseEntity.ok().body(titcketInteractions);

  }

}
