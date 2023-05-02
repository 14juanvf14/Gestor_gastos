package com.gasto.gasto.Repository;

import com.gasto.gasto.Modelo.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  Gasto repository
 *  Este repositorio es una interfaz que extiende todas las operaciones CRUD de
 *  la clase JPA Repository para gastos, lo cual ocurre en la capa de persistencia.
 *
 */
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

}
