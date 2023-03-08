package com.gasto.gasto.Repository;

import com.gasto.gasto.Modelo.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository <Usuario, Integer> {
}
