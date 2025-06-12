package com.online.shop.clientservice.exception.throwable;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message) {
        super(message);
    }
    public ClientNotFoundException() {
        super("Client with given Id doesn't exist");
    }
}
