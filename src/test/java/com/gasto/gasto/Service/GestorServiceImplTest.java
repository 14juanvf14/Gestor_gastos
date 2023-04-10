package com.gasto.gasto.Service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Repository.GestorRepository;
import com.gasto.gasto.Service.GestorServiceImpl.GestorServiceImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GestorServiceImplTest {

    @Mock
    private GestorRepository gestorRepository;

    @InjectMocks
    private GestorServiceImpl gestorService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Rollback
    public void testGetAll() {
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

        when(gestorRepository.findAll()).thenReturn(gestores);

        List<Gestor> result = gestorService.getAll();

        assertEquals(gestores, result);
        verify(gestorRepository).findAll();
    }

    @Test
    @Rollback
    public void testFindById() {
        Long id = 1L;
        Gestor gestor = Gestor.builder()
                .id(1L).nombre("Gestor 1")
                .email("gestor1@example.com")
                .build();

        when(gestorRepository.findById(id)).thenReturn(Optional.of(gestor));

        Optional<Gestor> result = gestorService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(gestor, result.get());
        verify(gestorRepository).findById(id);
    }

    @Test
    @Rollback
    public void testSave() {
        Gestor gestor = Gestor.builder()
                .id(1L).nombre("Gestor 1")
                .email("gestor1@example.com")
                .build();

        when(gestorRepository.save(gestor)).thenReturn(gestor);

        Gestor result = gestorService.save(gestor);

        assertEquals(gestor, result);
        verify(gestorRepository).save(gestor);
    }

    @Test
    @Rollback
    public void testDeleteById() {
        Long id = 1L;

        doNothing().when(gestorRepository).deleteById(id);

        gestorService.deleteById(id);

        verify(gestorRepository).deleteById(id);
    }

    @Test
    @Rollback
    public void testUpdateGestor() {
        Gestor gestorActualizado = Gestor.builder()
                .id(1L).nombre("Gestor 1")
                .email("gestor1@example.com")
                .build();

        when(gestorRepository.save(gestorActualizado)).thenReturn(gestorActualizado);

        Gestor result = gestorService.updategestor(gestorActualizado);

        assertEquals(gestorActualizado, result);
        verify(gestorRepository).save(gestorActualizado);
    }
}