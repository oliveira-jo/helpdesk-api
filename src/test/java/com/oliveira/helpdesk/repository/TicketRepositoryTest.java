package com.oliveira.helpdesk.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.enums.TicketStatus;
import com.oliveira.helpdesk.utils.TicketCreator;

import jakarta.transaction.Transactional;

@DataJpaTest
public class TicketRepositoryTest {

  @Autowired
  private TicketRepository ticketRepository;

  @Test
  @DisplayName("save persists ticket when successful")
  void save_PersistTicket_WhenSuccessful() {
    TicketEntity ticketToBeSaved = TicketCreator.createTicketEntityToBeSaved();
    TicketEntity ticketSaved = this.ticketRepository.save(ticketToBeSaved);

    Assertions.assertThat(ticketSaved)
        .isNotNull()
        .extracting(TicketEntity::getId, TicketEntity::getSubject)
        .containsExactly(ticketToBeSaved.getId(), ticketToBeSaved.getSubject());
  }

  @Test
  @DisplayName("save updates ticket when successful")
  void save_UpdateTicket_WhenSuccessful() {
    TicketEntity ticketToBeSaved = TicketCreator.createTicketEntityToBeSaved();
    TicketEntity ticketSaved = this.ticketRepository.save(ticketToBeSaved);

    ticketSaved.setSubject(TicketCreator.createValidUpdateTicket().getSubject());
    ticketSaved.setStatus(TicketStatus.IN_PROGRESS);

    TicketEntity updatedTicket = this.ticketRepository.save(ticketSaved);

    Assertions.assertThat(updatedTicket)
        .isNotNull()
        .extracting(TicketEntity::getSubject, TicketEntity::getStatus)
        .containsExactly(TicketCreator.createValidUpdateTicket().getSubject(), TicketStatus.IN_PROGRESS);
  }

  @Test
  @DisplayName("delete removes ticket when successful")
  void delete_RemovesTicket_WhenSuccessful() {
    TicketEntity ticketToBeSaved = TicketCreator.createTicketEntityToBeSaved();
    TicketEntity ticketSaved = this.ticketRepository.save(ticketToBeSaved);

    this.ticketRepository.delete(ticketSaved);

    Optional<TicketEntity> ticketOptional = this.ticketRepository.findById(ticketSaved.getId());
    Assertions.assertThat(ticketOptional).isEmpty();
  }

  @Test
  @DisplayName("findAll returns all tickets when successful")
  @Transactional
  void findAll_ReturnsAllTickets_WhenSuccessful() {
    TicketEntity ticket1 = TicketCreator.createTicketEntityToBeSaved();
    this.ticketRepository.save(ticket1);

    List<TicketEntity> tickets = this.ticketRepository.findAll();

    Assertions.assertThat(tickets)
        .isNotEmpty()
        .hasSize(1)
        .contains(ticket1);
  }

  @Test
  @DisplayName("delete does nothing when ticket does not exist")
  void delete_DoesNothing_WhenTicketDoesNotExist() {
    TicketEntity nonExistentTicket = new TicketEntity();
    nonExistentTicket.setId(UUID.randomUUID());

    Assertions.assertThatCode(() -> this.ticketRepository.delete(nonExistentTicket))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("findById returns ticket when successful")
  void findById_ReturnsTicket_WhenSuccessful() {
    TicketEntity ticketToBeSaved = TicketCreator.createTicketEntityToBeSaved();
    TicketEntity ticketSaved = this.ticketRepository.save(ticketToBeSaved);

    Optional<TicketEntity> ticketFound = this.ticketRepository.findById(ticketSaved.getId());

    Assertions.assertThat(ticketFound)
        .isPresent()
        .get()
        .isEqualTo(ticketSaved);
  }

  @Test
  @DisplayName("findById returns empty optional when ID does not exist")
  void findById_ReturnsEmptyOptional_WhenIdDoesNotExist() {
    Optional<TicketEntity> ticketFound = this.ticketRepository.findById(UUID.randomUUID());

    Assertions.assertThat(ticketFound).isEmpty();
  }

  @Test
  @DisplayName("update ticket status when successful")
  void update_TicketStatus_WhenSuccessful() {
    TicketEntity ticketToBeSaved = TicketCreator.createTicketEntityToBeSaved();
    TicketEntity ticketSaved = this.ticketRepository.save(ticketToBeSaved);

    ticketSaved.setStatus(TicketStatus.RESOLVED);
    TicketEntity updatedTicket = this.ticketRepository.save(ticketSaved);

    Assertions.assertThat(updatedTicket)
        .isNotNull()
        .extracting(TicketEntity::getStatus)
        .isEqualTo(TicketStatus.RESOLVED);
  }
}
