package com.gasto.gasto.Service.GastoServiceImpl;

import com.gasto.gasto.Modelo.Gasto;
import com.gasto.gasto.Repository.GastoRepository;
import com.gasto.gasto.Service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la interfaz {@link GastoService} que proporciona servicios relacionados con los gastos.
 * @author Juan vasquez
 * @since 29/04/2023
 * @version 1.0
 */


@Service
public class GastoServiceImpl implements GastoService {
    /**
     * gasto repository
     * Es una inyección de dependencia del repositorio de gastos
     */
    @Autowired
    private GastoRepository gastoRepository;

    /**
     * find all
     * Opera con el repositorio para obtener la lista de gastos en la base
     * de datos
     *
     * @return {@link List} Devuelve una lista con los gastos existentes en la base de datos
     * @see List
     * @see Gasto
     */
    @Override
    public List<Gasto> findAll() {
        return gastoRepository.findAll();
    }

    /**
     * find by id
     * Permite buscar un objeto "gasto" en la capa de persistencia
     * usando el identificador unico para dicho objeto
     *
     * @param id identificador del gasto que se desea buscar
     * @return {@link Gasto}
     * @see Gasto
     */
    @Override
    public Gasto findById(Long id) {
        return gastoRepository.findById(id).orElse(null);
    }

    /**
     * save
     * Permite guardar un objeto "gasto" en la base de datos
     * haciendo uso de save del repositorio
     *
     * @param gasto gasto que se desea guardar
     * @return {@link Gasto} retorna el gasto que se guardo
     * @see Gasto
     */
    @Override
    public Gasto save(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    /**
     * delete by id:
     * Permite eliminar ub objeto "gasto" de la base de datos
     * usando su identificador unico
     *
     * @param id identificador del gasto que se desea eliminar
     */
    @Override
    public void deleteById(Long id) {
        gastoRepository.deleteById(id);
    }


    /**
     * Listar gastos por usuarios
     * Actualizar un objeto "gasto" en la base de datos
     *
     * @param id del usuario que se desea buscar
     * @return {@link Gasto}
     * @see Gasto
     */
    @Override
    public List<Gasto> findByUsuario(Long id) {
        return gastoRepository.findByUsuarioId(id);
    }
}
