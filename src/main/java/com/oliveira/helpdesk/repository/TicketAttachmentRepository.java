package com.oliveira.helpdesk.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oliveira.helpdesk.entity.TicketAttachmentEntity;

@Repository
public interface TicketAttachmentRepository extends JpaRepository<TicketAttachmentEntity, UUID> {

}
