package com.gasto.gasto.Repository;

import com.gasto.gasto.Modelo.Gestor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class GestorRepositoryTest {

    @Autowired
    private GestorRepository gestorRepository;

    @Test
    @Rollback
    public void testFindByEmail() {
        String email = "juanv14@example.com";

        Optional<Gestor> result = gestorRepository.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(result.get().getEmail(), email);
    }

}