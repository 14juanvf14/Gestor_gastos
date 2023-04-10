package com.gasto.gasto.Controlador;

import com.gasto.gasto.Modelo.Gasto;
import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Service.GastoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@Transactional
public class GastoControllerTest {

    @InjectMocks
    private GastoController gastoController;

    @Mock
    private GastoService gastoService;

    @Test
    @Rollback
    public void testFindAll() {
        Usuario usuarioPrueba1 = Usuario.builder()
                .nombre("Roberto")
                .email("juan@hotm.com")
                .fecha_ingreso(LocalDate.of(2022, 1, 3))
                .estado(1)
                .build();

        // create a list of mock Gasto objects
        List<Gasto> gastos = Arrays.asList(
                Gasto.builder().id(1L)
                        .fecha(LocalDate.of(2022,2,3))
                        .monto(4.5).descripcion("Compra en el supermercado")
                        .usuario(usuarioPrueba1)
                        .build(),
                Gasto.builder().id(2L)
                        .fecha(LocalDate.of(2022,2,3))
                        .monto(67.5).descripcion("Compra en el avion")
                        .usuario(usuarioPrueba1)
                        .build()
        );
        // set up the mock behavior of GastoService.findAll()
        Mockito.when(gastoService.findAll()).thenReturn(gastos);
        // call the GastoController.findAll() method
        ResponseEntity<List<Gasto>> response = gastoController.findAll();


        // assert that the response status code is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // assert that the response body contains the expected list of Gasto objects
        assertEquals(gastos, response.getBody());
    }

    @Test
    @Rollback
    public void testFindById() {
        Gasto gasto = new Gasto(1L, LocalDate.of(2021, 4, 1), 200.0, "Comida", null);
        Mockito.when(gastoService.findById(1L)).thenReturn(gasto);
        ResponseEntity<Gasto> response = gastoController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gasto, response.getBody());
    }

    @Test
    @Rollback
    public void testFindByIdNotFound() {
        Mockito.when(gastoService.findById(1L)).thenReturn(null);
        ResponseEntity<Gasto> response = gastoController.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Rollback
    public void testSave() {
        Gasto gasto = new Gasto(1L, LocalDate.of(2021, 4, 1), 200.0, "Comida", null);
        Mockito.when(gastoService.save(gasto)).thenReturn(gasto);
        ResponseEntity<Gasto> response = gastoController.save(gasto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(gasto, response.getBody());
    }

    @Test
    @Rollback
    public void testUpdate() {
        Gasto currentGasto = new Gasto(1L, LocalDate.of(2021, 4, 1), 200.0, "Comida", null);
        Gasto updatedGasto = new Gasto(1L, LocalDate.of(2021, 4, 2), 300.0, "Comida y bebida", null);
        Mockito.when(gastoService.findById(1L)).thenReturn(currentGasto);
        Mockito.when(gastoService.save(currentGasto)).thenReturn(updatedGasto);
        ResponseEntity<Gasto> response = gastoController.update(1L, updatedGasto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Rollback
    public void testUpdateNotFound() {
        Gasto updatedGasto = new Gasto(1L, LocalDate.of(2021, 4, 2), 300.0, "Comida y bebida", null);
        Mockito.when(gastoService.findById(1L)).thenReturn(null);
        ResponseEntity<Gasto> response = gastoController.update(1L, updatedGasto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Rollback
    public void testDeleteById() {
        Long gastoId = 1L;
        Gasto gasto = new Gasto(gastoId, LocalDate.of(2021, 4, 1), 200.0, "Comida", null);
        Mockito.when(gastoService.findById(gastoId)).thenReturn(gasto);
        ResponseEntity<Void> response = gastoController.deleteById(gastoId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(gastoService, Mockito.times(1)).deleteById(gastoId);
    }




}
