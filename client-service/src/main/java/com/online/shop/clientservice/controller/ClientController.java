package com.online.shop.clientservice.controller;

import com.online.shop.clientservice.domain.dto.ClientRequest;
import com.online.shop.clientservice.domain.dto.ClientResponse;
import com.online.shop.clientservice.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> getAllClients(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(127) Byte size
    ) {
        Page<ClientResponse> clients = clientService.readAllClients(offset, size);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable UUID id) {
        return new ResponseEntity<>(clientService.readClient(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> postClient(@Valid @RequestBody ClientRequest request) {
        ClientResponse savedClient = clientService.saveClient(request);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ClientResponse> updateClient(
            @PathVariable UUID id,
            @Valid @RequestBody ClientRequest request
    ) {
        ClientResponse updatedClient = clientService.updateClient(id, request);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ClientResponse> partialUpdateClient(
            @PathVariable UUID id,
            @RequestBody ClientRequest request
    ) {
        ClientResponse updatedClient = clientService.partialUpdateClient(id, request);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
