
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;



    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario El objeto de tipo Usuario que contiene los datos del usuario a guardar.
     * @return Un objeto ResponseEntity con estado HTTP 200 OK si el usuario se guarda exitosamente.
     * @throws RequestException Si se producen errores en la validación de los datos del usuario.
     */

    @PostMapping
    public ResponseEntity<Usuario> saveUser(@RequestBody @NotNull Usuario usuario){
        LocalDate fechaActual = LocalDate.now();

        if(usuarioService.getUserById(usuario.getId()).isPresent()){
            throw new RequestException("U-101", HttpStatus.BAD_REQUEST,"Identificación de usuario ya existe en la base");
        }
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
     * Obtiene la lista de todos los usuarios registrados en la base de datos.
     *
     * @return Un objeto ResponseEntity que contiene la lista de usuarios si se encuentran en la base de datos.
     * @throws RequestException Si no se encuentran usuarios en la base de datos.
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> verUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsers();
        if (usuarios.isEmpty()) {
            throw new RequestException("U-106A", HttpStatus.NOT_FOUND, "No se encontraron usuarios");
        }
        else {
            return ResponseEntity.ok(usuarios);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioID(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.getUserById(id);
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            throw new RequestException("U-106B", HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
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
                throw new RequestException("U-103",HttpStatus.BAD_REQUEST,"El estado solo puede ser 1 o 0");
            }
            if(usuario.getNombre().isEmpty()){
                throw new RequestException("U-104",HttpStatus.BAD_REQUEST,"El estado solo puede ser 1 o 0");
            }
            if (usuario.getFecha_ingreso().isAfter(fechaActual)) {
                throw new RequestException("U-105", HttpStatus.BAD_REQUEST, "La fecha de nacimiento no puede ser una fecha futura");
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

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") long usuarioId){
        Optional<Usuario> usuario = usuarioService.getUserById(usuarioId);
        if (usuario.isPresent()) {
            usuarioService.eliminarUsuario(usuarioId);
            return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
        } else {
            throw new RequestException("U-106B", HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.getUserByEmail(email);
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            throw new RequestException("U-107", HttpStatus.NOT_FOUND, "Usuario no encontrado por correo electrónico");
        }
    }
}

