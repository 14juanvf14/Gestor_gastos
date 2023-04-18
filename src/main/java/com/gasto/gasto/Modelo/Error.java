package com.gasto.gasto.Modelo;

import lombok.*;
import org.springframework.http.HttpStatus;

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
