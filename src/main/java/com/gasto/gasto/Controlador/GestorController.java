package com.gasto.gasto.Controlador;

import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Service.GestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
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
        // Agrega excepción if aquí
        if (gestorService.getAll().isEmpty()) {
            throw new RequestException("G-106A", HttpStatus.NOT_FOUND, "No se encontraron gestores en la base de datos");
        }
        return gestorService.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Gestor> findById(@PathVariable Long id) {
        Optional<Gestor> gestor = gestorService.findById(id);
        if (gestor.isPresent()) {
            return new ResponseEntity<>(gestor.get(), HttpStatus.OK);
        } else {
            throw  new RequestException("G-106B", HttpStatus.NOT_FOUND, "No se encontró el gestor con el ID proporcionado");
        }
    }



    @PostMapping
    public ResponseEntity<Gestor> save(@RequestBody @NotNull Gestor gestor) {
        if(gestorService.findById(gestor.getId()).isPresent()){
            throw new RequestException("G-101",HttpStatus.BAD_REQUEST,"El id de gestor ya se encuentra en la base");
        }
        if(gestor.getNombre().isEmpty()){
            throw new RequestException("G-104",HttpStatus.BAD_REQUEST,"El nombre no puede estar vacio");
        }
        if (gestor.getEmail() == null || gestor.getEmail().equals("")){
            throw new RequestException("U-102A",HttpStatus.BAD_REQUEST,"El e-mail ingresado no es valido o esta vacio");
        }
        if (!gestor.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            throw new RequestException("U-102B", HttpStatus.BAD_REQUEST, "El correo electrónico ingresado no es válido");
        }
        if (!Objects.equals(gestor.getRol(), "Administrador") || !Objects.equals(gestor.getRol(), "Gestor")){
            throw new RequestException("G-103",HttpStatus.BAD_REQUEST, "El rol solo puede ser Administrador o Gestor");
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gestor> update(@PathVariable Long id, @RequestBody Gestor gestor) {
        Optional<Gestor> currentGestor = gestorService.findById(id);
        if (currentGestor.isPresent()) {
            if(gestor.getNombre().isEmpty()){
                throw new RequestException("G-104",HttpStatus.BAD_REQUEST,"El nombre no puede estar vacio");
            }
            if (gestor.getEmail() == null || gestor.getEmail().equals("")){
                throw new RequestException("U-102A",HttpStatus.BAD_REQUEST,"El e-mail ingresado no es valido o esta vacio");
            }
            if (!gestor.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
                throw new RequestException("U-102B", HttpStatus.BAD_REQUEST, "El correo electrónico ingresado no es válido");
            }
            if (!Objects.equals(gestor.getRol(), "Administrador") || !Objects.equals(gestor.getRol(), "Gestor")){
                throw new RequestException("G-103",HttpStatus.BAD_REQUEST, "El rol solo puede ser Administrador o Gestor");
            }
            Gestor updatedGestor = currentGestor.get();
            updatedGestor.setNombre(gestor.getNombre());
            updatedGestor.setEmail(gestor.getEmail());
            updatedGestor.setPassword(gestor.getPassword());
            gestorService.save(updatedGestor);
            return new ResponseEntity<>(updatedGestor, HttpStatus.OK);
        } else {
            throw new RequestException("G-106B", HttpStatus.NOT_FOUND, "No se encontró el gestor con el ID proporcionado");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Gestor> gestor = gestorService.findById(id);
        if (gestor.isPresent()) {
            gestorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new RequestException("G-107", HttpStatus.NOT_FOUND, "No se encontró el gestor con el ID proporcionado");
        }
    }
}
