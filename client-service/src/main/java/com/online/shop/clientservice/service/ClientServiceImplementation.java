package com.online.shop.clientservice.service;

import com.online.shop.clientservice.domain.dto.ClientRequest;
import com.online.shop.clientservice.domain.dto.ClientResponse;
import com.online.shop.clientservice.domain.entity.Client;
import com.online.shop.clientservice.domain.mapper.ClientMapper;
import com.online.shop.clientservice.exception.throwable.ClientNotFoundException;
import com.online.shop.clientservice.exception.throwable.EmailAlreadyTakenException;
import com.online.shop.clientservice.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImplementation implements ClientService {

    private final ClientRepository clientRepository;

    /**
     * @param email Email address to validate
     */
    private void validateEmailUniqueness(String email) {
        if (clientRepository.existsByEmail(email)) {
            log.info("Email {} is already taken", email);
            throw new EmailAlreadyTakenException(String.format("Email %s is already taken", email));
        }
    }

    /**
     * @return Paginated list of all clients
     */
    @Override
    public Page<ClientResponse> readAllClients(Integer offset, Byte size) {
        return clientRepository.findAll(PageRequest.of(offset, size))
                .map(ClientMapper::mapToResponse);
    }

    /**
     * @param id Client UUID
     * @return Found client
     */
    @Override
    public ClientResponse readClient(UUID id) {
        Client foundClient = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapToResponse(foundClient);
    }

    /**
     * @param request Request with client data
     * @return Saved client
     */
    @Override
    public ClientResponse saveClient(ClientRequest request) {
        validateEmailUniqueness(request.getEmail());
        Client client = ClientMapper.mapFromRequest(request);
        client.setCreatedAt(LocalDateTime.now());
        Client savedClient = clientRepository.save(client);
        log.info("Saved new client under id {}", savedClient.getId());
        return ClientMapper.mapToResponse(savedClient);
    }

    /**
     * @param id Client UUID
     * @param request Request with updated client data
     * @return Updated client
     */
    @Override
    public ClientResponse updateClient(UUID id, ClientRequest request) {
        validateEmailUniqueness(request.getEmail());
        Client client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        client.setId(id);
        client.setName(request.getName());
        client.setSurname(request.getSurname());
        client.setEmail(request.getEmail());
        client.setAddress(request.getAddress());

        Client updatedClient = clientRepository.save(client);
        return ClientMapper.mapToResponse(updatedClient);
    }

    /**
     * @param id Client UUID
     * @param request Request with partially updated client data
     * @return Updated Client
     */
    @Override
    public ClientResponse partialUpdateClient(UUID id, ClientRequest request) {
        validateEmailUniqueness(request.getEmail());
        Client client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        Optional.ofNullable(request.getName()).ifPresent(client::setName);
        Optional.ofNullable(request.getSurname()).ifPresent(client::setSurname);
        Optional.ofNullable(request.getEmail()).ifPresent(client::setEmail);
        Optional.ofNullable(request.getAddress()).ifPresent(client::setAddress);

        Client updatedClient = clientRepository.save(client);

        return ClientMapper.mapToResponse(updatedClient);
    }

    /**
     * @param id Client UUID
     */
    @Override
    public void deleteClient(UUID id) {
        clientRepository.deleteById(id);
        log.info("Client with id {} has been deleted", id);
    }
}
