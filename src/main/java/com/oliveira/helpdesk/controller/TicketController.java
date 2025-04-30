package com.oliveira.helpdesk.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oliveira.helpdesk.dto.CreateTicketDto;
import com.oliveira.helpdesk.dto.CreateTicketInteractionDto;
import com.oliveira.helpdesk.dto.StatusResponseDto;
import com.oliveira.helpdesk.dto.TicketDto;
import com.oliveira.helpdesk.dto.TicketInteractionDto;
import com.oliveira.helpdesk.dto.UpdateTicketDto;
import com.oliveira.helpdesk.mapper.TicketMapper;
import com.oliveira.helpdesk.service.TicketService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;

@OpenAPIDefinition
@RestController
@RequestMapping(path = "/tickets")
public class TicketController {

  private final TicketService ticketService;

  private final TicketMapper mapper;

  public TicketController(TicketService ticketService, TicketMapper mapper) {
    this.ticketService = ticketService;
    this.mapper = mapper;

  }

  @Operation(description = "This method CREATE a new support ticket in the system", method = "POST")
  @PostMapping
  public ResponseEntity<TicketDto> create(@RequestBody CreateTicketDto request, Authentication authentication) {

    return ResponseEntity.ok().body(
        mapper.toDto(
            ticketService.createTicket(request, authentication.getName())));

  }

  @Operation(description = "This method UPDATE a support ticket in the system", method = "UPDATE")
  @PutMapping(value = "/{id}")
  public ResponseEntity<TicketDto> update(@PathVariable(name = "id") UUID id, @RequestBody UpdateTicketDto request,
      Authentication authentication) {

    return ResponseEntity.ok().body(
        mapper.toDto(
            this.ticketService.updateTicket(id, request, authentication)));

  }

  @Operation(description = "This method DELETE a support ticket in the system", method = "DELETE")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteTichet(@PathVariable(name = "id") UUID id, Authentication authentication) {

    this.ticketService.delete(id, authentication);
    return ResponseEntity.ok().build();

  }

  @Operation(description = "This method CREATE a new support ticket interaction in the system", method = "POST")
  @PostMapping(value = "/{id}/interaction")
  public ResponseEntity<TicketDto> createTicketInteraction(@PathVariable(name = "id") UUID ticketId,
      @RequestBody CreateTicketInteractionDto request, Authentication authentication) {

    return ResponseEntity.ok().body(
        mapper.toDto(
            ticketService.ticketInteraction(ticketId, request, authentication.getName())));

  }

  @Operation(description = "This method list all tickets and return a list of TicketsDto", method = "GET")
  @GetMapping
  public ResponseEntity<List<TicketDto>> listAllTickets(Authentication authentication) {

    return ResponseEntity.ok().body(
        mapper.toDto(ticketService.listAll(authentication)));

  }

  @Operation(description = "This method returs the numbers of tickets saved in the DB with it's status", method = "GET")
  @GetMapping(value = "/numberOfStatus")
  public ResponseEntity<StatusResponseDto> numberOfStatus(Authentication authentication) {

    return ResponseEntity.ok().body(
        this.ticketService.numberOfStatus(authentication));

  }

  @Operation(description = "This method GET a ticket from the sytem by provider id", method = "GET")
  @GetMapping(value = "/{id}")
  public ResponseEntity<TicketDto> getById(@PathVariable(name = "id") UUID ticketId,
      Authentication authentication) {

    return ResponseEntity.ok().body(
        mapper.toDto(ticketService.getById(ticketId, authentication)));

  }

  @Operation(description = "This method GET all interactions of a ticket from the sytem by provider ticket id", method = "GET")
  @GetMapping(value = "/{id}/interactions")
  public ResponseEntity<List<TicketInteractionDto>> getInteractionsByTicketId(@PathVariable(name = "id") UUID ticketId,
      Authentication authentication) {

    return ResponseEntity.ok().body(
        mapper.toTicketInteractionsDto(
            ticketService.getInteractionsByTicketId(ticketId, authentication)));

  }

}
