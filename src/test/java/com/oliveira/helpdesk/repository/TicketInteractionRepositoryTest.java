package com.oliveira.helpdesk.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.oliveira.helpdesk.entity.TicketInteractionEntity;
import com.oliveira.helpdesk.enums.TicketStatus;
import com.oliveira.helpdesk.utils.TicketCreator;
import com.oliveira.helpdesk.utils.TicketInteractionCreator;

import jakarta.transaction.Transactional;

@DataJpaTest
public class TicketInteractionRepositoryTest {

  @Autowired
  private TicketInteractionRepository ticketInteractionRepository;

  @Test
  @DisplayName("save persists ticket interaction when successful")
  void save_PersistTicketInteraction_WhenSuccessful() {
    TicketInteractionEntity ticketInteractionToBeSaved = TicketInteractionCreator.createTicketToBeSaved();
    TicketInteractionEntity ticketInteractionSaved = this.ticketInteractionRepository.save(ticketInteractionToBeSaved);

    Assertions.assertThat(ticketInteractionSaved)
        .isNotNull()
        .extracting(TicketInteractionEntity::getMessage, TicketInteractionEntity::getStatus)
        .containsExactly(ticketInteractionToBeSaved.getMessage(), ticketInteractionToBeSaved.getStatus());
  }

  @Test
  @DisplayName("save updates ticket interaction when successful")
  void save_UpdateTicketInteraction_WhenSuccessful() {
    TicketInteractionEntity ticketInteractionToBeSaved = TicketInteractionCreator.createTicketToBeSaved();
    TicketInteractionEntity ticketInteractionSaved = this.ticketInteractionRepository.save(ticketInteractionToBeSaved);

    ticketInteractionSaved.setMessage(TicketInteractionCreator.createValidUpdateTicket().getMessage());
    ticketInteractionSaved.setStatus(TicketStatus.IN_PROGRESS);

    TicketInteractionEntity ticketInteractionUpdated = this.ticketInteractionRepository.save(ticketInteractionSaved);

    Assertions.assertThat(ticketInteractionUpdated)
        .isNotNull()
        .extracting(TicketInteractionEntity::getMessage, TicketInteractionEntity::getStatus)
        .containsExactly(TicketInteractionCreator.createValidUpdateTicket().getMessage(), TicketStatus.IN_PROGRESS);
  }

  @Test
  @DisplayName("findAll returns all ticket interactions when successful")
  @Transactional
  void findAll_ReturnsAllTicketsInteraction_WhenSuccessful() {
    TicketInteractionEntity ticketInteractionToBeSaved = TicketInteractionCreator.createTicketToBeSaved();
    TicketInteractionEntity ticketInteractionSaved = this.ticketInteractionRepository.save(ticketInteractionToBeSaved);

    List<TicketInteractionEntity> ticketInteractions = this.ticketInteractionRepository.findAll();

    Assertions.assertThat(ticketInteractions)
        .isNotEmpty()
        .hasSize(1)
        .contains(ticketInteractionSaved);
  }

  @Test
  @DisplayName("findByTicket returns ticket interactions by ticket when successful")
  void findByTicket_ReturnsTicketInteraction_WhenSuccessful() {
    TicketInteractionEntity ticketInteractionToBeSaved = TicketInteractionCreator.createTicketToBeSaved();
    TicketInteractionEntity ticketInteractionSaved = this.ticketInteractionRepository.save(ticketInteractionToBeSaved);

    List<TicketInteractionEntity> ticketInteractionsFound = this.ticketInteractionRepository
        .findByTicket(TicketCreator.createValidTicket());

    Assertions.assertThat(ticketInteractionsFound)
        .isNotEmpty()
        .hasSize(1)
        .contains(ticketInteractionSaved);
  }

  @Test
  @DisplayName("update ticket interaction status and message when successful")
  void update_TicketInteractionStatusAndMessage_WhenSuccessful() {
    TicketInteractionEntity ticketInteractionToBeSaved = TicketInteractionCreator.createTicketToBeSaved();
    TicketInteractionEntity ticketInteractionSaved = this.ticketInteractionRepository.save(ticketInteractionToBeSaved);

    ticketInteractionSaved.setMessage(TicketInteractionCreator.createValidUpdateTicket().getMessage());
    ticketInteractionSaved.setStatus(TicketStatus.IN_PROGRESS);

    TicketInteractionEntity ticketInteractionUpdated = this.ticketInteractionRepository.save(ticketInteractionSaved);

    Assertions.assertThat(ticketInteractionUpdated)
        .isNotNull()
        .extracting(TicketInteractionEntity::getStatus, TicketInteractionEntity::getMessage)
        .containsExactly(TicketStatus.IN_PROGRESS, TicketInteractionCreator.createValidUpdateTicket().getMessage());
  }
}
