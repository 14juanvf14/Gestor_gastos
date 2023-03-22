package com.gasto.gasto;


import static org.junit.jupiter.api.Assertions.*;


import java.util.Date;
import com.gasto.gasto.Controlador.ControladorUsuario;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.gasto.gasto.Modelo.Usuario;



import javax.validation.ValidationException;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class ControladorUsuarioTest {

    @Autowired
    @InjectMocks
    private ControladorUsuario controlador;


    @Test
    public void agregarUsuario_Valido() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setCorreo("juanDa@gmail.com");
        usuario.setEstado(0);
        usuario.setFecha_ingreso(new Date());

        ResponseEntity<?> response = controlador.AgregarUsuario(usuario);
        Usuario usuarioAgregado = (Usuario) response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(usuarioAgregado);
        assertEquals(usuario.getNombre(), usuarioAgregado.getNombre());
        assertEquals(usuario.getCorreo(), usuarioAgregado.getCorreo());
        assertEquals(usuario.getFecha_ingreso(), usuarioAgregado.getFecha_ingreso());
    }

    @Test
    public void agregarUsuario_NombreVacio() {
        Usuario usuario = new Usuario();
        usuario.setNombre("");
        usuario.setCorreo("juan@gmail.com");
        usuario.setFecha_ingreso(new Date());

        assertThrows(ValidationException.class, () -> {
            controlador.AgregarUsuario(usuario);
        });
    }

    @Test
    public void agregarUsuario_CorreoInvalido() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setCorreo("correo_invalido");
        usuario.setFecha_ingreso(new Date());

        assertThrows(ValidationException.class, () -> {
            controlador.AgregarUsuario(usuario);
        });
    }

    @Test
    public void agregarUsuario_FechaIngresoFutura() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setCorreo("juan@gmail.com");
        usuario.setFecha_ingreso(new Date(System.currentTimeMillis() + 86400000)); // fecha de mañana

        assertThrows(ValidationException.class, () -> {
            controlador.AgregarUsuario(usuario);
        });
    }

    @Test
    public void testModificarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Test");
        usuario.setCorreo("test@test.com");
        usuario.setEstado(1);
        usuario.setFecha_ingreso(new Date());

        ResponseEntity<?> response = controlador.ModificarUsuario(usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testVerUsuarios() {
        ResponseEntity<?> response = controlador.VerUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testBuscarUsuarioID() {
        int id = 1;

        ResponseEntity<?> response = controlador.BuscarUsuarioID(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testEliminarUsuario() {
        int id = 1;

        ResponseEntity<?> response = controlador.EliminarUsuario(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}


