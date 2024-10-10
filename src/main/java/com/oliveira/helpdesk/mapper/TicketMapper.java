package com.oliveira.helpdesk.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.oliveira.helpdesk.domain.Ticket;
import com.oliveira.helpdesk.domain.TicketInteraction;
import com.oliveira.helpdesk.dto.CreateTicketDto;
import com.oliveira.helpdesk.dto.CreateTicketInteractionDto;
import com.oliveira.helpdesk.dto.TicketDto;
import com.oliveira.helpdesk.dto.TicketInteractionDto;
import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.entity.TicketInteractionEntity;

// unmappedSourcePolicy = ReportingPolicy.IGNORE -> if dont's exist in destiny, just ignore
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

  // For diferent name of variables
  // @Mapping(source = "userId", target = "sentByUser")

  @Mapping(target = "createdByUserId", ignore = true)
  @Mapping(target = "attachments", ignore = true)
  Ticket toDomain(TicketEntity entity);

  TicketDto toDto(Ticket damin);

  @Mapping(target = "supportUser", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updateAt", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  TicketEntity toEntity(Ticket domain);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdByUserId", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "supportUser", ignore = true)
  @Mapping(target = "updateAt", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  Ticket toDomain(CreateTicketDto request);

  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "sentByUser", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "ticket", ignore = true)
  @Mapping(target = "ticketId", ignore = true)
  @Mapping(target = "updateAt", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  TicketInteraction toDomain(CreateTicketInteractionDto dto);

  List<Ticket> toDomain(List<TicketEntity> entities);

  List<TicketDto> toDto(List<Ticket> domains);

  List<TicketInteractionDto> toTicketInteractionsDto(List<TicketInteraction> domains);

  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "ticketId", ignore = true)
  @Mapping(target = "attachments", ignore = true)
  List<TicketInteraction> toInteractionsDomain(List<TicketInteractionEntity> byTicket);
}
