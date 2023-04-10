package com.gasto.gasto.Excepciones;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ResourceNotFoundException extends Throwable {
    public ResourceNotFoundException(@NotBlank(message = "El correo no puede estar vacío") @Email(message = "El correo no es válido") String s) {
    }
}
