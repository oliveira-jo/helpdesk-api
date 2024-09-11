package com.oliveira.helpdesk.dto;

import java.util.List;

public record CreateTicketInteractionDto(
    String message,
    // UUID userId,
    List<AttachmentDto> attachments) {

}
