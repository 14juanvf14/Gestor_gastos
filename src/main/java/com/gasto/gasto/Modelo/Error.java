package com.gasto.gasto.Modelo;


import lombok.*;
import org.springframework.http.HttpStatus;
/**
 * @author Juan Vasquez
 * @FechaActualización 29/04/2023
 *  <p>
 *  Error:
 *  Representa a los errores generados en la aplicación
 *  Está acompañada de los siguientes atributos:
 *  <ul>
 *  <li>Código de error</li>
 *  <li>Mensaje de error</li>
 *  <li>Estado Http de la solicitud donde se produjo el error</li>
 *  <p>
 *  La librería lombok permite generar automáticamente los getter, setter
 *  y constructores de la clase, con las notaciones que le acompañan con @,
 *  por su parte Builder le permite la construcción de objetos cuando la clase es instanciada.
 *  <p>
 *  La librería HttpStatus de SpringFramework permite representar un estado
 *  Http
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor



public class Error {
    String code;
    String error_mensaje;
    HttpStatus status;
}
