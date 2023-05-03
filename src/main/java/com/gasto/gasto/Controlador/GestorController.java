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

/**
 * Controlador que maneja las solicitudes relacionadas con los gestores de la aplicación.
 * @author Juan Vasquez
 * @version 1.0
 * @since 29/04/2023
 * @see com.gasto.gasto.Modelo.Gestor
 * @see com.gasto.gasto.Service.GestorService
 * @see com.gasto.gasto.Excepciones.RequestException
 */

@RestController
@RequestMapping("/gestor")
public class GestorController {

    /**
     * Servicio para la entidad Gestor.
     */
    @Autowired
    private GestorService gestorService;

    /**
     * Constructor que recibe un servicio de Gestor.
     *
     * @param gestorService Servicio de Gestor a utilizar.
     */
    public GestorController(GestorService gestorService) {
        this.gestorService = gestorService;
    }

    /**
     * find all:
     * Obtiene todos los gestores de la base de datos.
     *
     * @return ResponseEntity con una lista de Gestor y el código de estado HTTP correspondiente.
     * @throws RequestException si no se encuentran gestores en la base de datos.
     */
    @GetMapping
    public ResponseEntity<List<Gestor>> findAll() {
        if (gestorService.getAll().isEmpty()) {
            throw new RequestException("G-106A", HttpStatus.NOT_FOUND, "No se encontraron gestores en la base de datos");
        }
        List<Gestor> gestores = gestorService.getAll();
        return ResponseEntity.ok(gestores);
    }

    /**
     * Find by id
     * Obtiene un gestor de la base de datos por su identificador unico.
     *
     * @param id Identificador del gestor a buscar.
     * @return ResponseEntity con el Gestor encontrado y el código de estado HTTP correspondiente.
     * @throws RequestException si no se encuentra un gestor con el ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Gestor> findById(@PathVariable Long id) {
        Optional<Gestor> gestor = gestorService.findById(id);
        if (gestor.isPresent()) {
            return new ResponseEntity<>(gestor.get(), HttpStatus.OK);
        } else {
            throw  new RequestException("G-106B", HttpStatus.NOT_FOUND, "No se encontró el gestor con el ID proporcionado");
        }
    }


    /**
     * Save:
     * Guarda un nuevo Gestor en la base de datos.
     *
     * @param gestor Gestor a guardar.
     * @return ResponseEntity con el Gestor guardado y el código de estado HTTP correspondiente.
     * @throws RequestException si el ID del Gestor ya existe en la base de datos, si el nombre está vacío,
     * si el email no es válido o está vacío, o si el rol no es Administrador o Gestor.
     */
    @PostMapping
    public ResponseEntity<Gestor> save(@RequestBody @NotNull Gestor gestor) {
        if(gestorService.findById(gestor.getId()).isPresent()){
            throw new RequestException("G-101",HttpStatus.BAD_REQUEST,"El id de gestor ya se encuentra en la base");
        }
        if(gestor.getNombre().isEmpty()){
            throw new RequestException("G-104",HttpStatus.BAD_REQUEST,"El nombre no puede estar vacio");
        }
        if (gestor.getEmail() == null || gestor.getEmail().equals("")){
            throw new RequestException("G-102A",HttpStatus.BAD_REQUEST,"El e-mail ingresado no es valido o esta vacio");
        }
        if (!gestor.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            throw new RequestException("G-102B", HttpStatus.BAD_REQUEST, "El correo electrónico ingresado no es válido");
        }
        if (!Objects.equals(gestor.getRol(), "Administrador") && !Objects.equals(gestor.getRol(), "Gestor")){
            throw new RequestException("G-103",HttpStatus.BAD_REQUEST, "El rol solo puede ser Administrador o Gestor");
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza un Gestor existente en la base de datos por su identificador unico.
     *
     * @param id identificador del Gestor a actualizar.
     * @param gestor Gestor con los datos actualizados.
     * @return ResponseEntity con el Gestor actualizado y el código de estado HTTP correspondiente.
     * @throws RequestException si no se encuentra un Gestor con el ID proporcionado, si el nombre está vacío,
     * si el email no es válido o está vacío, o si el rol no es Administrador o Gestor.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Gestor> update(@PathVariable Long id, @RequestBody Gestor gestor) {
        Optional<Gestor> currentGestor = gestorService.findById(id);
        if (currentGestor.isPresent()) {
            if(gestor.getNombre().isEmpty()){
                throw new RequestException("G-104",HttpStatus.BAD_REQUEST,"El nombre no puede estar vacio");
            }
            if (gestor.getEmail() == null || gestor.getEmail().equals("")){
                throw new RequestException("G-102A",HttpStatus.BAD_REQUEST,"El e-mail ingresado no es valido o esta vacio");
            }
            if (!gestor.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
                throw new RequestException("G-102B", HttpStatus.BAD_REQUEST, "El correo electrónico ingresado no es válido");
            }
            if (!Objects.equals(gestor.getRol(), "Administrador") && !Objects.equals(gestor.getRol(), "Gestor")){
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

    /**
     * Delete by id
     * Elimina un Gestor existente en la base de datos por su Identificador.
     *
     * @param id Identificador del Gestor a eliminar.
     * @return ResponseEntity con el código de estado HTTP correspondiente.
     * @throws RequestException si no se encuentra un Gestor con el ID proporcionado.
     */
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
