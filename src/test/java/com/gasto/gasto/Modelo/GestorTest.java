package com.gasto.gasto.Modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@Transactional
class GestorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Rollback
    void testValidGestor() {
        Gestor gestor = Gestor.builder()
                .nombre("Juan Pérez")
                .email("juan.perez@example.com")
                .password("StrongPassword123")
                .build();

        Set<ConstraintViolation<Gestor>> violations = validator.validate(gestor);

        assertEquals(2, violations.size());
    }

    @Test
    @Rollback
    void testInvalidNombre() {
        Gestor gestor = Gestor.builder()
                .nombre("Juan123")
                .email("juan.perez@example.com")
                .password("StrongPassword123")
                .build();

        Set<ConstraintViolation<Gestor>> violations = validator.validate(gestor);

        assertEquals(3, violations.size());
        assertEquals("El nombre no puede contener números o caracteres extraños", violations.iterator().next().getMessage());
    }

    @Test
    @Rollback
    void testInvalidEmail() {
        Gestor gestor = Gestor.builder()
                .nombre("Juan Pérez")
                .email("juaneznvalidemail")
                .password("StrongPassword123")
                .rol("Gestor")
                .build();

        Set<ConstraintViolation<Gestor>> violations = validator.validate(gestor);

        assertEquals(3, violations.size());
        assertEquals("no debe ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    @Rollback
    void testInvalidPassword() {
        Gestor gestor = Gestor.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .email("juan.perez@example.com")
                .password("")
                .build();

        Set<ConstraintViolation<Gestor>> violations = validator.validate(gestor);

        assertEquals(3, violations.size());
        assertEquals("La clave no puede estar vacío", violations.iterator().next().getMessage());
    }

    @Test
    public void testCrearGestor() {
        Gestor gestor = Gestor.builder()
                .id(1L)
                .nombre("Juan")
                .email("Juanisimo@gmail.com")
                .password("Husus123")
                .build();
        assertEquals(1L, gestor.getId());
        assertEquals("Juan", gestor.getNombre());
        assertEquals("Juanisimo@gmail.com", gestor.getEmail());
        assertEquals("Husus123", gestor.getPassword());
    }

}
