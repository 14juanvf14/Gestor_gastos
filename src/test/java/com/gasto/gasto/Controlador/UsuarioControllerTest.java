package com.gasto.gasto.Controlador;

import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName(value = "Prueba guardar user valido")
    public void testSaveUser() throws RequestException {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1020253)
                .nombre("Nombre")
                .estado(1)
                .email("juan23v@gmail.com")
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(Mockito.anyLong())).thenReturn(Optional.empty());
        when(usuarioService.saveUser(Mockito.any(Usuario.class))).thenReturn(usuario);

        // Act
        ResponseEntity<Usuario> response = usuarioController.saveUser(usuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar user con email nulo")
    public void testSaveUserEmailnull() throws RequestException {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1020233)
                .nombre("Nombre")
                .estado(1)
                .email(null)
                .fecha_ingreso(LocalDate.now())
                .build();

        // Act
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.saveUser(usuario);
        });
        assertEquals("U-102A", exception.getCode());
    }

    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar user con email invalido")
    public void testSaveUserEmailInvalid() throws RequestException {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1020233)
                .nombre("Nombre")
                .estado(1)
                .email("juan123.com")
                .fecha_ingreso(LocalDate.now())
                .build();

        // Act
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.saveUser(usuario);
        });
        assertEquals("U-102B", exception.getCode());
    }

    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar user con nombre vacio")
    public void testSaveUserNameEmpty() throws RequestException {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1020233)
                .nombre("")
                .estado(1)
                .email("juan123@pepito.com")
                .fecha_ingreso(LocalDate.now())
                .build();

        // Act
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.saveUser(usuario);
        });
        assertEquals("U-104", exception.getCode());
    }


    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar user con estado invalido")
    public void testSaveUserStateInvalid() throws RequestException {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1020233)
                .nombre("Nombre")
                .estado(2)
                .email("juan123@gmail.com")
                .fecha_ingreso(LocalDate.now())
                .build();

        // Act
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.saveUser(usuario);
        });
        assertEquals("U-103", exception.getCode());
    }

    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar user con fecha futura")
    public void testSaveUserDateFuture() throws RequestException {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1020233)
                .nombre("Nombre")
                .estado(1)
                .email("juan123@gmail.com")
                .fecha_ingreso(LocalDate.of(2024,12,1))
                .build();

        // Act
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.saveUser(usuario);
        });
        assertEquals("U-105", exception.getCode());
    }


    @Test
    @Rollback
    @DisplayName("Prueba que valida la lista cuando esta vacia")
    public void testVerUsuariosWhenEmpty() throws RequestException {
        // Arrange
        when(usuarioService.getAllUsers()).thenReturn(Collections.emptyList());

        // Act
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.verUsuarios();
        });

        // Assert
        assertEquals("U-106A", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No se encontraron usuarios", exception.getMessage());
    }

    @Test
    @Rollback
    @DisplayName("Test para guardar usuario cuando ya existe con id")
    public void testSaveUserWhenUserAlreadyExists() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(123456)
                .nombre("Juan Perez")
                .email("juanperez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(usuario.getId())).thenReturn(Optional.of(usuario));

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.saveUser(usuario);
        });

        assertEquals("U-101", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test para guardar usuario cuando el e-mail ya existe en la base")
    public void testSaveUserWhenEmailAlreadyExists() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(123456)
                .nombre("Juan Perez")
                .email("juanperez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.saveUser(usuario);
        });

        assertEquals("U-102C", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test que retorna una lista valida")
    public void testVerUsuariosWhenValidList() throws RequestException {
        List<Usuario> usuarios = Arrays.asList(
                Usuario.builder().id(1L).nombre("Juan Perez").email("juan.perez@gmail.com").estado(1).fecha_ingreso(LocalDate.now()).build(),
                Usuario.builder().id(2L).nombre("Ana Gomez").email("ana.gomez@gmail.com").estado(0).fecha_ingreso(LocalDate.now().minusDays(1)).build(),
                Usuario.builder().id(3L).nombre("Pedro Rodriguez").email("pedro.rodriguez@gmail.com").estado(1).fecha_ingreso(LocalDate.now().minusDays(2)).build()
        );

        when(usuarioService.getAllUsers()).thenReturn(usuarios);

        ResponseEntity<List<Usuario>> response = usuarioController.verUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarios, response.getBody());
    }

    @Test
    @Rollback
    @DisplayName("Test  para buscar usuario por ID")
    public void testBuscarUsuarioID() throws RequestException {
        // Arrange
        long id = 123456L;
        Usuario usuario = Usuario.builder()
                .id(id)
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(id)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.buscarUsuarioID(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    @Rollback
    @DisplayName("Test Buscar usuario no encontrado")
    public void testBuscarUsuarioIDNoEncontrado() {
        long id = 123456L;

        when(usuarioService.getUserById(id)).thenReturn(Optional.empty());

        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.buscarUsuarioID(id);
        });

        assertEquals("U-106B", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    @Rollback
    @DisplayName("Tes eliminar usuario encontrado en DB")
    public void testEliminarUsuario() throws RequestException {
        long usuarioId = 123456L;
        Usuario usuario = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(0)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(usuarioId)).thenReturn(Optional.of(usuario));

        // Act
        ResponseEntity<String> response = usuarioController.eliminarUsuario(usuarioId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario eliminado correctamente", response.getBody());
    }

    @Test
    @Rollback
    @DisplayName("Test eliminar usuario no encontrado en DB")
    public void testEliminarUsuarioWhenNotFound() {
        // Arrange
        long usuarioId = 123456L;

        when(usuarioService.getUserById(usuarioId)).thenReturn(Optional.empty());

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.eliminarUsuario(usuarioId);
        });

        assertEquals("U-106B", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    @Rollback
    @DisplayName("Test eliminar usuario no encontrado en DB")
    public void testBuscarUsuarioPorEmailWhenFound() throws RequestException {
        String email = "juan.perez@gmail.com";
        Usuario usuario = Usuario.builder()
                .id(123456L)
                .nombre("Juan Perez")
                .email(email)
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserByEmail(email)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.buscarUsuarioPorEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    @Rollback
    @DisplayName("Test buscar por email, email de usuario no encontrado en DB")
    public void testBuscarUsuarioPorEmailNoEncontrado() {
        String email = "correo.no.existe@gmail.com";

        when(usuarioService.getUserByEmail(email)).thenReturn(Optional.empty());

        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.buscarUsuarioPorEmail(email);
        });

        assertEquals("U-107", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test update con usuario no encontrado en DB")
    public void testActualizarUsuarioNoEncontrado() {
        // Arrange
        long usuarioId = 123456L;
        Usuario usuario = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(usuarioId)).thenReturn(Optional.empty());

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.actualizarUsuario(usuarioId, usuario);
        });

        assertEquals("U-106B", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test update de usuario valido")
    public void testActualizarUsuario() throws RequestException {
        long usuarioId = 123456L;
        Usuario usuario = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioService.updateUser(usuario)).thenReturn(usuario);

        Usuario usuarioActualizado = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez Martinez")
                .email("juan.martinez@gmail.com")
                .estado(0)
                .fecha_ingreso(LocalDate.now().minusYears(1))
                .build();

        ResponseEntity<Usuario> response = usuarioController.actualizarUsuario(usuarioId, usuarioActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Rollback
    @DisplayName("Test update con email vacio")
    public void testActualizarUsuarioEmailVacio() {
        // Arrange
        long usuarioId = 123456L;
        Usuario usuario = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(usuarioId)).thenReturn(Optional.of(usuario));

        Usuario usuarioActualizado = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez Martinez")
                .email("")
                .estado(0)
                .fecha_ingreso(LocalDate.now().minusYears(1))
                .build();

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.actualizarUsuario(usuarioId, usuarioActualizado);
        });

        assertEquals("U-102A", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test update con email existente en base de datos")
    public void testActualizarUsuarioEmailExiste() {
        // Arrange
        long usuarioId = 123456L;
        Usuario usuario = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioService.getUserByEmail("juan.perez.martinez@gmail.com")).thenReturn(Optional.of(usuario));

        Usuario usuarioActualizado = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez Martinez")
                .email("juan.perez.martinez@gmail.com")
                .estado(0)
                .fecha_ingreso(LocalDate.now().minusYears(1))
                .build();

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.actualizarUsuario(usuarioId, usuarioActualizado);
        });

        assertEquals("U-102C", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Actualización con estado invalido")
    public void testActualizarUsuarioConEstadoInvalido() {
        // Arrange
        long usuarioId = 123456L;
        Usuario usuario = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(usuarioId)).thenReturn(Optional.of(usuario));

        Usuario usuarioActualizado = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez Martinez")
                .email("juan.perez.martinez@gmail.com")
                .estado(5)
                .fecha_ingreso(LocalDate.now().minusYears(1))
                .build();

        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.actualizarUsuario(usuarioId, usuarioActualizado);
        });

        assertEquals("U-103", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Actualizar usuario con nombre vacio")
    public void testActualizarUsuariNombreVacio() {
        // Arrange
        long usuarioId = 123456L;
        Usuario usuario = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        when(usuarioService.getUserById(usuarioId)).thenReturn(Optional.of(usuario));

        Usuario usuarioActualizado = Usuario.builder()
                .id(usuarioId)
                .nombre("")
                .email("juan.perez.martinez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now().minusYears(1))
                .build();

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.actualizarUsuario(usuarioId, usuarioActualizado);
        });

        assertEquals("U-104", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Actualizar con fecha futura")
    public void testUpdateUsuarioFutureDate() {
        Usuario usuario = Usuario.builder()
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        Usuario usuarioActualizado = Usuario.builder()
                .id(usuario.getId())
                .nombre("Maria Gomez")
                .email("maria.gomez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now().plusDays(1)) // Fecha futura
                .build();

        when(usuarioService.getUserById(usuario.getId())).thenReturn(Optional.of(usuario));

        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.actualizarUsuario(usuario.getId(), usuarioActualizado);
        });

        assertEquals("U-105", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test para actualizar usuario con e-mail invalido")
    public void testCrearUsuarioEmailInvalido() {
        Usuario usuarioActual = Usuario.builder()
                .nombre("Juan Perez")
                .email("juan.perez@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())
                .build();

        Usuario usuarioFuturo = Usuario.builder()
                .nombre("Maria Gomez")
                .email("maria.gomez@@gmail.com")
                .estado(1)
                .fecha_ingreso(LocalDate.now())// Fecha futura
                .build();

        when(usuarioService.getUserById(usuarioActual.getId())).thenReturn(Optional.of(usuarioActual));

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            usuarioController.actualizarUsuario(usuarioActual.getId(),usuarioFuturo);
        });

        assertEquals("U-102B", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

}