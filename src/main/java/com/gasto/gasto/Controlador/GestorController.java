package com.gasto.gasto.Controlador;

import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Service.GestorService;
import com.gasto.gasto.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controlador que maneja las solicitudes relacionadas con los gestores de la aplicación springboot.
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

    @Autowired
    private JWTUtil jwtUtil;

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
    public ResponseEntity<List<Gestor>> findAll(@RequestHeader(value = "Authorization") @Nullable String token) {
        if(token==null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        if (jwtUtil.isTokenExpired(token)) {
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        String gestorID = jwtUtil.getKey(token);
        if (gestorID == null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
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
    public ResponseEntity<Gestor> findById(@RequestHeader(value = "Authorization") @Nullable String token, @PathVariable Long id) {
        if(token==null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        if (jwtUtil.isTokenExpired(token)) {
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        String gestorID = jwtUtil.getKey(token);
        if (gestorID == null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
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
    public ResponseEntity<Gestor> save(@RequestHeader(value = "Authorization") @Nullable String token, @RequestBody @NotNull Gestor gestor) {
        if(token==null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        if (jwtUtil.isTokenExpired(token)) {
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        String gestorID = jwtUtil.getKey(token);
        if (gestorID == null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        if (gestor.getId() == null){
            throw new RequestException("1", HttpStatus.BAD_REQUEST,"El id no puede estar vacio");
        }
        if(gestorService.findById(gestor.getId()).isPresent()){
            throw new RequestException("G-101",HttpStatus.BAD_REQUEST,"El id de gestor ya se encuentra en la base");
        }
        if (gestor.getPassword()=="" || gestor.getPassword()==null){
            throw new RequestException("2", HttpStatus.BAD_REQUEST, "La contraseña no puede ser vacia o nula");
        }
        if(gestor.getNombre().isEmpty()){
            throw new RequestException("G-104",HttpStatus.BAD_REQUEST,"El nombre no puede estar vacio");
        }
        if (gestor.getEmail() == null || gestor.getEmail().equals("")){
            throw new RequestException("G-102A",HttpStatus.BAD_REQUEST,"El e-mail ingresado no es valido o esta vacio");
        }
        if (gestorService.getGestorByEmail(gestor.getEmail()).isPresent()){
            throw new RequestException("3",HttpStatus.BAD_REQUEST,"El e-mail ya esta registrado en la base de datos");
        }
        if (!gestor.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            throw new RequestException("G-102B", HttpStatus.BAD_REQUEST, "El correo electrónico ingresado no es válido");
        }
        if (!Objects.equals(gestor.getRol(), "Administrador") && !Objects.equals(gestor.getRol(), "Gestor")){
            throw new RequestException("G-103",HttpStatus.BAD_REQUEST, "El rol solo puede ser Administrador o Gestor");
        }

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1, gestor.getPassword());
        gestor.setPassword(hash);
        gestorService.save(gestor);
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
    public ResponseEntity<Gestor> update(@RequestHeader(value = "Authorization") @Nullable String token, @PathVariable Long id, @RequestBody Gestor gestor) {
        if(token==null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        if (jwtUtil.isTokenExpired(token)) {
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        String gestorID = jwtUtil.getKey(token);
        if (gestorID == null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        if (id == null){
            throw new RequestException("1", HttpStatus.BAD_REQUEST,"El id no puede estar vacio");
        }
        if (id==99999 && !Objects.equals(gestor.getEmail(), "admin@gasto.com")){
            throw new RequestException("",HttpStatus.BAD_REQUEST,"Este es un administrador no modificable");
        }
        if (id==99999 && !Objects.equals(gestor.getRol(), "Administrador")){
            throw new RequestException("",HttpStatus.BAD_REQUEST,"Este es un administrador no modificable");
        }
        Optional<Gestor> currentGestor = gestorService.findById(id);
        Optional<Gestor> currentGestorEmail = gestorService.getGestorByEmail(gestor.getEmail());
        if (currentGestor.isPresent()) {
            if(gestor.getNombre().isEmpty()){
                throw new RequestException("G-104",HttpStatus.BAD_REQUEST,"El nombre no puede estar vacio");
            }
            if (gestor.getPassword()=="" || gestor.getPassword()==null){
                throw new RequestException("2", HttpStatus.BAD_REQUEST, "La contraseña no puede ser vacia o nula");
            }
            if (gestor.getEmail() == null || gestor.getEmail().equals("")){
                throw new RequestException("G-102A",HttpStatus.BAD_REQUEST,"El e-mail ingresado no es valido o esta vacio");
            }
            if (!gestor.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
                throw new RequestException("G-102B", HttpStatus.BAD_REQUEST, "El correo electrónico ingresado no es válido");
            }
            if (currentGestorEmail.isPresent() && !Objects.equals(currentGestorEmail.get().getId(), id)){
                throw new RequestException("3",HttpStatus.BAD_REQUEST,"El e-mail ya esta registrado en la base de datos");
            }
            if (!Objects.equals(gestor.getRol(), "Administrador") && !Objects.equals(gestor.getRol(), "Gestor")){
                throw new RequestException("G-103",HttpStatus.BAD_REQUEST, "El rol solo puede ser Administrador o Gestor");
            }
            Gestor updatedGestor = currentGestor.get();
            updatedGestor.setNombre(gestor.getNombre());
            updatedGestor.setEmail(gestor.getEmail());
            updatedGestor.setRol(gestor.getRol());
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String hash = argon2.hash(1,1024,1, gestor.getPassword());
            updatedGestor.setPassword(hash);
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
    public ResponseEntity<Void> deleteById(@RequestHeader(value = "Authorization") @Nullable String token,@PathVariable Long id) {
        if(token==null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        if (jwtUtil.isTokenExpired(token)) {
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        String gestorID = jwtUtil.getKey(token);
        if (gestorID == null){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        Optional<Gestor> gestor = gestorService.findById(id);
        if (id==99999){
            throw new RequestException("",HttpStatus.BAD_REQUEST,"No se puede eliminar a este administrador");
        }
        if (gestor.isPresent()) {
            gestorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new RequestException("G-107", HttpStatus.NOT_FOUND, "No se encontró el gestor con el ID proporcionado");
        }
    }
}

