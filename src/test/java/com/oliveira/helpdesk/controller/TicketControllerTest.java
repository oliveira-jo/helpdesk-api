package com.oliveira.helpdesk.controller;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oliveira.helpdesk.domain.Ticket;
import com.oliveira.helpdesk.domain.TicketInteraction;
import com.oliveira.helpdesk.dto.StatusResponseDto;
import com.oliveira.helpdesk.dto.TicketDto;
import com.oliveira.helpdesk.dto.TicketInteractionDto;
import com.oliveira.helpdesk.dto.UpdateTicketDto;
import com.oliveira.helpdesk.enums.TicketStatus;
import com.oliveira.helpdesk.mapper.TicketMapper;
import com.oliveira.helpdesk.service.TicketService;
import com.oliveira.helpdesk.utils.TicketCreator;

@DisplayName("Ticket Controller Test")
@ExtendWith(SpringExtension.class)
public class TicketControllerTest {

        @InjectMocks
        private TicketController ticketController;

        @Mock
        private TicketService ticketServiceMock;

        @Mock
        private TicketMapper ticketMapperMock;

        @Mock
        private Authentication authenticationMock;

        @BeforeEach
        void setUp() {

                BDDMockito.when(this.ticketServiceMock.createTicket(
                                ArgumentMatchers.any(),
                                ArgumentMatchers.any()))
                                .thenReturn(TicketCreator.createValidTicket());

                BDDMockito.when(ticketServiceMock.updateTicket(ArgumentMatchers.any(UUID.class),
                                ArgumentMatchers.any(UpdateTicketDto.class),
                                ArgumentMatchers.any(Authentication.class)))
                                .thenReturn(TicketCreator.createValidUpdateTicket());

                BDDMockito.when(ticketServiceMock.getById(
                                ArgumentMatchers.any(UUID.class),
                                ArgumentMatchers.any(Authentication.class)))
                                .thenReturn(TicketCreator.createValidTicket());

                BDDMockito.when(ticketServiceMock.listAll(ArgumentMatchers.any(Authentication.class)))
                                .thenReturn(List.of(TicketCreator.createValidTicket()));

                BDDMockito.when(ticketMapperMock.toDto(ArgumentMatchers.<List<Ticket>>any()))
                                .thenReturn(List.of(TicketCreator.createTicketResponseDto()));

                BDDMockito.willDoNothing().given(ticketServiceMock).delete(ArgumentMatchers.any(UUID.class),
                                ArgumentMatchers.any(Authentication.class));

                BDDMockito.when(this.ticketMapperMock.toDto(ArgumentMatchers.any(Ticket.class)))
                                .thenReturn(TicketCreator.createTicketResponseDto());

        }

        @Test
        @DisplayName("create Ticket and returns TicketDTO WhenSuccessfull")
        void createTicket_ReturnsTicketDTO_WhenSuccessfull() {

                TicketDto response = this.ticketController
                                .createTicket(TicketCreator.createTicketRequestDto(), authenticationMock).getBody();

                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response.id()).isEqualTo(TicketCreator.createValidTicket().getId());
                Assertions.assertThat(response.status()).isEqualTo(TicketStatus.OPEN);
                Assertions.assertThat(response.subject())
                                .isEqualTo(TicketCreator.createValidTicket().getSubject());
                Assertions.assertThat(response.description())
                                .isEqualTo(TicketCreator.createValidTicket().getDescription());
                Assertions.assertThat(response.createdAt())
                                .isEqualTo(TicketCreator.createValidTicket().getCreatedAt());
        }

        @Test
        @DisplayName(" update ticket and returns TicketDTO WhenSuccessfull")
        void update_ReturnsTicketDTO_WhenSuccessfull() {

                BDDMockito.when(this.ticketMapperMock.toDto(ArgumentMatchers.any(Ticket.class)))
                                .thenReturn(TicketCreator.createUpdateTicketDtoUpdated());

                TicketDto response = this.ticketController.update(TicketCreator.createValidUpdateTicket().getId(),
                                TicketCreator.createUpdateTicketDto(), authenticationMock).getBody();

                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response.id()).isEqualTo(TicketCreator.createValidUpdateTicket().getId());
                Assertions.assertThat(response.status()).isEqualTo(TicketStatus.OPEN);
                Assertions.assertThat(response.subject())
                                .isEqualTo(TicketCreator.createValidUpdateTicket().getSubject());
                Assertions.assertThat(response.description())
                                .isEqualTo(TicketCreator.createValidUpdateTicket().getDescription());
                Assertions.assertThat(response.createdAt())
                                .isEqualTo(TicketCreator.createValidUpdateTicket().getCreatedAt());

        }

        @Test
        @DisplayName("findById a ticket by a provide id and returns a ticket WhenSuccessfull")
        void findById_ReturnsTicket_WhenSuccessfull() {

                UUID expectedId = TicketCreator.createValidTicketEntity().getId();

                ResponseEntity<TicketDto> response = this.ticketController
                                .getById(TicketCreator.createValidTicketEntity().getId(), authenticationMock);

                Assertions.assertThat(response.getBody()).isNotNull();
                Assertions.assertThat(response.getBody().id()).isEqualTo(expectedId);

        }

        @Test
        @DisplayName("findAllTickets returns a list of Tickets WhenSuccessfull for admin")
        void findAllUsers_ReturnsListOfTickets_WhenSuccessfull() {

                UUID expectedId = TicketCreator.createValidTicketEntity().getId();

                ResponseEntity<List<TicketDto>> response = this.ticketController.listAllTickets(authenticationMock);

                Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty().hasSize(1);
                Assertions.assertThat(response.getBody().get(0).id()).isEqualTo(expectedId);

        }

        @Test
        @DisplayName("delete returns void if tiket was deleted WhenSuccessfull")
        void delete_ReturnsVoid_WhenSuccessfull() {

                Assertions.assertThatCode(
                                () -> ticketController.deleteTichet(
                                                ArgumentMatchers.any(UUID.class),
                                                ArgumentMatchers.any(Authentication.class)))
                                .doesNotThrowAnyException();

                ResponseEntity<Void> response = this.ticketController.deleteTichet(ArgumentMatchers.any(UUID.class),
                                ArgumentMatchers.any(Authentication.class));

                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        }

        @Test
        @DisplayName("numberOfStatus returns StatusResponseDTO When Successful")
        void numberOfStatus_returnsStatusResponseDto_whenSuccessfull() {

                BDDMockito.when(ticketServiceMock.numberOfStatus(ArgumentMatchers.any(Authentication.class)))
                                .thenReturn(TicketCreator.createStatusResponseDto());

                StatusResponseDto response = this.ticketController.numberOfStatus(authenticationMock).getBody();

                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response.nOpenTickets()).isEqualTo(1);
                Assertions.assertThat(response.nInProgressTickets()).isEqualTo(1);
                Assertions.assertThat(response.nAwaitingCustomerTickets()).isEqualTo(1);
                Assertions.assertThat(response.nResolverdTickets()).isEqualTo(1);
                Assertions.assertThat(response.nCancelledTickets()).isEqualTo(1);

        }

        @Test
        @DisplayName("getInteractionsByTicketId returns List of TicketInteractionDTO When Successful")
        void getInteractionsByTicketId_ReturnsListOfTicketInteractionDto_WhenSuccessfull() {

                BDDMockito.when(ticketServiceMock.getInteractionsByTicketId(
                                ArgumentMatchers.any(UUID.class),
                                ArgumentMatchers.any(Authentication.class)))
                                .thenReturn(TicketCreator.createListTicketInteractions());

                BDDMockito.when(ticketMapperMock
                                .toTicketInteractionsDto(ArgumentMatchers.<List<TicketInteraction>>any()))
                                .thenReturn(TicketCreator.createListTicketInteractionsDto());

                List<TicketInteractionDto> response = this.ticketController.getInteractionsByTicketId(
                                TicketCreator.createValidTicketEntity().getId(), authenticationMock).getBody();

                Assertions.assertThat(response).isNotNull().isNotEmpty();
                Assertions.assertThat(response.get(0).message())
                                .isEqualTo(TicketCreator.createListTicketInteractions().get(0).getMessage());
        }

}
