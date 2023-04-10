package com.gasto.gasto.Modelo;
import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Repository.GestorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

@Transactional
public class GestorTest {

    @Autowired
    private GestorRepository gestorRepository;

    @Test
    @Rollback
    public void testCrearGestorExitoso() {
        Gestor gestor = Gestor.builder()
                .nombre("Juan Perez")
                .email("juan.perez@example.com")
                .password("password")
                .build();
        Gestor gestorCreado = gestorRepository.save(gestor);
        Assertions.assertNotNull(gestorCreado.getId());
    }

    @Test
    @Rollback
    public void testCrearGestorConNombreNulo() {
        Gestor gestor = Gestor.builder()
                .email("juan.perez@example.com")
                .password("password")
                .build();
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            gestorRepository.save(gestor);
        });
    }

    @Test
    @Rollback
    public void testCrearGestorConEmailInvalido() {
        Gestor gestor = Gestor.builder()
                .nombre("Juan Perez")
                .email("juan.perezexample.com")
                .password("password")
                .build();
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            gestorRepository.save(gestor);
        });
    }

    @Test
    @Transactional
    @Rollback
    public void testCrearGestorConPasswordNulo() {
        Gestor gestor = Gestor.builder()
                .nombre("Juan Perez")
                .email("juan.perez@example.com")
                .build();
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            gestorRepository.save(gestor);
        });
    }

}