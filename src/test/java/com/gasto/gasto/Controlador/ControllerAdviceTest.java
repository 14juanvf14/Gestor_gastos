package com.gasto.gasto.Controlador;

import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.Error;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerAdviceTest {

    @Mock
    private RequestException requestException;

    @InjectMocks
    private ControllerAdvice controllerAdvice;

    @Test
    @Rollback
    public void testRuntimeExceptionHandler() {
        // Arrange
        String errorMessage = "Error de tiempo de ejecución";
        RuntimeException runtimeException = new RuntimeException(errorMessage);

        // Act
        ResponseEntity<Error> response = controllerAdvice.RuntimeExceptionHandler(runtimeException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("P-500", Objects.requireNonNull(response.getBody()).getCode());
        assertEquals(errorMessage, response.getBody().getError_mensaje());
    }

    @Test
    @Rollback
    public void testRequestExceptionHandler() {
        // Arrange
        String errorCode = "U-103B";
        String errorMessage = "El email proporcionado no es válido";
        when(requestException.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);
        when(requestException.getMessage()).thenReturn(errorMessage);

        // Act
        ResponseEntity<Error> response = controllerAdvice.RequestExceptionHandler(requestException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
