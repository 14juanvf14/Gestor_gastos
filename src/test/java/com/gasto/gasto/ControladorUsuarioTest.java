package com.gasto.gasto;

import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Controlador.ControladorUsuario;
import com.gasto.gasto.Service.UsuarioServiceIMP.UsuarioSIMP;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControladorUsuarioTest {

    @Mock
    private UsuarioSIMP usuarioService;

    @InjectMocks
    private ControladorUsuario controladorUsuario;

    private Usuario usuario;

    @Before
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setEstado(1);
        usuario.setNombre("Juan Perez");
        usuario.setCorreo("juan.perez@gmail.com");
        usuario.setFecha_ingreso(new Date());
    }

    @Test
    public void agregarUsuario_debeRetornarUsuarioCreado() {
        when(usuarioService.agregarUsuario(any(Usuario.class))).thenReturn(usuario);
        ResponseEntity<?> respuesta = controladorUsuario.AgregarUsuario(usuario);
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(usuario, respuesta.getBody());
    }


    @Test
    public void verUsuarios_debeRetornarListaUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuario);
        when(usuarioService.verUsuarios()).thenReturn(listaUsuarios);
        ResponseEntity<?> respuesta = controladorUsuario.VerUsuarios();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(listaUsuarios, respuesta.getBody());
    }

    @Test
    public void buscarUsuarioID_debeRetornarUsuarioPorId() {
        when(usuarioService.buscarUsuarioID(anyInt())).thenReturn(usuario);
        ResponseEntity<?> respuesta = controladorUsuario.BuscarUsuarioID(1);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(usuario, respuesta.getBody());
    }

    @Test
    public void eliminarUsuario_debeRetornarOk() {
        ResponseEntity<?> respuesta = controladorUsuario.EliminarUsuario(1);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        verify(usuarioService, times(1)).eliminarUsuario(1);
    }

}
