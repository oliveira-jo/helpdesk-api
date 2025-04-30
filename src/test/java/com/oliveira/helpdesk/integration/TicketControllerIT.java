package com.oliveira.helpdesk.integration;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.oliveira.helpdesk.dto.AuthResponseDto;
import com.oliveira.helpdesk.dto.CreateTicketDto;
import com.oliveira.helpdesk.dto.CreateTicketInteractionDto;
import com.oliveira.helpdesk.dto.LoginRequestDto;
import com.oliveira.helpdesk.dto.TicketDto;
import com.oliveira.helpdesk.dto.TicketInteractionDto;
import com.oliveira.helpdesk.entity.TicketEntity;
import com.oliveira.helpdesk.entity.TicketInteractionEntity;
import com.oliveira.helpdesk.entity.UserEntity;
import com.oliveira.helpdesk.enums.TicketStatus;
import com.oliveira.helpdesk.enums.UserRole;
import com.oliveira.helpdesk.repository.TicketInteractionRepository;
import com.oliveira.helpdesk.repository.TicketRepository;
import com.oliveira.helpdesk.repository.UserRepository;
import com.oliveira.helpdesk.utils.TicketCreator;
import com.oliveira.helpdesk.utils.UserCreator;

@DisplayName("Integration tests for Ticket Controller")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketControllerIT {

  @Autowired
  private TestRestTemplate testeRestTemplate;

  @LocalServerPort
  private int port;

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TicketInteractionRepository ticketInteractionRepository;

  public String getBaseUrl() {
    return "http://localhost:" + port + "/api/v1/tickets";
  }

  public String getBaseUrlAuth() {
    return "http://localhost:" + port + "/api/v1/auth/login";
  }

  @Test
  @DisplayName("This method create a ticket and return a TicketDto WhenSuccessful")
  void create_ReturnTicketDto_WhenSuccessful() {

    CreateTicketDto savedTicket = TicketCreator.createTicketRequestDto();

    HttpEntity<CreateTicketDto> request = new HttpEntity<>(savedTicket, this.getHeaderWithToken("admin", "password"));

    ResponseEntity<TicketDto> response = testeRestTemplate.postForEntity(getBaseUrl(), request, TicketDto.class);

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().id()).isNotNull();

  }

  @Test
  @DisplayName("This Method update a ticket and return a TicketDto WhenSuccessful")
  void update_ReturnTicketDto_WhenSuccessful() {

    String url = getBaseUrl() + "/{id}";

    TicketEntity savedTicket = this.ticketRepository.save(TicketCreator.createTicketEntityToBeSaved());

    savedTicket.setSubject("new subject");
    savedTicket.setStatus(TicketStatus.IN_PROGRESS);

    HttpEntity<TicketEntity> request = new HttpEntity<>(savedTicket, this.getHeaderWithToken("admin", "password"));

    ResponseEntity<TicketDto> response = testeRestTemplate.exchange(
        url, HttpMethod.PUT, request, TicketDto.class,
        savedTicket.getId());

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody().subject()).isEqualTo(savedTicket.getSubject());
    Assertions.assertThat(response.getBody().status()).isEqualTo(TicketStatus.IN_PROGRESS);

  }

  @Test
  @DisplayName("This method delete a ticket by a provide id and return http status ok WhenSuccessful")
  void delete_ReturnHttpStatusOk_WhenSuccessful() {

    String url = getBaseUrl() + "/{id}";

    TicketEntity savedTicket = this.ticketRepository.save(TicketCreator.createTicketEntityToBeSaved());

    HttpEntity<Void> requestEntity = new HttpEntity<>(this.getHeaderWithToken("admin", "password"));

    ResponseEntity<Void> response = testeRestTemplate.exchange(
        url, HttpMethod.DELETE, requestEntity, Void.class,
        savedTicket.getId());

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

  }

  @Test
  @DisplayName("This method create a ticket interaction and return a TicketDto WhenSuccessful")
  void createTicketInteraction_ReturnTicketDto_WhenSuccessful() {

    // CREATE USER
    UserEntity userEntity = UserCreator.createUserToBeSaved();
    userEntity.setRole(UserRole.ADMIN);
    UserEntity savedUser = this.userRepository.save(userEntity);

    // CREATE TICKET
    TicketEntity ticketEntity = TicketCreator.createTicketEntityToBeSaved();
    TicketEntity savedTicket = this.ticketRepository.save(ticketEntity);

    savedTicket.setCreatedBy(savedUser);
    savedTicket.setStatus(TicketStatus.IN_PROGRESS);
    this.ticketRepository.save(savedTicket);

    UUID ticketId = savedTicket.getId();

    // CREATE TICKET INTERACTION
    CreateTicketInteractionDto ticketInteractionDto = TicketCreator.createTicketInteractionDto();

    // MOUNT THE REQUEST WITH TOKEN AND CREATE TICKET INTERACTION
    HttpEntity<CreateTicketInteractionDto> request = new HttpEntity<>(ticketInteractionDto,
        this.getHeaderWithToken("admin", "password"));

    // SEND THE REQUEST
    String url = getBaseUrl() + "/" + ticketId + "/interaction";
    ResponseEntity<TicketDto> response = testeRestTemplate.postForEntity(url, request, TicketDto.class);

    // VALIDATE THE RESPONSE
    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(response.getBody()).isNotNull();
    Assertions.assertThat(response.getBody().id()).isEqualTo(savedTicket.getId());

  }

  @Test
  @DisplayName("This method list all tickets and return a list of TicketsDto WhenSuccessful")
  void listAllTickets_ReturnUserDto_WhenSuccessful() {

    TicketEntity savedTicket = this.ticketRepository.save(TicketCreator.createTicketEntityToBeSaved());

    HttpEntity<Void> requestEntity = new HttpEntity<>(this.getHeaderWithToken("admin", "password"));

    List<TicketDto> response = testeRestTemplate.exchange(getBaseUrl(),
        HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<TicketDto>>() {
        }).getBody();

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.size()).isEqualTo(1);
    Assertions.assertThat(response.get(0).id()).isNotNull().isEqualTo(savedTicket.getId());

  }

  @Test
  @DisplayName("This method get a ticket by a provide id and return a TicketDto WhenSuccessful")
  void getById_ReturnUserDto_WhenSuccessful() {

    TicketEntity savedTicket = this.ticketRepository.save(TicketCreator.createTicketEntityToBeSaved());
    UUID exepctedId = savedTicket.getId();

    HttpEntity<Void> requestEntity = new HttpEntity<>(this.getHeaderWithToken("admin", "password"));

    String url = getBaseUrl() + "/" + exepctedId;

    TicketDto response = testeRestTemplate.exchange(url,
        HttpMethod.GET, requestEntity, TicketDto.class).getBody();

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.id()).isNotNull().isEqualTo(exepctedId);

  }

  @Test
  @DisplayName("This method get a ticket's interactions by a provide ticket id and return a list of TicletsInteractionDto WhenSuccessful")
  void getInteractionsByTicketId_ReturnUserDto_WhenSuccessful() {

    TicketEntity savedTicket = this.ticketRepository.save(TicketCreator.createTicketEntityToBeSaved());
    UUID ticketId = savedTicket.getId();

    TicketInteractionEntity newTicketInteractionEntity = TicketCreator.createValidTicketInteractionEntity();
    newTicketInteractionEntity.setTicket(savedTicket);

    this.ticketInteractionRepository.save(newTicketInteractionEntity);

    HttpEntity<Void> requestEntity = new HttpEntity<>(this.getHeaderWithToken("admin", "password"));

    String url = getBaseUrl() + "/" + ticketId + "/interactions";

    List<TicketInteractionDto> response = testeRestTemplate.exchange(url,
        HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<TicketInteractionDto>>() {
        }).getBody();

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.size()).isEqualTo(1);
    Assertions.assertThat(response.get(0).id()).isNotNull().isEqualTo(newTicketInteractionEntity.getId());

  }

  HttpHeaders getHeaderWithToken(String username, String password) {

    String url = getBaseUrlAuth();

    LoginRequestDto request = new LoginRequestDto(username, password);

    ResponseEntity<AuthResponseDto> response = testeRestTemplate.postForEntity(url, request, AuthResponseDto.class);

    HttpHeaders headers = new HttpHeaders();

    headers.set("Authorization", "Bearer " + response.getBody().accessToken());

    return headers;

  }

}
