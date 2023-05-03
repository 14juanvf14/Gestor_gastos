package com.gasto.gasto.Controlador;

import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.Gasto;
import com.gasto.gasto.Service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controlador que maneja las solicitudes relacionadas con los gastos de la aplicación.
 * @author Juan Vasquez
 * @version 1.0
 * @since 29/04/2023
 * @see com.gasto.gasto.Modelo.Gasto
 * @see com.gasto.gasto.Service.GastoService
 * @see com.gasto.gasto.Excepciones.RequestException
 */
@RestController
@RequestMapping("/gastos")
public class GastoController {
    /**
     * gasto service
     * Servicio para gasto
     */
    @Autowired
    private GastoService gastoService;

    /**
     * Find all:
     * Obtiene una lista de todos los gastos y devuelve una respuesta HTTP OK con la lista de gastos.
     *
     * @return Una respuesta HTTP con una lista de objetos Gasto.
     * @throws RequestException si no se encontraron gastos.
     */
    @GetMapping
    public ResponseEntity<List<Gasto>> findAll() {
        List<Gasto> gastos = gastoService.findAll();
        if(gastos.isEmpty()){
            throw new RequestException("E-106A",HttpStatus.NOT_FOUND,"No se encontraron gastos");
        }
        return ResponseEntity.ok(gastos);
    }

    /**
     * Find by Id:
     * Busca un gasto por su ID y devuelve una respuesta HTTP con el objeto Gasto si se encuentra.
     *
     * @param id El ID del gasto que se desea buscar.
     * @return Una respuesta HTTP con un objeto Gasto.
     * @throws RequestException si no se encontró el gasto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gasto> findById(@PathVariable Long id) {
        Gasto gasto = gastoService.findById(id);
        if (gasto != null) {
            return new ResponseEntity<>(gasto, HttpStatus.OK);
        } else {
            throw new RequestException("E-106B",HttpStatus.NOT_FOUND,"No se encontro gasto con el ID");
        }
    }


    /**
     * Save:
     * Crea un nuevo gasto y devuelve una respuesta HTTP con el objeto Gasto creado.
     *
     * @param gasto El objeto Gasto que se desea crear.
     * @return Una respuesta HTTP con el objeto Gasto creado.
     * @throws RequestException si alguno de los campos requeridos del objeto Gasto es nulo o tiene un valor inválido.
     */
    @PostMapping
    public ResponseEntity<Gasto> save(@RequestBody Gasto gasto) {
        LocalDate fechaActual = LocalDate.now();
        if(gasto.getDescripcion() == null){
            throw new RequestException("E-102",HttpStatus.BAD_REQUEST,"la descripción no puede ser nula");
        }
        if(gasto.getMonto() <= 0){
            throw new RequestException("E-102",HttpStatus.BAD_REQUEST,"El monto no puede ser menor a cero");
        }
        if (gasto.getFecha().isAfter(fechaActual)) {
            throw new RequestException("E-105", HttpStatus.BAD_REQUEST, "La fecha de nacimiento no puede ser una fecha futura");
        }

        Gasto savedGasto = gastoService.save(gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGasto);
    }

    /**
     * update:
     * Actualiza un gasto existente y devuelve una respuesta HTTP con el objeto Gasto actualizado.
     *
     * @param id El ID del gasto que se desea actualizar.
     * @param gasto El objeto Gasto actualizado que se desea guardar.
     * @return Una respuesta HTTP con el objeto Gasto actualizado.
     * @throws RequestException si el gasto no se encuentra o alguno de los campos requeridos del objeto Gasto es nulo o tiene un valor inválido.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gasto> update(@PathVariable Long id, @RequestBody Gasto gasto) {
        Optional<Gasto> currentGasto = Optional.ofNullable(gastoService.findById(id));
        LocalDate fechaActual = LocalDate.now();
        if (currentGasto.isPresent()) {

            Gasto updatedGasto = currentGasto.get();
            if(gasto.getDescripcion() == null){
                throw new RequestException("E-102",HttpStatus.BAD_REQUEST,"la descripción no puede ser nula");
            }
            if(gasto.getMonto() <= 0){
                throw new RequestException("E-102",HttpStatus.BAD_REQUEST,"El monto no puede ser menor a cero");
            }
            if (gasto.getFecha().isAfter(fechaActual)) {
                throw new RequestException("E-105", HttpStatus.BAD_REQUEST, "La fecha de nacimiento no puede ser una fecha futura");
            }
            updatedGasto.setFecha(gasto.getFecha());
            updatedGasto.setMonto(gasto.getMonto());
            updatedGasto.setDescripcion(gasto.getDescripcion());
            gastoService.save(updatedGasto);
            return new ResponseEntity<>(updatedGasto, HttpStatus.OK);
        } else {
            throw new RequestException("E-106B", HttpStatus.NOT_FOUND, "Gasto no encontrado");

        }
    }

    /**
     * delete by id:
     * Elimina un gasto por su ID y devuelve una respuesta HTTP sin contenido.
     *
     * @param id El ID del gasto que se desea eliminar.
     * @return Una respuesta HTTP sin contenido.
     * @throws RequestException si el gasto no se encuentra.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Gasto> gasto = Optional.ofNullable(gastoService.findById(id));
        if (gasto.isPresent()) {
            gastoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new RequestException("E-106B", HttpStatus.NOT_FOUND, "Gasto no encontrado");
        }
    }

}
