package com.gasto.gasto.Service;

import com.gasto.gasto.Excepciones.ResourceNotFoundException;
import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Repository.UsuarioRepository;
import com.gasto.gasto.Service.UsuarioServiceImpl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuarioPrueba1, usuarioPrueba2;

    @BeforeEach
    void setup(){
        usuarioPrueba1 = Usuario.builder()
                .nombre("Roberto")
                .email("juan@hotm.com")
                .fecha_ingreso(LocalDate.of(2022,1,3))
                .estado(1)
                .build();

        usuarioPrueba2 = Usuario.builder()
                .nombre("Camila")
                .email("kkssn@kstm.com")
                .fecha_ingreso(LocalDate.of(2022,1,3))
                .estado(0)
                .build();
    }

    @DisplayName("Test para crear un usuario")
    @Test
    @Rollback
    void testSaveUser() throws ResourceNotFoundException {

        //given
        given(usuarioRepository.findByEmail(usuarioPrueba1.getEmail()))
                .willReturn(Optional.empty());
        given(usuarioRepository.save(usuarioPrueba1))
                .willReturn(usuarioPrueba1);

        //When
        Usuario usuarioCreado = usuarioService.saveUser(usuarioPrueba1);

        //Then
        assertThat(usuarioCreado).isNotNull();
    }

    @DisplayName("Test para listar usuarios")
    @Test
    @Rollback
    void testListarUsuario(){
        given(usuarioRepository.findAll())
                .willReturn(Arrays.asList(usuarioPrueba1, usuarioPrueba2));

        //when
        List<Usuario> usuarios = usuarioService.getAllUsers();

        //then
        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isEqualTo(2);
    }

    @DisplayName("Test para retornar una lista vacia")
    @Test
    @Rollback
    void testListUsersEmpty(){
        //given

        given(usuarioRepository.findAll()).
                willReturn(Collections.emptyList());

        //when
        List<Usuario> listUsers = usuarioService.getAllUsers();

        //then
        assertThat(listUsers).isEmpty();
    }

    @DisplayName("Test para obtener un usuario por ID")
    @Test
    @Rollback
    void testObtenerEmpleadoPorId(){
        //given
        usuarioPrueba1.setId(1);
        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuarioPrueba1));

        //when
        Optional<Usuario> usuarioGuardadoOptional = usuarioService.getUserById(usuarioPrueba1.getId());
        Usuario usuarioGuardado = null;
        if (usuarioGuardadoOptional.isPresent()) {
            usuarioGuardado = usuarioGuardadoOptional.get();
        }

        //then
        assertThat(usuarioGuardado).isNotNull();

    }

    @DisplayName("Test para actualizar un usuario")
    @Test
    @Rollback
    void testActualizarUsuario(){
        //given
        given(usuarioRepository.save(usuarioPrueba1)).willReturn(usuarioPrueba1);
        usuarioPrueba1.setEmail("chr2@gmail.com");
        usuarioPrueba1.setNombre("Christian Raul");
        usuarioPrueba1.setFecha_ingreso(LocalDate.of(2021,3,29));

        //when
        Usuario usuarioActualizado  = usuarioService.updateUser(usuarioPrueba1);

        //then
        assertThat(usuarioActualizado.getEmail()).isEqualTo("chr2@gmail.com");
        assertThat(usuarioActualizado.getNombre()).isEqualTo("Christian Raul");
    }

    @DisplayName("Test para eliminar un usuario")
    @Test
    @Rollback
    void testEliminarEmpleado(){
        //given
        long usuarioId = 1L;
        willDoNothing().given(usuarioRepository).deleteById(usuarioId);

        //when
        usuarioService.eliminarUsuario(usuarioId);

        //then
        verify(usuarioRepository,times(1)).deleteById(usuarioId);
    }

}
