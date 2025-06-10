package com.online.shop.clientservice.unit.exception.handler;

import com.online.shop.clientservice.exception.handler.ClientServiceExceptionHandler;
import com.online.shop.clientservice.exception.throwable.ClientNotFoundException;
import com.online.shop.clientservice.exception.throwable.EmailAlreadyTakenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@ExtendWith(MockitoExtension.class)
public class ClientServiceExceptionHandlerTest {

    @InjectMocks
    private ClientServiceExceptionHandler clientServiceExceptionHandler;

    @Test
    public void testThatClientServiceExceptionHandlerHandlesEmailAlreadyTakenException() {
        // Arrange
        String message = "Given email is already taken";
        EmailAlreadyTakenException exception = new EmailAlreadyTakenException(message);

        // Act
        ProblemDetail response = clientServiceExceptionHandler.handleEmailAlreadyTakenException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
        Assertions.assertEquals("Email already taken", response.getTitle());
        Assertions.assertEquals(message, response.getDetail());
    }

    @Test
    public void testThatClientServiceExceptionHandlerHandlesClientNotFoundException() {
        // Arrange
        String message = "Client with given id was not found";
        ClientNotFoundException exception = new ClientNotFoundException(message);

        // Act
        ProblemDetail response = clientServiceExceptionHandler.handleClientNotFoundException(exception);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        Assertions.assertEquals("Client not found", response.getTitle());
        Assertions.assertEquals(message, response.getDetail());
    }
}
