package com.gasto.gasto.Controlador;


import com.gasto.gasto.Excepciones.RequestException;
import com.gasto.gasto.Modelo.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice  {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Error> RuntimeExceptionHandler(RuntimeException ex){
        Error error = Error.builder().code("P-500").error_mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<Error> RequestExceptionHandler(RequestException ex){
        Error error = Error.builder().code(ex.getMessage()).error_mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getStatus());
    }

}
