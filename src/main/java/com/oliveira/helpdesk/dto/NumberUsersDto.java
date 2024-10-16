package com.oliveira.helpdesk.dto;

public record NumberUsersDto(
    Integer nAdmins,
    Integer nAttendents,
    Integer nDefaultUsers) {
}
