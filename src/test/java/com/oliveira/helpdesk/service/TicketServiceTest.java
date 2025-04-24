package com.oliveira.helpdesk.service;

import java.util.List;
import java.util.Optional;

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

import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.oliveira.helpdesk.domain.Ticket;
import com.oliveira.helpdesk.domain.TicketInteraction;
import com.oliveira.helpdesk.dto.CreateTicketInteractionDto;
import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.enums.TicketStatus;
import com.oliveira.helpdesk.mapper.TicketMapper;
import com.oliveira.helpdesk.repository.TicketInteractionRepository;
import com.oliveira.helpdesk.repository.TicketRepository;
import com.oliveira.helpdesk.repository.UserRepository;
import com.oliveira.helpdesk.security.CustomUserDetails;
import com.oliveira.helpdesk.utils.TicketCreator;
import com.oliveira.helpdesk.utils.TicketInteractionCreator;
import com.oliveira.helpdesk.utils.UserCreator;

@DisplayName("Ticket Service Test")
@ExtendWith(SpringExtension.class)
public class TicketServiceTest {

        @InjectMocks
        private TicketService ticketService;

        @Mock
        private UserRepository userRepositoryMock;

        @Mock
        private TicketRepository ticketRepositoryMock;

        @Mock
        TicketInteractionRepository ticketInteractionRepositoryMock;

        @Mock
        private TicketMapper ticketMapperMock;

        @Mock
        private Authentication authenticationMock;

        @BeforeEach
        void setUp() {

                BDDMockito.when(userRepositoryMock.findByUsername(ArgumentMatchers.any()))
                                .thenReturn(Optional.of(UserCreator.createValidUser()));

                BDDMockito.when(ticketMapperMock.toDomain(ArgumentMatchers.any(TicketEntity.class)))
                                .thenReturn(TicketCreator.createValidTicket());

                BDDMockito.when(ticketRepositoryMock.save(ArgumentMatchers.any(TicketEntity.class)))
                                .thenReturn(TicketCreator.createValidTicketEntity());

                BDDMockito.when(ticketRepositoryMock.findById(ArgumentMatchers.any()))
                                .thenReturn(Optional.of(TicketCreator.createValidTicketEntity()));

                BDDMockito.when(ticketMapperMock.toDomain(ArgumentMatchers.any(CreateTicketInteractionDto.class)))
                                .thenReturn(TicketCreator.createValidTicketInteraction());

                BDDMockito.when(ticketMapperMock.toDomain(ArgumentMatchers.<List<TicketEntity>>any()))
                                .thenReturn(List.of(TicketCreator.createValidTicket()));

                BDDMockito.when(ticketInteractionRepositoryMock.findByTicket(ArgumentMatchers.any()))
                                .thenReturn(List.of(TicketInteractionCreator.createValidTicket()));

                BDDMockito.when(ticketMapperMock.toInteractionsDomain(ArgumentMatchers.any()))
                                .thenReturn(List.of(TicketInteractionCreator.createValidTicketInteraction()));

        }

        @Test
        @DisplayName("create Ticket and returns TicketDTO WhenSuccessfull")
        void createTicket_ReturnsTicketDTO_WhenSuccessfull() {

                Ticket response = this.ticketService.createTicket(TicketCreator.createTicketRequestDto(),
                                UserCreator.createValidUser().getUsername());

                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response.getId()).isEqualTo(TicketCreator.createValidTicketEntity().getId());
                Assertions.assertThat(response.getSubject())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getSubject());
                Assertions.assertThat(response.getDescription())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getDescription());
                Assertions.assertThat(response.getCreatedAt())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getCreatedAt());
                Assertions.assertThat(response.getCreatedBy())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getCreatedBy());
                Assertions.assertThat(response.getStatus()).isEqualTo(TicketStatus.OPEN);

        }

        @Test
        @DisplayName("ticketInteraction save a interaction and returns Ticket WhenSuccessfull")
        void ticketInteraction_ReturnsTicket_WhenSuccessfull() {

                BDDMockito.when(ticketInteractionRepositoryMock.save(ArgumentMatchers.any()))
                                .thenReturn(TicketInteractionCreator.createValidTicket());

                Ticket response = this.ticketService.ticketInteraction(
                                TicketInteractionCreator.createValidTicket().getId(),
                                TicketCreator.createTicketInteractionDto(),
                                UserCreator.createValidUser().getUsername());

                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response.getId())
                                .isEqualTo(TicketInteractionCreator.createValidTicket().getTicket().getId());

        }

        @Test
        @DisplayName(" update ticket and returns TicketDTO WhenSuccessfull")
        void update_ReturnsTicketDTO_WhenSuccessfull() {

                BDDMockito.when(ticketRepositoryMock.save(ArgumentMatchers.any(TicketEntity.class)))
                                .thenReturn(TicketCreator.createValidUpdateTicketEntity());

                BDDMockito.when(ticketMapperMock.toDomain(ArgumentMatchers.any(TicketEntity.class)))
                                .thenReturn(TicketCreator.createValidUpdateTicket());

                Ticket response = this.ticketService.updateTicket(
                                TicketCreator.createValidUpdateTicket().getId(), TicketCreator.createUpdateTicketDto(),
                                authenticationMock);

                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response.getId())
                                .isEqualTo(TicketCreator.createValidUpdateTicket().getId());
                Assertions.assertThat(response.getSubject())
                                .isEqualTo(TicketCreator.createValidUpdateTicket().getSubject());
                Assertions.assertThat(response.getDescription())
                                .isEqualTo(TicketCreator.createValidUpdateTicket().getDescription());
                Assertions.assertThat(response.getCreatedAt())
                                .isEqualTo(TicketCreator.createValidUpdateTicket().getCreatedAt());
                Assertions.assertThat(response.getCreatedBy())
                                .isEqualTo(TicketCreator.createValidUpdateTicket().getCreatedBy());
                Assertions.assertThat(response.getStatus())
                                .isEqualTo(TicketCreator.createValidUpdateTicket().getStatus());

        }

        @Test
        @DisplayName("listAll returns List of Tickets WhenSuccessfull")
        void listAll_ReturnsTicketsList_WhenSuccessfull() {

                CustomUserDetails userDetails = new CustomUserDetails(UserCreator.createValidUpdateUser());

                BDDMockito.when(authenticationMock.getPrincipal()).thenReturn(userDetails);

                List<Ticket> response = this.ticketService.listAll(authenticationMock);
                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response).isNotEmpty();
                Assertions.assertThat(response.size()).isEqualTo(1);
                Assertions.assertThat(response.get(0).getId())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getId());

        }

        @Test
        @DisplayName("getById returns if exist a Ticket WhenSuccessfull")
        void getById_ReturnTicket_WhenSuccessfull() {

                Ticket response = this.ticketService.getById(TicketCreator.createValidTicket().getId(),
                                authenticationMock);

                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response.getId())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getId());
                Assertions.assertThat(response.getSubject())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getSubject());
                Assertions.assertThat(response.getDescription())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getDescription());
                Assertions.assertThat(response.getCreatedAt())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getCreatedAt());
                Assertions.assertThat(response.getCreatedBy())
                                .isEqualTo(TicketCreator.createValidTicketEntity().getCreatedBy());
                Assertions.assertThat(response.getStatus()).isEqualTo(TicketStatus.OPEN);

        }

        @Test
        @DisplayName("getInteractionsByTicketId returns if exist a Ticket WhenSuccessfull")
        void getInteractionsByTicketId_ReturnTicketInteractionList_WhenSuccessfull() {

                List<TicketInteraction> response = this.ticketService.getInteractionsByTicketId(
                                TicketCreator.createValidTicket().getId(), authenticationMock);

                Assertions.assertThat(response).isNotNull();
                Assertions.assertThat(response.size()).isEqualTo(1);
                Assertions.assertThat(response.get(0).getId())
                                .isEqualTo(TicketInteractionCreator.createValidTicket().getId());
                Assertions.assertThat(response.get(0).getTicket().getId())
                                .isEqualTo(TicketCreator.createValidTicket().getId());

        }

        @Test
        @DisplayName(" delete Ticket and doNothing WhenSuccessfull")
        void delete_doNothing_WhenSuccessfull() {

                Assertions.assertThatCode(() -> this.ticketService.delete(
                                TicketCreator.createValidTicket().getId(), authenticationMock))
                                .doesNotThrowAnyException();

                BDDMockito.verify(ticketRepositoryMock, BDDMockito.times(1))
                                .delete(ArgumentMatchers.any(TicketEntity.class));

        }

}
