package com.oliveira.helpdesk.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.entity.UserEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {

  List<TicketEntity> findByCreatedBy(Optional<UserEntity> userEntity);

}
