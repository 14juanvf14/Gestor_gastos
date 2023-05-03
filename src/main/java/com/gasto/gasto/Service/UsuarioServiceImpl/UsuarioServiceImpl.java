package com.gasto.gasto.Service.UsuarioServiceImpl;

import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Repository.UsuarioRepository;
import com.gasto.gasto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz {@link UsuarioService} que proporciona servicios relacionados con los usuario.
 * @author Juan vasquez
 * @since 29/04/2023
 * @version 1.0
 */

@Service
public class UsuarioServiceImpl implements UsuarioService {

    /**
     * usuario repository:
     * Es la inyección de dependencias que corresponde al repositorio de Usuarios
     *
     */
    @Autowired
    private UsuarioRepository usuarioRepository;
    /**
     * save user
     * Permite guardar un objeto "usuario" en la base de datos
     *
     * @param usuario usuario que se desea aguardar
     * @return {@link Usuario} retorna el usuario que es guardado
     * @see Usuario
     */
    @Override
    public Usuario saveUser(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * get all users
     * Obtiene todos los usuarios de la base de datos
     *
     * @return {@link List} retorna un listado con los usuarios
     * @see List
     * @see Usuario
     */
    @Override
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    /**
     * get user by id
     * Obtiene un objeto "usuario" por su identificador unico
     *
     * @param id identificador el usuario a buscar
     * @return {@link Optional} retorna el objeto usuario si existe en la base de datos o
     * un objeto vacio si no existe
     * @see Optional
     * @see Usuario
     */
    @Override
    public Optional<Usuario> getUserById(long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * update user
     * Actualiza un usuario en la base de datos
     *
     * @param usuarioActualizado objeto usuario actualizado
     * @return {@link Usuario} el objeto usuario que se actualizo
     * @see Usuario
     */
    @Override
    public Usuario updateUser(Usuario usuarioActualizado) {
        return usuarioRepository.save(usuarioActualizado);
    }

    /**
     * Eliminar usuario
     * Elimina un usuario de la base de datos por su identificador unico
     *
     * @param id identificador del usuario que se desea eliminar
     */
    @Override
    public void eliminarUsuario(long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * get user by email
     * Obtiene el usuario por su e-mail
     *
     * @param email email del usuario a buscar
     * @return {@link Optional} retorna un objeto usuario si existe,
     * si no retorna un objeto vacio
     * @see Optional
     * @see Usuario
     */
    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


}
