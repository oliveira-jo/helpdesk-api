package com.oliveira.helpdesk.domain;

import java.util.Date;
import java.util.UUID;

import com.oliveira.helpdesk.enums.UserRole;

public record User(
                UUID id,
                String username,
                String password,
                String name,
                String email,
                boolean active,
                UserRole role,
                Date createdAt) {

}
