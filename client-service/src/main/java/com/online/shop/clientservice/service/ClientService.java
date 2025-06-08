package com.online.shop.clientservice.service;

import com.online.shop.clientservice.domain.dto.ClientResponse;
import com.online.shop.clientservice.domain.dto.ClientRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ClientService {
    Page<ClientResponse> readAllClients(Integer offset, Byte size);
    ClientResponse readClient(UUID id);
    ClientResponse saveClient(ClientRequest request);
    ClientResponse updateClient(UUID id, ClientRequest request);
    ClientResponse partialUpdateClient(UUID id, ClientRequest request);
    void deleteClient(UUID id);

}
