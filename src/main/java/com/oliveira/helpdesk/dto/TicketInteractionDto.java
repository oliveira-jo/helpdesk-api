package com.oliveira.helpdesk.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.oliveira.helpdesk.enums.TicketStatus;

public record TicketInteractionDto(
    UUID id,
    String message,
    TicketStatus status,
    List<AttachmentDto> attachments,
    UserDto sentByUser,
    Date createdAt) {
}
