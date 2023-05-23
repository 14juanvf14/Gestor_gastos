package com.gasto.gasto.Service;

import com.gasto.gasto.Modelo.Gestor;

import java.util.List;
import java.util.Optional;

/**
 *  gestor service
 *  Interfaz que define los métodos para el servicio de gestores.
 * @author Juan Vasquez
 * @since 29/04/2023
 * @version 1.0
 *
 */
public interface GestorService {
    /**
     * get all
     * Obtiene una lista de todos los gestores de la base de datos.
     *
     * @return {@link List} una lista de gestores
     * @see List
     * @see Gestor
     */
    List<Gestor> getAll();
    /**
     * find by id
     * Obtiene un gestor por su identificador único.
     *
     * @param id el identificador del gestor a buscar
     * @return {@link Optional} Optional que contiene el gestor encontrado, o vacío si no se encuentra el gestor
     * @see Optional
     * @see Gestor
     */
    Optional<Gestor> findById(Long id);
    /**
     * saveGestor
     * Guarda un gestor en la base de datos.
     *
     * @param gestor el gestor a guardar
     * @return {@link Gestor} retorna el gestor guardado
     * @see Gestor
     */
    Gestor save(Gestor gestor);
    /**
     * delete by id
     * Elimina un gestor de la base de datos por su identificador único.
     *
     * @param id id identificador unico del gestor a eliminar
     */
    void deleteById(Long id);
    /**
     * updategestor
     * Permite actualizar un gestor de la base de datos.
     *
     * @param gestorActualizado ingresa gestor con datos actualizados
     * @return {@link Gestor} retorna gestor que ha sido actualizado
     * @see Gestor
     */
    Gestor updategestor(Gestor gestorActualizado);


    /**
     * get user by email
     * Obtiene un usuario por su dirección de correo electrónico.
     *
     * @param email la dirección de correo electrónico del gestor a buscar
     * @return {@link Optional} que contiene el gestor encontrado, o vacío si no se encuentra el usuario
     * @see Optional
     * @see Gestor
     */
    Optional<Gestor> getGestorByEmail(String email);
}
