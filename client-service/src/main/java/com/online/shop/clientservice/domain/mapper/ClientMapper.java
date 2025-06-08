package com.online.shop.clientservice.domain.mapper;

import com.online.shop.clientservice.domain.dto.ClientRequest;
import com.online.shop.clientservice.domain.dto.ClientResponse;
import com.online.shop.clientservice.domain.entity.Client;

public class ClientMapper {

    public static ClientResponse mapToResponse(Client client) {
        return ClientResponse.builder()
                .email(client.getEmail())
                .name(client.getName())
                .surname(client.getSurname())
                .createdAt(client.getCreatedAt())
                .build();
    }

    public static Client mapFromRequest(ClientRequest request) {
        return Client.builder()
                .email(request.getEmail())
                .name(request.getName())
                .surname(request.getSurname())
                .address(request.getAddress())
                .build();
    }
}
