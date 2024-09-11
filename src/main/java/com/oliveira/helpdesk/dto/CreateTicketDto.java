package com.oliveira.helpdesk.dto;

import java.util.List;

public record CreateTicketDto(
        String subject,
        String description,
        // Change the name to mapper ignore this case and I need to set manually what is
        // the user. After will impplements this automation with spring security when
        // I will get the user from the token generated by security passing in the
        // server
        // UUID createdByUserId,
        List<AttachmentDto> attachments) {

}