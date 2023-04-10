package com.gasto.gasto.Controlador;

import com.gasto.gasto.Excepciones.ResourceNotFoundException;
import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @Test
    @Rollback
    void testSaveUser() throws ResourceNotFoundException {
        Usuario usuario = Usuario.builder()
                .nombre("Juan")
                .email("juan@mail.com")
                .fecha_ingreso(LocalDate.now())
                .build();
        when(usuarioService.saveUser(usuario)).thenReturn(usuario);
        Usuario respuesta = usuarioController.saveUser(usuario);
        assertEquals(usuario, respuesta);
    }

    @Test
    @Rollback
    void testVerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(Usuario.builder().id(1L).nombre("Juan").build());
        usuarios.add(Usuario.builder().id(2L).nombre("Pedro").build());
        when(usuarioService.getAllUsers()).thenReturn(usuarios);
        List<Usuario> respuesta = usuarioController.verUsuarios();
        assertEquals(usuarios, respuesta);
    }

    @Test
    @Rollback
    void testBuscarUsuarioID() {
        Usuario usuario = Usuario.builder().id(1L).nombre("Juan").build();
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        when(usuarioService.getUserById(1L)).thenReturn(optionalUsuario);
        ResponseEntity<Usuario> respuesta = usuarioController.buscarUsuarioID(1L);
        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respuesta.getBody()).isEqualTo(usuario);
    }

    @Test
    @Rollback
    void testActualizarUsuario() {
        Usuario usuario = Usuario.builder().id(1L).nombre("Juan").build();
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        when(usuarioService.getUserById(1L)).thenReturn(optionalUsuario);
        when(usuarioService.updateUser(usuario)).thenReturn(usuario);
        ResponseEntity<Usuario> respuesta = usuarioController.actualizarUsuario(1L, usuario);
        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respuesta.getBody()).isEqualTo(usuario);
    }

    @Test
    @Rollback
    void testEliminarUsuario() {
        usuarioController.eliminarUsuario(1L);

        ResponseEntity<String> respuesta = usuarioController.eliminarUsuario(1L);
        assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respuesta.getBody()).isEqualTo("Usuario eliminado correctamente");
    }
}