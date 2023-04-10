package com.gasto.gasto.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gasto.gasto.Service.GastoServiceImpl.GastoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gasto.gasto.Modelo.Gasto;
import com.gasto.gasto.Repository.GastoRepository;


public class GastoServiceImplTest {

    @Mock
    private GastoRepository gastoRepository;

    @InjectMocks
    private GastoServiceImpl gastoService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Gasto> gastos = new ArrayList<>();
        gastos.add(Gasto.builder().id(1L)
                .fecha(LocalDate.of(2022,2,3))
                .monto(304.5).descripcion("Compra en internet")
                .build());
        gastos.add(Gasto.builder().id(2L)
                .fecha(LocalDate.of(2022,2,3))
                .monto(334.5).descripcion("Compra en el bus")
                .build());
        gastos.add(Gasto.builder().id(3L)
                .fecha(LocalDate.of(2022,2,3))
                .monto(4.5).descripcion("Compra en el supermercado")
                .build());

        when(gastoRepository.findAll()).thenReturn(gastos);

        List<Gasto> result = gastoService.findAll();

        assertEquals(gastos, result);
        verify(gastoRepository).findAll();
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Gasto gasto = Gasto.builder().id(3L)
                .fecha(LocalDate.of(2022,2,3))
                .monto(4.5).descripcion("Compra en el supermercado")
                .build();

        when(gastoRepository.findById(id)).thenReturn(Optional.of(gasto));

        Gasto result = gastoService.findById(id);

        assertEquals(gasto, result);
        verify(gastoRepository).findById(id);
    }

    @Test
    public void testSave() {
        Gasto gasto = Gasto.builder().id(3L)
                .fecha(LocalDate.of(2022,2,3))
                .monto(4.5).descripcion("Compra en el supermercado")
                .build();

        when(gastoRepository.save(gasto)).thenReturn(gasto);

        Gasto result = gastoService.save(gasto);

        assertEquals(gasto, result);
        verify(gastoRepository).save(gasto);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        doNothing().when(gastoRepository).deleteById(id);

        gastoService.deleteById(id);

        verify(gastoRepository).deleteById(id);
    }

    @Test
    public void testUpdateGasto() {
        Gasto gastoActualizado = Gasto.builder().id(3L)
                .fecha(LocalDate.of(2022,2,3))
                .monto(4.5).descripcion("Compra en el supermercado")
                .build();

        when(gastoRepository.save(gastoActualizado)).thenReturn(gastoActualizado);

        Gasto result = gastoService.updateGasto(gastoActualizado);

        assertEquals(gastoActualizado, result);
        verify(gastoRepository).save(gastoActualizado);
    }
}