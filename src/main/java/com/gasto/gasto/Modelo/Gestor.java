package com.gasto.gasto.Modelo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 *  Gestor
 *  <p>Es una entidad que hace parte del modelo de la aplicación en SpringBoot
 *  la cual se encarga de representar los gestores del sistema, cuenta con los siguientes atributos:</p>
 *  <ul>
 *  <li>id: Identificador del gestor</li>
 *  <li>nombre: nombre o apodo de gestor</li>
 *  <li>email: dirección de e-mail del gestor asignada por el admin</li>
 *  <li>password: contraseña del gestor asignada por el admin</li>
 *  <li>rol: El rol de gestor podría ser gestor o administrador, lo que permitira acceder a otras crud por rol</li>
 *  <li>usuario: Relación de uno a muchos que infiere en la gestión de cada gestor</li>
 *  </ul>
 *
 *  <p>
 *  La librería lombok permite generar automáticamente los getter, setter
 *  y constructores de la clase, con las notaciones que le acompañan con @,
 *  por su parte Builder le permite la construcción de objetos cuando la clase es instanciada.
 *  </p>
 *  <p>
 *  La librería de persistence, permite notaciones como @Entity, @Table, @Id, @Column,
 *  permite representar el modelo en la capa de persistencia.
 *  </p>
 *  <p>
 *  La librería de validations permite poner restricciones sobre cada atributo, como
 *  que la un patron, un e-mail o que el dato no sea nulo.
 *  </p>
 *  <p>
 *  La librería util de java, nos permitira manejar listas en la relación hacia usuarios
 *  </p>
 *
 *
 *  @author Juan Vasquez
 *  @version 1.0
 *  @since 29/04/2023
 *
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "gestores")
public class Gestor {
    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nombre", nullable = false)
    @NotBlank(message = "El nombre no puede estar vacio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre no puede contener números o caracteres extraños")
    private String nombre;

    @NotNull
    @Column(name = "email", nullable = false)
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no es válido")
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    @NotBlank(message = "La clave no puede estar vacío")
    private String password;

    @Column(name = "rol", nullable = false)
    @NotBlank(message = "El gestor debe tener un rol definido")
    private String rol;
}
