package com.oliveira.helpdesk.dto;

public record StatusResponseDto(
        Integer nOpenTickets,
        Integer nInProgressTickets,
        Integer nAwaitingCustomerTickets,
        Integer nResolverdTickets,
        Integer nCancelledTickets) {
}
