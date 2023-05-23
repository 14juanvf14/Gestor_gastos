package com.gasto.gasto.Repository;

import com.gasto.gasto.Modelo.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *  Gestor repository
 *  Este repositorio es una interfaz que extiende todas las operaciones CRUD de
 *  la clase JPA Repository para gestores, lo cual ocurre en la capa de persistencia.
 *
 *  @author Juan Vasquez
 *  @since 29/04/2023
 *  @version 1.0
 *
 */
public interface GestorRepository extends JpaRepository<Gestor, Long> {
    Optional<Gestor> findByEmail(String email);
}
