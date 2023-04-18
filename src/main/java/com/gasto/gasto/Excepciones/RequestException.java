package com.gasto.gasto.Excepciones;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestException extends RuntimeException{
    private String code;
    private HttpStatus status;

    public RequestException(String code, HttpStatus status, String mensaje){
        super(mensaje);
        this.code = code;
        this.status = status;

    };
}
