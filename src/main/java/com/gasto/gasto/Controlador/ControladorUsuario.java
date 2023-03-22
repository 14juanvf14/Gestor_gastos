
package com.gasto.gasto.Controlador;

import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Service.UsuarioServiceIMP.UsuarioSIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.ValidationException;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("Gestorgasto")
public class ControladorUsuario {

    @Autowired
    private UsuarioSIMP implement;

    @PostMapping
    @RequestMapping(value = "AgregarUsuario", method = RequestMethod.POST)
    public ResponseEntity<?> AgregarUsuario(@Valid @RequestBody Usuario usuario){
        Usuario usuarioAgregado = this.implement.agregarUsuario(usuario);
        return  ResponseEntity.status(HttpStatus.CREATED).body(usuarioAgregado);
    }

    @PutMapping
    @RequestMapping(value = "ModificarUsuario", method = RequestMethod.PUT)
    public ResponseEntity<?> ModificarUsuario(@Valid @RequestBody Usuario usuario){
        Usuario usuarioAgregado = this.implement.agregarUsuario(usuario);
        return  ResponseEntity.status(HttpStatus.CREATED).body(usuarioAgregado);
    }


    @GetMapping
    @RequestMapping(value = "VerUsuarios", method = RequestMethod.GET)
    public ResponseEntity<?> VerUsuarios(){
        List<Usuario> listaUsuarios = this.implement.verUsuarios();
        return  ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping
    @RequestMapping(value = "BuscaUsuarioID/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> BuscarUsuarioID(@PathVariable int id){
        Usuario usuario = this.implement.buscarUsuarioID(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping
    @RequestMapping(value = "EliminarUsuario/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> EliminarUsuario(@PathVariable int id){
        this.implement.eliminarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationException handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        String message = String.join(", ", errors);
        return new ValidationException(message);
    }
}

