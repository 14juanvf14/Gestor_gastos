package com.gasto.gasto.Repository;

import com.gasto.gasto.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  Usuario repository
 *  Este repositorio es una interfaz que extiende todas las operaciones CRUD de
 *  la clase JPA Repository para los usuarios, lo cual ocurre en la capa de persistencia.
 *
 *  @author Juan Vasquez
 *  @since 29/04/2023
 *  @version 1.0
 *
 */


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
