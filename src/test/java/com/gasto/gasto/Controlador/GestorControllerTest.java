package com.gasto.gasto.Controlador;

import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Service.GestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
public class GestorControllerTest {

    @Mock
    private GestorService gestorService;

    private GestorController gestorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gestorController = new GestorController(gestorService);
    }

    @Test
    @Rollback
    void testFindAll() {
        List<Gestor> gestores = new ArrayList<>();
        gestores.add(Gestor.builder()
                .id(1L).nombre("Gestor 1")
                .email("gestor1@example.com")
                .build());
        gestores.add(Gestor.builder()
                .id(2L).nombre("Gestor 2")
                .email("gestor2@example.com")
                .build());
        gestores.add(Gestor.builder()
                .id(3L).nombre("Gestor 3")
                .email("gestor3@example.com")
                .build());

        when(gestorService.getAll()).thenReturn(gestores);

        List<Gestor> result = gestorService.getAll();

        assertEquals(3, result.size());
        assertEquals("Gestor 1", result.get(0).getNombre());
        assertEquals("Gestor 2", result.get(1).getNombre());
    }

    @Test
    @Rollback
    void testFindById() {
        Long id = 1L;
        Gestor gestor = Gestor.builder()
                .id(id).nombre("Gestor 1")
                .email("gestor1@example.com")
                .build();

        when(gestorService.findById(id)).thenReturn(Optional.of(gestor));

        ResponseEntity<Gestor> result = gestorController.findById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(id, Objects.requireNonNull(result.getBody()).getId());
        assertEquals("Gestor 1", result.getBody().getNombre());
    }

    @Test
    @Rollback
    void testFindByIdNotFound() {
        Long id = 1L;
        when(gestorService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Gestor> result = gestorController.findById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    @Rollback
    void testSave() {
        Gestor gestor = Gestor.builder()
                .id(1L).nombre("Gestor 1")
                .email("gestor1@example.com")
                .build();
        when(gestorService.save(gestor)).thenReturn(Gestor.builder()
                .id(1L).nombre("Gestor 1")
                .email("gestor1@example.com")
                .build());

        Gestor result = gestorController.save(gestor);

        assertEquals(1L, result.getId());
        assertEquals("Gestor 1", result.getNombre());
    }

    @Test
    @Rollback
    void testUpdate() {
        Long id = 1L;
        Gestor currentGestor = Gestor.builder()
                .id(id).nombre("Gestor 1")
                .email("gestor1@example.com")
                .build();
        Gestor updatedGestor = Gestor.builder()
                .id(id).nombre("Gestor 2")
                .email("gestor1@example.com")
                .build();
        when(gestorService.findById(id)).thenReturn(Optional.of(currentGestor));
        when(gestorService.save(any(Gestor.class))).thenReturn(updatedGestor);

        ResponseEntity<Gestor> result = gestorController.update(id, updatedGestor);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(id, Objects.requireNonNull(result.getBody()).getId());
        assertEquals("Gestor 2", result.getBody().getNombre());
    }

    @Test
    @Rollback
    void testDeleteById() {
        Long id = 1L;
        Gestor gestor = Gestor.builder()
                .id(id).nombre("Gestor 1")
                .email("gestor1@example.com")
                .build();

        // Simula que el registro existe en la base de datos
        when(gestorService.findById(id)).thenReturn(Optional.of(gestor));
        doNothing().when(gestorService).deleteById(id);

        ResponseEntity<Void> result = gestorController.deleteById(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(gestorService, times(1)).deleteById(id);
    }

}

