package com.gasto.gasto.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 *  Usuario
 *  <p>Es una entidad que hace parte del modelo de la aplicación en SpringBoot
 *  la cual se encarga de representar los usuarios que son gestionados, cuenta con los siguientes atributos:</p>
 *  <ul>
 *  <li>id: Identificador del usuario</li>
 *  <li>nombre: nombre del usuario</li>
 *  <li>estado: Indica si el usuario es cliente activo, sieno 1 si y 0 no</li>
 *  <li>email: dirección de e-mail del usuario</li>
 *  <li>fecha: Corresponde a la fecha de ingreso del usuario en el sistema</li>
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
 *  La librería de validations permite poner restricciones sobre cada atributo, como que no sea nulo o vacio, que tenga un valor
 *  minimo o maximo
 *  </p>
 *  <p>
 *  La librería util de java, nos permitira manejar listas en la relación hacia gastos
 *  </p>
 *  <p>
 *  La librería de time, permite asignar un formato de fecha al atributo correspondiente de gastos.
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
@Table(name = "usuarios")
public class Usuario {

    @Id @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "estado") @Min(0) @Max(1)
    private int estado;
    @Column(name = "nombre")
    @NotBlank(message = "El nombre no puede estar vacio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre no puede contener números o caracteres extraños")
    private String nombre;
    @Column(name = "email")
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no es válido")
    private String email;
    @Column(name = "fecha_ingreso")
    @NotNull(message = "La fecha de ingreso no puede estar vacía")
    @PastOrPresent(message = "La fecha de ingreso debe ser pasada o presente")
    private LocalDate fecha_ingreso;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Gasto> gastos;
}
