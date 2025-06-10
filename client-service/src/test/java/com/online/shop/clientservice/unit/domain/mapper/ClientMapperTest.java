package com.online.shop.clientservice.unit.domain.mapper;

import com.online.shop.clientservice.domain.dto.ClientRequest;
import com.online.shop.clientservice.domain.dto.ClientResponse;
import com.online.shop.clientservice.domain.entity.Client;
import com.online.shop.clientservice.domain.mapper.ClientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClientMapperTest {

    @Test
    public void testThatClientMapperConstructorInitializesClientMapperObject() {
        ClientMapper clientMapper = new ClientMapper();

        Assertions.assertInstanceOf(ClientMapper.class, clientMapper);
    }

    @Test
    public void testThatMapToResponseValuesEqualClientValues() {
        Client client = Client.builder()
                .id(UUID.randomUUID())
                .name("John")
                .surname("Doe")
                .email("john.doe@email.com")
                .address("1234 Elm Street Apt. 567 Springfield, IL 62701 USA")
                .createdAt(LocalDateTime.now())
                .build();

        ClientResponse response = ClientMapper.mapToResponse(client);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(client.getName(), response.getName());
            Assertions.assertEquals(client.getSurname(), response.getSurname());
            Assertions.assertEquals(client.getEmail(), response.getEmail());
            Assertions.assertEquals(client.getCreatedAt(), response.getCreatedAt());
        });
    }

    @Test
    public void testThatMapFromRequestsValuesEqualClientValue() {
        ClientRequest request = ClientRequest.builder()
                .name("John")
                .surname("Doe")
                .address("1234 Elm Street Apt. 567 Springfield, IL 62701 USA")
                .email("john.doe@email.com")
                .build();

        Client client = ClientMapper.mapFromRequest(request);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(client.getName(), request.getName());
            Assertions.assertEquals(client.getSurname(), request.getSurname());
            Assertions.assertEquals(client.getAddress(), request.getAddress());
            Assertions.assertEquals(client.getEmail(), request.getEmail());
        });
    }
}
