package com.oliveira.helpdesk.dto;

public record CreateUserDto(
        String username,
        String password,
        String name,
        String email) {

}
