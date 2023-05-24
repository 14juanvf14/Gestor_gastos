package com.gasto.gasto.Service;

import com.gasto.gasto.Modelo.Gasto;

import java.util.List;

/**
 *  gasto service
 *  Interfaz que define los metodos del servicio para gastos
 *
 * @author Juan Vasquez
 * @since 29/04/2023
 * @version 1.0
 *
 */
public interface GastoService {
    /**
     * find all
     * Este metodo lista los gastos generales del sistema
     * @return {@link List}Devuelve una lista con todos los gastos.
     * @see List
     * @see Gasto
     */
    List<Gasto> findAll();
    /**
     * find by id
     * Busca un objeto "Gasto" por su identificador único.
     *
     * @param id identificador el gasto que se desea buscar
     * @return {@link Gasto} Retorna un objeto que contiene los datos del gasto
     * @see Gasto
     */
    Gasto findById(Long id);
    /**
     * save
     * Guarda un objeto "Gasto" en la base de datos
     *
     * @param gasto ingresa un objeto gasto
     * @return {@link Gasto} retorna el objeto que se agrego
     * @see Gasto
     */
    Gasto save(Gasto gasto);
    /**
     * delete by id
     * Elimina un objeto "Gasto" de la base de datos partiendo de su identificador unico
     *
     * @param id identificador del gasto que se desea eliminar
     */
    void deleteById(Long id);
    /**
     * update gasto
     * Permite actualizar un objeto "gasto" en la base de datos
     *
     * @param gasto ingresa el objeto "gasto" actualizado
     * @return {@link Gasto} retorna el objeto ya actualizado
     * @see Gasto
     */
    List<Gasto> findByUsuario(Long id);

}
