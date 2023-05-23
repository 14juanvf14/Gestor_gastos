
package com.gasto.gasto.Controlador;

import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Service.UsuarioService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controlador que maneja las solicitudes relacionadas con los usuarios de la aplicación.
 * @author Juan Vasquez
 * @version 1.0
 * @since 29/04/2023
 * @see com.gasto.gasto.Modelo.Usuario
 * @see com.gasto.gasto.Service.UsuarioService
 * @see com.gasto.gasto.Excepciones.RequestException
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthController authController;


    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario El objeto de tipo Usuario que contiene los datos del usuario a guardar.
     * @return Un objeto ResponseEntity con estado HTTP 200 OK si el usuario se guarda exitosamente.
     * @throws RequestException Sí se producen errores en la validación de los datos del usuario.
     */

    @PostMapping
    public ResponseEntity<Usuario> saveUser(@RequestHeader(value = "Authorization") String token, @RequestBody @NotNull Usuario usuario){
        LocalDate fechaActual = LocalDate.now();
        if(!authController.validarSesion(token)){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        if(usuario.getId()==null){
            throw new RequestException("",HttpStatus.BAD_REQUEST,"El identificador esta vacio");
        }
        if(usuario.getFecha_ingreso()==null){
            throw new RequestException("",HttpStatus.BAD_REQUEST,"La fecha no puede estar vacia");
        }
        if (usuario.getEmail() == null || usuario.getEmail().equals("")){
            throw new RequestException("U-102A",HttpStatus.BAD_REQUEST,"El e-mail ingresado no es valido o esta vacio");
        }
        if(usuarioService.getUserById(usuario.getId()).isPresent()){
            throw new RequestException("U-101", HttpStatus.BAD_REQUEST,"Identificación de usuario ya existe en la base");
        }
        if (!usuario.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            throw new RequestException("U-102B", HttpStatus.BAD_REQUEST, "El correo electrónico ingresado no es válido");
        }
        if(usuarioService.getUserByEmail(usuario.getEmail()).isPresent()){
            throw new RequestException("U-102C", HttpStatus.BAD_REQUEST, "El email ya existe en la base de datos");
        }
        if (usuario.getEstado() < 0 || usuario.getEstado() > 1){
            throw new RequestException("U-103",HttpStatus.BAD_REQUEST,"El estado solo puede ser 1 o 0");
        }
        if(usuario.getNombre().isEmpty()){
            throw new RequestException("U-104",HttpStatus.BAD_REQUEST,"El nombre no puede estar vacio");
        }
        if (usuario.getFecha_ingreso().isAfter(fechaActual)) {
            throw new RequestException("U-105", HttpStatus.BAD_REQUEST, "La fecha de nacimiento no puede ser una fecha futura");
        }

        usuarioService.saveUser(usuario);
        return ResponseEntity.ok().build();
    }

    /**
     * ver usuarios:
     * Obtiene la lista de todos los usuarios registrados en la base de datos.
     *
     * @return Un objeto ResponseEntity que contiene la lista de usuarios si se encuentran en la base de datos.
     * @throws RequestException Si no se encuentran usuarios en la base de datos.
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> verUsuarios(@RequestHeader(value = "Authorization") String token) {
        if(!authController.validarSesion(token)){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        List<Usuario> usuarios = usuarioService.getAllUsers();
        if (usuarios.isEmpty()) {
            throw new RequestException("U-106A", HttpStatus.NOT_FOUND, "No se encontraron usuarios");
        }
        else {
            return ResponseEntity.ok(usuarios);
        }
    }

    /**
     * busca usuario por id:
     * Obtiene un usuario de la base de datos por su Identificador.
     *
     * @param id Ientificador del usuario a buscar.
     * @return Un objeto ResponseEntity con el usuario encontrado y el código de estado HTTP correspondiente.
     * @throws RequestException Si el usuario no se encuentra en la base de datos.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioID(@RequestHeader(value="Authorization") String token, @PathVariable Long id){
        if(!authController.validarSesion(token)){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        Optional<Usuario> usuario = usuarioService.getUserById(id);
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            throw new RequestException("U-106B", HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    /**
     * Actualizar usuario
     * Actualiza un usuario existente en la base de datos por su identificador.
     *
     * @param id identinficador del usuario a actualizar.
     * @param usuario Usuario con los datos actualizados.
     * @return ResponseEntity con el usuario actualizado y el código de estado HTTP correspondiente.
     * @throws RequestException Si el usuario no se encuentra en la base de datos o si se producen errores en la validación de los datos del usuario.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestHeader(value = "Authorization") String token, @PathVariable("id") Long id, @RequestBody Usuario usuario) {
        if(!authController.validarSesion(token)){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        Optional<Usuario> usuarioGuardadoOptional = usuarioService.getUserById(id);

        LocalDate fechaActual = LocalDate.now();

        if (usuarioGuardadoOptional.isPresent()) {
            Usuario usuarioGuardado = usuarioGuardadoOptional.get();

            if (usuario.getEmail() == null || usuario.getEmail().equals("")){
                throw new RequestException("U-102A",HttpStatus.BAD_REQUEST,"El e-mail ingresado no es valido o esta vacio");
            }
            if (!usuario.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
                throw new RequestException("U-102B", HttpStatus.BAD_REQUEST, "El correo electrónico ingresado no es válido");
            }
            if(usuarioService.getUserByEmail(usuario.getEmail()).isPresent()){
                throw new RequestException("U-102C", HttpStatus.BAD_REQUEST, "El email ya existe en la base de datos");
            }
            if (usuario.getEstado() < 0 || usuario.getEstado() > 1){
                throw new RequestException("U-103",HttpStatus.BAD_REQUEST,"El estado solo puede ser Activo o inactivo");
            }
            if(usuario.getNombre().isEmpty()){
                throw new RequestException("U-104",HttpStatus.BAD_REQUEST,"El nombre no puede estar vacio");
            }
            if (usuario.getFecha_ingreso().isAfter(fechaActual)) {
                throw new RequestException("U-105", HttpStatus.BAD_REQUEST, "La fecha de ingreso no puede ser una fecha futura");
            }

            usuarioGuardado.setEstado(usuario.getEstado());
            usuarioGuardado.setNombre(usuario.getNombre());
            usuarioGuardado.setFecha_ingreso(usuario.getFecha_ingreso());
            usuarioGuardado.setEmail(usuario.getEmail());

            Usuario usuarioActualizado = usuarioService.updateUser(usuarioGuardado);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } else {
            throw new RequestException("U-106B", HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    /**
     * eliminar usuario:
     * Elimina un usuario existente en la base de datos por su ID.
     *
     * @param usuarioId ID del usuario a eliminar.
     * @return ResponseEntity con el mensaje de éxito y el código de estado HTTP correspondiente.
     * @throws RequestException Si el usuario no se encuentra en la base de datos.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarUsuario(@RequestHeader(value = "Authorization") String token, @PathVariable("id") long usuarioId){
        if(!authController.validarSesion(token)){
            throw new RequestException("", HttpStatus.FORBIDDEN, "Token no valido");
        }
        Optional<Usuario> usuario = usuarioService.getUserById(usuarioId);
        if (usuario.isPresent()) {
            usuarioService.eliminarUsuario(usuarioId);
            return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
        } else {
            throw new RequestException("U-106B", HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }
}

