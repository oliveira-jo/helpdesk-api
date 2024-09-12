package com.oliveira.helpdesk.dto;

public record UpdateUserDto(
    String username,
    String password,
    String name,
    String email,
    Boolean active) {

}
