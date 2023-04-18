package com.gasto.gasto.Modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {

    private Error error;

    @BeforeEach
    public void setUp() {
        error = Error.builder()
                .code("1001")
                .error_mensaje("Error de prueba")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @Test
    public void testErrorModel() {
        assertEquals("1001", error.getCode());
        assertEquals("Error de prueba", error.getError_mensaje());
        assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());
    }
}

