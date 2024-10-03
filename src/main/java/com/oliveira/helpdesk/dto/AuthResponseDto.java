package com.oliveira.helpdesk.dto;

import java.util.UUID;

public record AuthResponseDto(
                UUID id,
                String username,
                String accessToken,
                Long expiresIn) {

}
