package com.gasto.gasto.Controlador;

import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Service.UsuarioServiceIMP.UsuarioSIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Gestorgasto")
public class ControladorUsuario {

    @Autowired
    private UsuarioSIMP implement;

    @PostMapping
    @RequestMapping(value = "AgregarUsuario", method = RequestMethod.POST)
    public ResponseEntity<?> AgregarUsuario(@RequestBody Usuario usuario){
        Usuario usuarioAgregado = this.implement.agregarUsuario(usuario);
        return  ResponseEntity.status(HttpStatus.CREATED).body(usuarioAgregado);
    }

}
