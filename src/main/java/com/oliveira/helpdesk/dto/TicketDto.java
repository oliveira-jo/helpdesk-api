package com.oliveira.helpdesk.dto;

import java.util.Date;
import java.util.UUID;

import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.TicketStatus;

public record TicketDto(
        UUID id,
        UserDto supportUser,
        String subject,
        String description,
        TicketStatus status,
        UserDto createdBy,
        Date createdAt,
        UserEntity updatedBy,
        Date updateAt) {

}
