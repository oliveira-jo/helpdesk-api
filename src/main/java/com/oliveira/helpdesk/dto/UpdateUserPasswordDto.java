package com.oliveira.helpdesk.dto;

public record UpdateUserPasswordDto(
                String password,
                String oldPassword) {

}
