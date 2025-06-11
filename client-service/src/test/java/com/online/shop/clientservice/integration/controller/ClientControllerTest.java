package com.online.shop.clientservice.integration.controller;

import com.online.shop.clientservice.domain.entity.Client;
import com.online.shop.clientservice.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static com.online.shop.clientservice.util.StringConstants.API_URL_V1;
import static com.online.shop.clientservice.util.StringConstants.API_URL_V1_NO_SLASH;
import static com.online.shop.clientservice.util.StringConstants.NON_EXISTENT_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    private final MockMvc mockMvc;

    private final ClientRepository clientRepository;

    private static Client client;

    @Autowired
    public ClientControllerTest(
            ClientRepository clientRepository,
            MockMvc mockMvc
    ) {
        this.clientRepository = clientRepository;
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void setUp() {
        client = clientRepository.save(
                Client.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@email.com")
                .address("1234 Elm Street Apt. 567 Springfield, IL 62701 USA")
                .build()
        );
    }

    @Nested
    @DisplayName("GET Method Calls")
    class GetMethodCalls {
        @Test
        @SneakyThrows
        public void testThatGetClientsReturnsClientsAndHttpStatus200() {
            mockMvc.perform(get(API_URL_V1_NO_SLASH)
                            .param("offset", "0")
                            .param("size", "3"))
                    .andExpect(jsonPath("$.content.[0].name").value(client.getName()))
                    .andExpect(jsonPath("$.content.[0].surname").value(client.getSurname()))
                    .andExpect(jsonPath("$.content.[0].email").value(client.getEmail()))
                    .andExpect(jsonPath("$.content.[0].created_at").value(client.getCreatedAt()))
                    .andExpect(jsonPath("$.first").value(true))
                    .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                    .andExpect(jsonPath("$.pageable.pageSize").value(3))
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        public void testThatGetClientByIdReturnsReturnsClientAndHttpStatus200() {
            mockMvc.perform(get(API_URL_V1 + client.getId()))
                    .andExpect(jsonPath("$.name").value(client.getName()))
                    .andExpect(jsonPath("$.surname").value(client.getSurname()))
                    .andExpect(jsonPath("$.email").value(client.getEmail()))
                    .andExpect(jsonPath("$.created_at").value(client.getCreatedAt()))
                    .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        public void testThatGetClientByIdForNonExistentClientReturnsHttpStatus404() {
            mockMvc.perform(get(API_URL_V1 + UUID.fromString(NON_EXISTENT_ID)))
                    .andExpect(jsonPath("$.title").value("Client not found"))
                    .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(jsonPath("$.detail").value("Client with given Id doesn't exist"))
                    .andExpect(jsonPath("$.instance").value(API_URL_V1 + NON_EXISTENT_ID))
                    .andExpect(status().isNotFound());
        }

        @Test
        @SneakyThrows
        public void testThatGetClientByIdForInvalidIdTypeReturnsHttpStatus400() {
            mockMvc.perform(get(API_URL_V1 + "Q"))
                    .andExpect(jsonPath("$.title").value("Bad request"))
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.instance").value(API_URL_V1 + "Q"))
                    .andExpect(status().isBadRequest());
        }
    }
}
