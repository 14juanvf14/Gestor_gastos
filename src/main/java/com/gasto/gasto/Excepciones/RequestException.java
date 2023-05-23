package com.gasto.gasto.Excepciones;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * Excepción lanzada cuando una solicitud HTTP no se puede procesar correctamente.
 * Esta excepción contiene un código de error, un estado HTTP y un mensaje descriptivo.
 * @author Juan vasquez
 * @version 1.0
 * @since 29/04/2023
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RequestException extends RuntimeException{

    private String mensaje;
    /**
     * Código de error de la excepción.
     */
    private String code;

    /**
     * Estado HTTP que se devuelve al cliente.
     */
    private HttpStatus status;


    /**
     * Crea una nueva excepción de solicitud con el código de error, el estado HTTP y el mensaje especificados.
     *
     * @param code    el código de error de la excepción
     * @param status  el estado HTTP que se devuelve al cliente
     * @param mensaje el mensaje descriptivo de la excepción
     */

    public RequestException(String code, HttpStatus status, String mensaje){
        super(mensaje);
        this.code = code;
        this.mensaje = mensaje;
        this.status = status;

    }
}
