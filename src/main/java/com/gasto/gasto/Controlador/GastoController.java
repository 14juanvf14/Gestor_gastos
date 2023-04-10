package com.gasto.gasto.Controlador;

import com.gasto.gasto.Modelo.Gasto;
import com.gasto.gasto.Service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gasto> findById(@PathVariable Long id) {
        Optional<Gasto> gasto = Optional.ofNullable(gastoService.findById(id));
        return gasto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Gasto> save(@RequestBody Gasto gasto) {
        Gasto savedGasto = gastoService.save(gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGasto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Gasto> update(@PathVariable Long id, @RequestBody Gasto gasto) {
        Optional<Gasto> currentGasto = Optional.ofNullable(gastoService.findById(id));
        if (currentGasto.isPresent()) {
            Gasto updatedGasto = currentGasto.get();
            updatedGasto.setFecha(gasto.getFecha());
            updatedGasto.setMonto(gasto.getMonto());
            updatedGasto.setDescripcion(gasto.getDescripcion());
            gastoService.save(updatedGasto);
            return new ResponseEntity<>(updatedGasto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Gasto> gasto = Optional.ofNullable(gastoService.findById(id));
        if (gasto.isPresent()) {
            gastoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
