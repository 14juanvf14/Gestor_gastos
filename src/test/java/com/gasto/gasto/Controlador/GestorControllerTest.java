package com.gasto.gasto.Controlador;

import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Service.GestorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class GestorControllerTest {

    @InjectMocks
    private GestorController gestorController;

    @Mock
    private GestorService gestorService;

    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar gestor")
    public void testSaveGestor() throws RequestException {
        Gestor gestor = Gestor.builder()
                .id(2020L)
                .nombre("Nombre")
                .email("juan23v@gmail.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        when(gestorService.findById(gestor.getId())).thenReturn(Optional.empty());

        ResponseEntity<Gestor> response = gestorController.save("", gestor);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar gestor con email nulo")
    public void testSaveGestorEmailnull() throws RequestException {
        Gestor gestor = Gestor.builder()
                .id(2020L)
                .nombre("Nombre")
                .email(null)
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.save("",gestor);
        });
        assertEquals("G-102A", exception.getCode());
    }

    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar gestor con rol invalido")
    public void testSaveGestorRolINvalido() throws RequestException {
        Gestor gestor = Gestor.builder()
                .id(2020L)
                .nombre("Nombre")
                .email("juan@out.com")
                .password("JuanDa123")
                .rol("Carro")
                .build();

        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.save("",gestor);
        });
        assertEquals("G-103", exception.getCode());
    }

    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar gestor con email invalido")
    public void testSaveGestorEmailInvalid() throws RequestException {
        Gestor gestor = Gestor.builder()
                .id(2020L)
                .nombre("Nombre")
                .email("Juans234.c")
                .password("JuanDa123")
                .rol("Gestor")
                .build();
        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.save("",gestor);
        });
        assertEquals("G-102B", exception.getCode());
    }

    @Test
    @Rollback
    @DisplayName(value = "Prueba guardar gestor con nombre vacio")
    public void testGestorUserNameEmpty() throws RequestException {
        Gestor gestor = Gestor.builder()
                .id(2020L)
                .nombre("")
                .email("Juans234@link.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();
        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.save("",gestor);
        });
        assertEquals("G-104", exception.getCode());
    }


    @Test
    @Rollback
    @DisplayName("Prueba que valida la lista cuando esta vacia")
    public void testVerGestoresEmpty() throws RequestException {
        when(gestorService.getAll()).thenReturn(Collections.emptyList());

        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.findAll("");
        });

        assertEquals("G-106A", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test para guardar gestor cuando ya existe con id")
    public void testSaveGestorExistente() {
        Gestor gestor = Gestor.builder()
                .id(2020L)
                .nombre("Nombre")
                .email("Juan@gmail.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        when(gestorService.findById(gestor.getId())).thenReturn(Optional.of(gestor));

        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.save("",gestor);
        });

        assertEquals("G-101", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test que retorna una lista valida")
    public void testListGestor() throws RequestException {
        List<Gestor> gestores = Arrays.asList(
                Gestor.builder().id(2020L).nombre("Nombre").email("Juan@gmail.com").password("JuanDa123").rol("Gestor").build(),
                Gestor.builder().id(2021L).nombre("Nombre2").email("Juan2@gmail.com").password("JuanDa123").rol("Gestor").build(),
                Gestor.builder().id(2022L).nombre("Nombre3").email("Juan3@gmail.com").password("JuanDa123").rol("Gestor").build()
        );

        when(gestorService.getAll()).thenReturn(gestores);

        ResponseEntity<List<Gestor>> response = gestorController.findAll("");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gestores, response.getBody());
    }

    @Test
    @Rollback
    @DisplayName("Test  para buscar gestor por ID")
    public void testBuscarGestorID() throws RequestException {
        // Arrange
        long id = 123456L;
        Gestor gestor = Gestor.builder()
                .id(id)
                .nombre("Nombre")
                .email("Juan@gmail.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        when(gestorService.findById(id)).thenReturn(Optional.of(gestor));

        ResponseEntity<Gestor> response = gestorController.findById("", id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gestor, response.getBody());
    }

    @Test
    @Rollback
    @DisplayName("Test Buscar gestor no encontrado")
    public void testBuscarGestorIDNoEncontrado() {
        long id = 123456L;

        when(gestorService.findById(id)).thenReturn(Optional.empty());

        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.findById("",id);
        });

        assertEquals("G-106B", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test eliminar gestor encontrado en DB")
    public void testEliminarGestor() throws RequestException {
        long id = 123456L;
        Gestor gestor = Gestor.builder()
                .id(id)
                .nombre("Nombre")
                .email("Juan@gmail.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        when(gestorService.findById(id)).thenReturn(Optional.of(gestor));

        // Act
        ResponseEntity<Void> response = gestorController.deleteById("",id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Rollback
    @DisplayName("Test eliminar usuario no encontrado en DB")
    public void testEliminarGestorWhenNotFound() {
        long id = 123456L;

        when(gestorService.findById(id)).thenReturn(Optional.empty());

        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.deleteById("",id);
        });

        assertEquals("G-107", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }


    @Test
    @Rollback
    @DisplayName("Test update con gestor no encontrado en DB")
    public void testActualizarGestorNoEncontrado() {
        long id = 123456L;
        Gestor gestor = Gestor.builder()
                .id(id)
                .nombre("Nombre")
                .email("Juan@gmail.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        when(gestorService.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.update("",id, gestor);
        });

        assertEquals("G-106B", exception.getCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Test update de gestor valido")
    public void testActualizarGestor() throws RequestException {
        long id = 123456L;
        Gestor gestor = Gestor.builder()
                .id(id)
                .nombre("Nombre")
                .email("Juan@gmail.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        when(gestorService.findById(id)).thenReturn(Optional.of(gestor));

        Gestor gestorActualizado = Gestor.builder()
                .id(id)
                .nombre("Peputi")
                .email("Juan@gmail.com")
                .password("Jamas123")
                .rol("Gestor")
                .build();

        ResponseEntity<Gestor> response = gestorController.update("",id, gestorActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Rollback
    @DisplayName("Test update con email vacio")
    public void testActualizarGestorEmailVacio() {
        // Arrange
        long id = 123456L;
        Gestor gestor = Gestor.builder()
                .id(id)
                .nombre("Nombre")
                .email("Juan@gmail.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        when(gestorService.findById(id)).thenReturn(Optional.of(gestor));

        Gestor gestorActualizado = Gestor.builder()
                .id(id)
                .nombre("Peputi")
                .email("")
                .password("Jamas123")
                .rol("Gestor")
                .build();

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.update("",id, gestorActualizado);
        });

        assertEquals("G-102A", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @Rollback
    @DisplayName("Actualizar usuario con nombre vacio")
    public void testActualizarGestorNombreVacio() {
        long id = 123456L;
        Gestor gestor = Gestor.builder()
                .id(id)
                .nombre("Nombre")
                .email("Juan@gmail.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        when(gestorService.findById(id)).thenReturn(Optional.of(gestor));

        Gestor gestorActualizado = Gestor.builder()
                .id(id)
                .nombre("")
                .email("Juan@gmail.com")
                .password("Jamas123")
                .rol("Gestor")
                .build();

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.update("",id, gestorActualizado);
        });

        assertEquals("G-104", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }


    @Test
    @Rollback
    @DisplayName("Test para actualizar usuario con e-mail invalido")
    public void testCrearGestorEmailInvalido() {
        long id = 123456L;
        Gestor gestor = Gestor.builder()
                .id(id)
                .nombre("Nombre")
                .email("Juan@gmail.com")
                .password("JuanDa123")
                .rol("Gestor")
                .build();

        when(gestorService.findById(id)).thenReturn(Optional.of(gestor));

        Gestor gestorActualizado = Gestor.builder()
                .id(id)
                .nombre("Peputi")
                .email("")
                .password("Jamas123")
                .rol("Gestor")
                .build();

        // Act and Assert
        RequestException exception = assertThrows(RequestException.class, () -> {
            gestorController.update("",id, gestorActualizado);
        });
        assertEquals("G-102A", exception.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

}

