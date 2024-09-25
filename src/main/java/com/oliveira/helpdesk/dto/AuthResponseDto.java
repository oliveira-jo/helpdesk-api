package com.oliveira.helpdesk.dto;

public record AuthResponseDto(
    String username,
    String accessToken,
    Long expiresIn) {

}
