package com.gasto.gasto.Service.GestorServiceImpl;

import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Repository.GestorRepository;
import com.gasto.gasto.Service.GestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz {@link GestorService} que proporciona servicios relacionados con los gestores.
 * @author Juan vasquez
 * @since 29/04/2023
 * @version 1.0
 */

@Service
public class GestorServiceImpl implements GestorService {
    /**
     * gestor repository:
     * Es una inyección de dependencia del repositorio de gestores
     */
    @Autowired
    private GestorRepository gestorRepository;


    /**
     * get all:
     * Obtiene una lista con los gestores en la base de datos
     *
     * @return {@link List} retorna una lista con los gestores de la base de datos
     * @see List
     * @see Gestor
     */
    @Override
    public List<Gestor> getAll() {
        return gestorRepository.findAll();
    }

    /**
     * find by id:
     * Busca un objeto "Gestor" en la base de datos a partir de su
     * identificador unico
     *
     * @param id identificador del gestor que se desea buscar
     * @return {@link Optional} Retorna un objeto "Gestor"
     * @see Optional
     * @see Gestor
     */
    @Override
    public Optional<Gestor> findById(Long id) {
        return gestorRepository.findById(id);
    }

    /**
     * save:
     * Guarda un objeto "gestor" en la base de datos
     *
     * @param gestor gestor que se desea agregar
     * @return {@link Gestor} retorna el gestor que se agrego
     * @see Gestor
     */
    @Override
    public Gestor save(Gestor gestor) {
        return gestorRepository.save(gestor);
    }

    /**
     * delete by id
     * Elimina un gestor de la base de datos con su identificador unico
     *
     * @param id identificador el gestor a eliminar
     */
    @Override
    public void deleteById(Long id) {
        gestorRepository.deleteById(id);
    }

    /**
     * updategestor
     * Actualiza un objeto "Gestor" en la base de datos
     *
     * @param gestorActualizado se ingresa el objeto gestor actualizado
     * @return {@link Gestor} retorna el objeto gestor ya actualizado
     * @see Gestor
     */
    @Override
    public Gestor updategestor(Gestor gestorActualizado) {
        return gestorRepository.save(gestorActualizado);
    }

    /**
     * get user by email
     * Obtiene un usuario por su dirección de correo electrónico.
     *
     * @param email la dirección de correo electrónico del gestor a buscar
     * @return {@link Optional} que contiene el gestor encontrado, o vacío si no se encuentra el usuario
     * @see Optional
     * @see Gestor
     */
    @Override
    public Optional<Gestor> getGestorByEmail(String email){
        return gestorRepository.findByEmail(email);
    }
}
