package com.gasto.gasto.Service;

import com.gasto.gasto.Modelo.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario saveUser(Usuario usuario);
    List<Usuario> getAllUsers();
    Optional<Usuario> getUserById(long id);
    Usuario updateUser(Usuario usuarioActualizado);
    void eliminarUsuario(long id);

    Optional<Usuario> getUserByEmail(String email);


}
