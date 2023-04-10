package com.gasto.gasto.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.gasto.gasto.Modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class UsuarioRepositoryTest {

    private Usuario usuarioPrueba1, usuarioPrueba2;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    @Test
    @DisplayName("Test que permite crear usuario")
    @Rollback
    void testCrearUsuario() {

        //given
        //when
        Usuario userAgregado = usuarioRepository.save(usuarioPrueba1);
        //then
        assertThat(userAgregado).isNotNull();
        assertThat(userAgregado.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test para listar usuarios")
    @Rollback
    void  testVerUsuarios(){
        //given
        Usuario userAgregado1 = usuarioRepository.save(usuarioPrueba1);
        Usuario userAgregado2 = usuarioRepository.save(usuarioPrueba2);

        //when
        List<Usuario> listaUsuarios = (List<Usuario>) usuarioRepository.findAll();

        //then
        assertThat(listaUsuarios).isNotNull();
        assertThat(listaUsuarios.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test para buscar usuario por ID")
    @Rollback
    void testBuscarUsuarioID(){
        Usuario userAgregado1;
        usuarioRepository.save(usuarioPrueba1);
        if(usuarioRepository.findById(usuarioPrueba1.getId()).isPresent()){
            userAgregado1 = usuarioRepository.findById(usuarioPrueba1.getId()).get();
        }
        else{
            userAgregado1 = null;
        }
        assertThat(userAgregado1).isNotNull();
    }

    @Test
    @DisplayName("Test para actualizar usuario por ID")
    @Rollback
    void testUpdateUser() {
        usuarioRepository.save(usuarioPrueba1);
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioPrueba1.getId());
        if (optionalUsuario.isPresent()) {
            Usuario userAgregado1 = optionalUsuario.get();
            userAgregado1.setEmail("teta@plot.xyz");
            userAgregado1.setEstado(0);
            userAgregado1.setNombre("Sebastian");
            Usuario usuarioActualizado = usuarioRepository.save(userAgregado1);

            assertThat(usuarioActualizado.getEmail()).isEqualTo("teta@plot.xyz");
            assertThat(usuarioActualizado.getEstado()).isEqualTo(0);
            assertThat(usuarioActualizado.getNombre()).isEqualTo("Sebastian");
        } else {
            fail("El usuario con id " + usuarioPrueba1.getId() + " no se encontró en la base de datos");
        }
    }

    @Test
    @DisplayName("Test para eliminar usuario por ID")
    @Rollback
    void testEliminarPorID(){
        usuarioRepository.save(usuarioPrueba1);
        usuarioRepository.deleteById(usuarioPrueba1.getId());
        Optional<Usuario> usuarioEliminado = usuarioRepository.findById(usuarioPrueba1.getId());
        assertThat(usuarioEliminado).isEmpty();
    }

}




