package com.gasto.gasto.Service;

import com.gasto.gasto.Modelo.Usuario;

import java.util.List;
import java.util.Optional;

/**
 *  Usuario service
 *  Esta es una interfaz que manejan los servicios o la
 *  logica de negocio para la entidad y controlador Usuario
 *
 *  <p>
 *      Usamos instancias de la clase de Usuario.java para objetos que permitan manejarse hacia
 *      la capa de persistencia
 *  </p>
 *  <p>
 *      La libreria util nos permite manejar listas para algunos metodos de la interfaz y optional que
 *      retornara los datos si los mismos estan presente desde la capa de persistencia
 *  </p>
 *
 *  @author Juan Vasquez
 *  @since 29/04/2023
 *  @version 1.0
 *
 */

public interface UsuarioService {

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param usuario el usuario a guardar
     * @return {@link Usuario} el usuario guardado
     * @see Usuario
     */
    Usuario saveUser(Usuario usuario);


    /**
     *  get all users  Obtiene una lista de todos los usuarios de la base de datos.
     *
     * @return {@link List} una lista de usuarios
     * @see List
     * @see Usuario
     */
    List<Usuario> getAllUsers();
    /**
     * get user by id
     * Obtiene un usuario por su identificador único.
     *
     * @param id id, el identificador del usuario a buscar
     * @return {@link Optional} que contiene el usuario encontrado, o vacío si no se encuentra el usuario
     * @see Optional
     * @see Usuario
     */
    Optional<Usuario> getUserById(long id);
    /**
     * update user
     * Actualiza un usuario en la base de datos.
     *
     * @param usuarioActualizado se brinda el usuario ya actualizado
     * @return {@link Usuario} retorna el usuario que ha sido actualizado
     * @see Usuario
     */
    Usuario updateUser(Usuario usuarioActualizado);
    /**
     * eliminar usuario
     * Elimina un usuario de la base de datos por su identificador único.
     *
     * @param id el identificador del usuario a eliminar
     */
    void eliminarUsuario(long id);

    /**
     * get user by email
     * Obtiene un usuario por su dirección de correo electrónico.
     *
     * @param email la dirección de correo electrónico del usuario a buscar
     * @return {@link Optional} que contiene el usuario encontrado, o vacío si no se encuentra el usuario
     * @see Optional
     * @see Usuario
     */
    Optional<Usuario> getUserByEmail(String email);


}
