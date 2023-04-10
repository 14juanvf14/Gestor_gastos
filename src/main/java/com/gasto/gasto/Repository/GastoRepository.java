package com.gasto.gasto.Repository;

import com.gasto.gasto.Modelo.Gasto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository extends CrudRepository<Gasto, Long> {
}
