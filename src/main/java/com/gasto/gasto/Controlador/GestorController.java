package com.gasto.gasto.Controlador;

import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Service.GestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/gestor")
public class GestorController {
    @Autowired
    private GestorService gestorService;

    public GestorController(GestorService gestorService) {
        this.gestorService = gestorService;
    }

    @GetMapping
    public List<Gestor> findAll() {
        return gestorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gestor> findById(@PathVariable Long id) {
        Optional<Gestor> gestor = gestorService.findById(id);
        return gestor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Gestor save(@RequestBody Gestor gestor) {
        return gestorService.save(gestor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gestor> update(@PathVariable Long id, @RequestBody Gestor gestor) {
        Optional<Gestor> currentGestor = gestorService.findById(id);
        if (currentGestor.isPresent()) {
            Gestor updatedGestor = currentGestor.get();
            updatedGestor.setNombre(gestor.getNombre());
            updatedGestor.setEmail(gestor.getEmail());
            updatedGestor.setPassword(gestor.getPassword());
            gestorService.save(updatedGestor);
            return new ResponseEntity<>(updatedGestor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Gestor> gestor = gestorService.findById(id);
        if (gestor.isPresent()) {
            gestorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
