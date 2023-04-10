
package com.gasto.gasto.Controlador;

import com.gasto.gasto.Excepciones.ResourceNotFoundException;
import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario saveUser(@RequestBody Usuario usuario) throws ResourceNotFoundException {
        return usuarioService.saveUser(usuario);
    }

    @GetMapping
    public List<Usuario> verUsuarios(){
        return usuarioService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioID(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.getUserById(id);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario){
        return usuarioService.getUserById(id)
                .map(usuarioGuardado -> {
                    usuarioGuardado.setEstado(usuario.getEstado());
                    usuarioGuardado.setEmail(usuario.getEmail());
                    usuarioGuardado.setNombre(usuario.getNombre());
                    usuarioGuardado.setFecha_ingreso(usuario.getFecha_ingreso());

                    Usuario usuarioActualizado = usuarioService.updateUser(usuarioGuardado);
                    return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
                })
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") long usuarioId){
        usuarioService.eliminarUsuario(usuarioId);
        return new ResponseEntity<String>("Usuario eliminado correctamente", HttpStatus.OK);
    }
}

