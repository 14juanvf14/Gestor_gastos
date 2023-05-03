package com.gasto.gasto.Modelo;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
class UsuarioTest {

    @Test
    @Rollback
    void testNombre() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        assertEquals("Juan", usuario.getNombre());
    }

    @Test
    @Rollback
    void testEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("juan@example.com");
        assertEquals("juan@example.com", usuario.getEmail());
    }

    @Test
    @Rollback
    void testId() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        assertEquals(1L, usuario.getId());
    }

    @Test
    @Rollback
    void testEstado() {
        Usuario usuario = new Usuario();
        usuario.setEstado(1);
        assertEquals(1, usuario.getEstado());
    }

    @Test
    @Rollback
    void testFechaIngreso() {
        LocalDate fechaIngreso = LocalDate.of(2022, 1, 1);
        Usuario usuario = new Usuario();
        usuario.setFecha_ingreso(fechaIngreso);
        assertEquals(fechaIngreso, usuario.getFecha_ingreso());
    }

    @Test
    @Rollback
    public void testValidarGastos() {
        List<Gasto> gastos = new ArrayList<>();
        Gasto gasto1 = Gasto.builder()
                .id(1)
                .descripcion("Compra en el supermercado")
                .monto(50.0)
                .usuario(null)
                .build();
        gastos.add(gasto1);
        Usuario usuario = Usuario.builder()
                .id(1)
                .estado(1)
                .nombre("Juan Perez")
                .email("juan.perez@example.com")
                .fecha_ingreso(LocalDate.now())
                .gastos(gastos)
                .build();
        assertEquals(usuario.getGastos().size(), 1);
    }


}