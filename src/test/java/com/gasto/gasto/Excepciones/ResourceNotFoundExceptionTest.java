package com.gasto.gasto.Excepciones;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ResourceNotFoundExceptionTest {

    @Test
    void testConstructorWithValidEmail() {
        String email = "test@example.com";
        ResourceNotFoundException exception = assertDoesNotThrow(() -> new ResourceNotFoundException(email));
        assertThat(exception).isInstanceOf(ResourceNotFoundException.class);
    }



}