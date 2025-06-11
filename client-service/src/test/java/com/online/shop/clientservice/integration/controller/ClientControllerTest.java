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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static com.online.shop.clientservice.util.StringConstants.API_URL_V1;
import static com.online.shop.clientservice.util.StringConstants.API_URL_V1_NO_SLASH;
import static com.online.shop.clientservice.util.StringConstants.FULL_UPDATE_JSON;
import static com.online.shop.clientservice.util.StringConstants.INVALID_TYPE_ID;
import static com.online.shop.clientservice.util.StringConstants.NON_EXISTENT_ID;
import static com.online.shop.clientservice.util.StringConstants.PARTIAL_UPDATE_JSON;
import static com.online.shop.clientservice.util.StringConstants.POST_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
            mockMvc.perform(get(API_URL_V1 + INVALID_TYPE_ID))
                    .andExpect(jsonPath("$.title").value("Bad request"))
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.instance").value(API_URL_V1 + INVALID_TYPE_ID))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("POST Method Calls")
    class PostMethodCalls {

        @Test
        @SneakyThrows
        public void testThatPostClientReturnsHttpStatus201() {
            mockMvc.perform(post(API_URL_V1_NO_SLASH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(POST_JSON))
                    .andExpect(jsonPath("$.name").value("John"))
                    .andExpect(jsonPath("$.surname").value("Smith"))
                    .andExpect(jsonPath("$.email").value("john.smith@email.com"))
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    @DisplayName("PUT Method Calls")
    class PutMethodCalls {

        @Test
        @SneakyThrows
        public void testThatPutClientReturnsHttpStatus200() {
            mockMvc.perform(put(API_URL_V1 + client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                            .content(FULL_UPDATE_JSON))
                    .andExpect(jsonPath("$.name").value("Dan"))
                    .andExpect(jsonPath("$.surname").value("Carter"))
                    .andExpect(jsonPath("$.email").value("dan.carter@email.com"))
                    .andExpect(jsonPath("$.created_at").value(client.getCreatedAt()))
                    .andExpect(status().isOk());
        }

    }

    @Nested
    @DisplayName("PATCH Method Calls")
    class PatchMethodCalls {

        @Test
        @SneakyThrows
        public void testThatPatchClientReturnsHttpStatus200() {
            mockMvc.perform(patch(API_URL_V1 + client.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                            .content(PARTIAL_UPDATE_JSON))
                    .andExpect(jsonPath("$.name").value("Andrew"))
                    .andExpect(jsonPath("$.surname").value("Doe"))
                    .andExpect(jsonPath("$.email").value("andrew.doe@email.com"))
                    .andExpect(jsonPath("$.created_at").value(client.getCreatedAt()))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("DELETE Method Calls")
    class DeleteMethodCalls {

        @Test
        @SneakyThrows
        public void testThatDeleteClientByIdReturnsHttpStatus204() {
            mockMvc.perform(delete(API_URL_V1 + client.getId()))
                    .andExpect(status().isNoContent());
        }
    }
}
