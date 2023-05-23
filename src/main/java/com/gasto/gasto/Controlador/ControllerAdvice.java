package com.gasto.gasto.Controlador;


import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Esta clase es un controlador de excepciones que se encarga de manejar las excepciones que se lancen en los controladores de la aplicación.
 * @author Juan Vasquez
 * @version 1.0
 * @since 29/04/2023
 */

@RestControllerAdvice
public class ControllerAdvice  {
    /**
     * Maneja las excepciones de tipo RuntimeException y devuelve una respuesta HTTP de BadRequest.
     *
     * @param ex La excepción Runtime que se lanzó.
     * @return Una respuesta HTTP con un objeto Error que contiene información sobre el error.
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Error> RuntimeExceptionHandler(RuntimeException ex){
        Error error = Error.builder().code("P-500").error_mensaje(ex.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja las excepciones de tipo RequestException y devuelve una respuesta HTTP con el código de estado y el mensaje de error correspondiente.
     *
     * @param ex La excepción RequestException que se lanzó.
     * @return Una respuesta HTTP con un objeto Error que contiene información sobre el error.
     */
    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<Error> RequestExceptionHandler(RequestException ex){
        Error error = Error.builder().code(ex.getCode()).error_mensaje(ex.getMessage()).status(ex.getStatus()).build();
        return new ResponseEntity<>(error, ex.getStatus());
    }

}
