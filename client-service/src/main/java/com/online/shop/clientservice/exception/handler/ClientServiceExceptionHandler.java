package com.online.shop.clientservice.exception.handler;

import com.online.shop.clientservice.exception.throwable.ClientNotFoundException;
import com.online.shop.clientservice.exception.throwable.EmailAlreadyTakenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class ClientServiceExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ClientNotFoundException.class)
    public ProblemDetail handleClientNotFoundException(ClientNotFoundException exception) {
        log.debug("Client not found {}", exception.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Client not found");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setType(URI.create("/api/v1/clients/{id}"));
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ProblemDetail handleEmailAlreadyTakenException(EmailAlreadyTakenException exception) {
        log.debug("Email already taken {}", exception.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
        problemDetail.setTitle("Email already taken");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setType(URI.create("/api/v1/clients"));
        return problemDetail;
    }
}
