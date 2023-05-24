package com.gasto.gasto.Controlador;


import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.AuthResponse;
import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Service.GestorService;
import com.gasto.gasto.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Clase Auth Controller:
 * Maneja la validación de credenciales de la aplicación
 * @author Juan Vasquez
 * @version 1.0
 * @since 29/04/2023
 */
@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private GestorService gestorService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping
    public ResponseEntity<AuthResponse> verificar_Credenciales(@RequestBody Gestor gestor) {
        Optional<Gestor> currentGestor = gestorService.getGestorByEmail(gestor.getEmail());
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (gestor.getEmail() == null || gestor.getEmail().equals("")){
            throw new RequestException("123",HttpStatus.BAD_REQUEST,"Error en correo");
        }
        if (gestor.getPassword() == null || gestor.getPassword().equals("Error en clave")){
            throw new RequestException("123",HttpStatus.BAD_REQUEST, "");
        }
        if (!gestor.getEmail().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b")) {
            throw new RequestException("G-102B", HttpStatus.BAD_REQUEST, "El correo electrónico ingresado no es válido");
        }
        if (currentGestor.isPresent()) {
            Gestor gestorExiste = currentGestor.get();
            if(argon2.verify(gestorExiste.getPassword(),gestor.getPassword())){
                String tokenJWT = jwtUtil.create(String.valueOf(gestorExiste.getId()),gestorExiste.getEmail());
                String rol = Objects.equals(gestorExiste.getRol(), "Administrador") ? "Administrador" : "Gestor";
                AuthResponse response = new AuthResponse(tokenJWT, rol, gestorExiste.getNombre());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else {
                throw new RequestException("G-106B", HttpStatus.NOT_FOUND, "La contraseña es incorrecta");
            }
        } else {
            throw new RequestException("G-106B", HttpStatus.NOT_FOUND, "No existe gestor con la dirección de e-mail indicada");
        }
    }

}
