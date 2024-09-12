package com.oliveira.helpdesk.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oliveira.helpdesk.domain.Attachment;
import com.oliveira.helpdesk.domain.Ticket;
import com.oliveira.helpdesk.domain.TicketInteraction;
import com.oliveira.helpdesk.dto.CreateTicketDto;
import com.oliveira.helpdesk.entity.TicketAttachmentEntity;
import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.entity.TicketInteractionEntity;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.TicketStatus;
import com.oliveira.helpdesk.enums.UserRole;
import com.oliveira.helpdesk.exception.BusinessException;
import com.oliveira.helpdesk.exception.ObjectNotFoundException;
import com.oliveira.helpdesk.mapper.TicketMapper;
import com.oliveira.helpdesk.repository.TicketAttachmentRepository;
import com.oliveira.helpdesk.repository.TicketInteractionRepository;
import com.oliveira.helpdesk.repository.TicketRepository;
import com.oliveira.helpdesk.repository.UserRepository;
import com.oliveira.helpdesk.security.CustomUserDetails;
import com.oliveira.helpdesk.utils.FileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TicketService {

  private final UserRepository userRepository;

  private final TicketRepository ticketRepository;

  private final TicketInteractionRepository ticketInteractionRepository;

  private final TicketAttachmentRepository ticketAttachmentRepository;

  private final TicketMapper mapper;

  @Value("${helpdesk.attachments-folder}")
  private String attachmentFolder;

  /*
   * 
   * In @Transactional to JPA, If exist any exception during the execution in this
   * method
   * all datas will not save in the Data Base
   * 
   */
  @Transactional
  public Ticket createTicket(Ticket newTicket, String username) {

    UserEntity createdByUser = userRepository.findByUsername(username).orElse(null);
    if (createdByUser == null) {
      throw new ObjectNotFoundException("User not found with provided id.");
    }

    TicketEntity entity = mapper.toEntity(newTicket);
    entity.setCreatedBy(createdByUser);
    entity.setStatus(TicketStatus.OPEN);
    entity.setCreatedAt(new Date());
    entity = ticketRepository.save(entity);

    /*
     * Salve attachment in local disck
     * File and in the DataBase only the name e informations
     * exists attachments in the tikect
     */
    if (newTicket.getAttachments() != null && !newTicket.getAttachments().isEmpty()) {
      for (Attachment attachment : newTicket.getAttachments()) {
        // Saving attachments name in the Data Base - Base64
        TicketAttachmentEntity ticketAttachmentEntity = new TicketAttachmentEntity();
        ticketAttachmentEntity.setTicket(entity);
        ticketAttachmentEntity.setCreatedBy(createdByUser);
        ticketAttachmentEntity.setCreatedAt(new Date());
        ticketAttachmentEntity.setFilename(attachment.filename());
        ticketAttachmentEntity = ticketAttachmentRepository.save(ticketAttachmentEntity);
        // Convert to byte arrey to save in local disk
        saveFileToDisk(ticketAttachmentEntity, attachment.content());
      }
    }

    return mapper.toDomain(entity);

  }

  public Ticket ticketInteraction(TicketInteraction domain, String username) {

    UserEntity user = userRepository.findByUsername(username).orElse(null);
    if (user == null) {
      throw new BusinessException("User not found with provided id");
    }

    TicketEntity ticket = ticketRepository.findById(domain.getTicketId()).orElse(null);
    if (ticket == null) {
      throw new BusinessException("Ticket not found with provided id");
    }

    if (!user.equals(ticket.getCreatedBy()) && user.getRole() != UserRole.ADMIN
        && user.getRole() != UserRole.SUPPORT_ATTENDANT) {
      throw new BusinessException("UNAUNTHORIZED access to this  ticket");
    }

    Date now = new Date();
    TicketStatus status = TicketStatus.IN_PROGRESS;
    if (ticket.getCreatedBy().getId() != user.getId()) {
      ticket.setSupportUser(user);
      status = TicketStatus.AWAITING_CUSTOMER_ANSWER;
    }

    TicketInteractionEntity entity = new TicketInteractionEntity();
    entity.setTicket(ticket);
    entity.setMessage(domain.getMessage());
    entity.setCreatedBy(user);
    entity.setSentByUser(user);
    entity.setCreatedAt(now);
    entity.setStatus(status);
    entity = ticketInteractionRepository.save(entity);

    if (domain.getAttachments() != null && !domain.getAttachments().isEmpty()) {
      for (Attachment attachment : domain.getAttachments()) {
        TicketAttachmentEntity ticketAttachmentEntity = new TicketAttachmentEntity();
        ticketAttachmentEntity.setTicketInteraction(entity);
        ticketAttachmentEntity.setCreatedBy(user);
        ticketAttachmentEntity.setCreatedAt(new Date());
        ticketAttachmentEntity.setFilename(attachment.filename());
        ticketAttachmentEntity = ticketAttachmentRepository.save(ticketAttachmentEntity);
        saveFileToDisk(ticketAttachmentEntity, attachment.content());

      }
    }

    ticket.setUpdateAt(now);
    ticket.setUpdatedBy(user.getId());
    ticket.setStatus(status);
    ticket = ticketRepository.save(ticket);

    return mapper.toDomain(ticket);

  }

  private void saveFileToDisk(TicketAttachmentEntity entity, String content) {
    byte[] attachmentContent = null;
    try {
      attachmentContent = FileUtils.convertBase64ToByteArray(content);
      String fileName = entity.getId() + "."
          + FileUtils.extractFileExtensionFromBase64String(content);

      FileUtils.saveByteArrayToFile(attachmentContent, new File(attachmentFolder +
          fileName));

    } catch (IOException e) {
      throw new BusinessException("Error saving: " + entity.getFilename() + "File" + e);
    }
  }

  public List<Ticket> listAll(Authentication authentication) {

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    Optional<UserEntity> userEntity = userRepository.findByUsername(userDetails.getUsername());

    for (GrantedAuthority authority : userDetails.getAuthorities()) {
      if (authority.getAuthority().equals(("ROLE_ADMIN")) || authority.getAuthority().equals(("SUPPORT_ATTENDANT"))) {
        return mapper.toDomain(ticketRepository.findAll());

      } else if (authority.getAuthority().equals(("ROLE_CUSTOMER"))) {
        return mapper.toDomain(ticketRepository.findByCreatedBy(userEntity));

      }
    }

    return List.of();

  }

  public Ticket getById(UUID ticketId, Authentication authentication) {

    UserEntity user = userRepository.findByUsername(authentication.getName()).orElse(null);
    if (user == null) {
      throw new BusinessException("User not found with provided id");
    }

    TicketEntity ticket = ticketRepository.findById(ticketId).orElse(null);
    if (ticket == null) {
      throw new BusinessException("Ticket not found with provided id");
    }

    if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.SUPPORT_ATTENDANT)) {
      return mapper.toDomain(ticket);

    } else if (user.equals(ticket.getCreatedBy())) {
      return mapper.toDomain(ticket);

    } else {
      throw new BusinessException("Ticket Access UNAUNTHORIZED");

    }

  }

  public List<TicketInteraction> getInteractionsByTicketId(UUID ticketId, Authentication authentication) {

    UserEntity user = userRepository.findByUsername(authentication.getName()).orElse(null);
    if (user == null) {
      throw new BusinessException("User not found with provided id");
    }

    TicketEntity ticket = ticketRepository.findById(ticketId).orElse(null);
    if (ticket == null) {
      throw new BusinessException("Ticket not found with provided id");
    }

    if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.SUPPORT_ATTENDANT)) {
      return mapper.toInteractionsDomain(ticketInteractionRepository.findByTicket(ticket));

    } else if (user.equals(ticket.getCreatedBy())) {
      return mapper.toInteractionsDomain(ticketInteractionRepository.findByTicket(ticket));

    } else {
      throw new BusinessException("Ticket Interaction Access UNAUNTHORIZED");

    }

  }

  // ** REFACTOR **
  public Ticket updateTicket(UUID id, CreateTicketDto data, Authentication authentication) {

    UserEntity userLooged = userRepository.findByUsername(authentication.getName()).orElse(null);
    if (userLooged == null) {
      throw new BusinessException("User not found with provided id OR Unaunthorized");
    }

    TicketEntity ticketEntity = ticketRepository.findById(id).orElse(null);
    if (ticketEntity == null) {
      throw new BusinessException("Ticket not found with provided id");
    }

    if (userLooged.getRole().equals(UserRole.ADMIN) || userLooged.getRole().equals(UserRole.SUPPORT_ATTENDANT)
        || userLooged.equals(ticketEntity.getCreatedBy())) {

      // UPDATE
      if (!data.subject().isEmpty()) {
        ticketEntity.setSubject(data.subject());
      }
      if (!data.description().isEmpty()) {
        ticketEntity.setSubject(data.description());
      }

      // AND ARRAY OF ATTCHMENTS ?
      // ******************************************************************************************

      // UPDATE TICKET ENTITY
      this.ticketRepository.save(ticketEntity);
      return mapper.toDomain(ticketEntity);

    } else {
      throw new BusinessException("Ticket update Access UNAUNTHORIZED");
    }

  }

  public void delete(UUID id, Authentication authentication) {

    UserEntity user = userRepository.findByUsername(authentication.getName()).orElse(null);
    if (user == null) {
      throw new BusinessException("User not found or not authenticated");
    }

    TicketEntity ticket = ticketRepository.findById(id).orElse(null);
    if (ticket == null) {
      throw new BusinessException("Ticket not found with provided id");
    }

    if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.SUPPORT_ATTENDANT)
        || user.equals(ticket.getCreatedBy())) {
      this.ticketRepository.delete(ticket);

    } else {
      throw new BusinessException("Ticket Interaction Access UNAUNTHORIZED");

    }

  }

}
