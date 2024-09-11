package com.oliveira.helpdesk.dto;

import java.util.Date;
import java.util.UUID;

import com.oliveira.helpdesk.enums.UserRole;

public record UserDto(
                UUID id,
                String username,
                String name,
                String email,
                boolean active,
                UserRole role,
                Date createdAt) {
}