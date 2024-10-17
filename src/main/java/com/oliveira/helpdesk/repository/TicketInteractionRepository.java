package com.oliveira.helpdesk.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.entity.TicketInteractionEntity;

@Repository
public interface TicketInteractionRepository extends JpaRepository<TicketInteractionEntity, UUID> {

  List<TicketInteractionEntity> findByTicket(TicketEntity ticket);

  void deleteByTicket(TicketEntity ticket);

}
