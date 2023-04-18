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

@RestController
@RequestMapping("/gastos")
public class GastoController {
    @Autowired
    private GastoService gastoService;

    @GetMapping
    public ResponseEntity<List<Gasto>> findAll() {
        List<Gasto> gastos = gastoService.findAll();
        if(gastos.isEmpty()){
            throw new RequestException("E-106A",HttpStatus.NOT_FOUND,"No se encontraron gastos");
        }
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gasto> findById(@PathVariable Long id) {
        Gasto gasto = gastoService.findById(id);
        if (gasto != null) {
            return new ResponseEntity<>(gasto, HttpStatus.OK);
        } else {
            throw new RequestException("E-106B",HttpStatus.NOT_FOUND,"No se encontro gasto con el ID");
        }
    }


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
