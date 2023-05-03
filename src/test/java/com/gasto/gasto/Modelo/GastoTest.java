package com.gasto.gasto.Modelo;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class GastoTest {

    @Test
    @Rollback
    public void testCreacionGasto() {
        LocalDate fecha = LocalDate.now();
        double monto = 50.0;
        String descripcion = "Comida";
        Usuario usuario = new Usuario();

        Gasto gasto = new Gasto();
        gasto.setFecha(fecha);
        gasto.setMonto(monto);
        gasto.setDescripcion(descripcion);
        gasto.setUsuario(usuario);

        assertNotNull(gasto);
        assertEquals(fecha, gasto.getFecha());
        assertEquals(monto, gasto.getMonto());
        assertEquals(descripcion, gasto.getDescripcion());
        assertEquals(usuario, gasto.getUsuario());
    }

    @Test
    @Rollback
    public void testIdGastoGenerado() {
        LocalDate fecha = LocalDate.now();
        double monto = 50.0;
        String descripcion = "Comida";
        Usuario usuario = new Usuario();

        Gasto gasto = new Gasto();
        gasto.setFecha(fecha);
        gasto.setMonto(monto);
        gasto.setDescripcion(descripcion);
        gasto.setUsuario(usuario);

        assertNotNull(gasto.getId());
    }

    @Test
    @Rollback
    public void testFechaGastoNula() {
        LocalDate fecha = null;
        double monto = 50.0;
        String descripcion = "Comida";
        Usuario usuario = new Usuario();

        Gasto gasto = new Gasto();
        gasto.setFecha(fecha);
        gasto.setMonto(monto);
        gasto.setDescripcion(descripcion);
        gasto.setUsuario(usuario);

        assertNotNull(gasto);
    }

    @Test
    @Rollback
    public void testMontoGastoNegativo() {
        LocalDate fecha = LocalDate.now();
        double monto = -50.0;
        String descripcion = "Comida";
        Usuario usuario = new Usuario();

        Gasto gasto = new Gasto();
        gasto.setFecha(fecha);
        gasto.setMonto(monto);
        gasto.setDescripcion(descripcion);
        gasto.setUsuario(usuario);

        assertNotNull(gasto);
    }

    @Test
    @Rollback
    public void testDescripcionGastoNula() {
        LocalDate fecha = LocalDate.now();
        double monto = 50.0;
        String descripcion = null;
        Usuario usuario = new Usuario();

        Gasto gasto = new Gasto();
        gasto.setFecha(fecha);
        gasto.setMonto(monto);
        gasto.setDescripcion(descripcion);
        gasto.setUsuario(usuario);

        assertNotNull(gasto);
    }

    @Test
    @Rollback
    public void testUsuarioGastoNulo() {
        LocalDate fecha = LocalDate.now();
        double monto = 50.0;
        String descripcion = "Comida";
        Usuario usuario = null;

        Gasto gasto = new Gasto();
        gasto.setFecha(fecha);
        gasto.setMonto(monto);
        gasto.setDescripcion(descripcion);
        gasto.setUsuario(usuario);

        assertNotNull(gasto);
    }

}