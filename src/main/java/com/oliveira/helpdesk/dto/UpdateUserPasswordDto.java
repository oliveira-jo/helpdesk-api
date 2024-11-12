package com.oliveira.helpdesk.dto;

public record UpdateUserPasswordDto(
    String currentPassword,
    String newPassword) {

}
