package com.gasto.gasto.Modelo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

/**
 *  Gasto
 *  <p>Es una entidad que hace parte del modelo de la aplicación en SpringBoot
 *  la cual se encarga de representar los gastos por usuario, cuenta con los siguientes atributos:</p>
 *  <ul>
 *  <li>id: Identificador del gasto</li>
 *  <li>fecha: Fecha en la que se efectuó el gasto</li>
 *  <li>monto: valor del gasto</li>
 *  <li>descripción: Corresponde a la descripción del gasto</li>
 *  <li>usuario: este representa una relación con usuario al que pertenece el gasto</li>
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
 *  La libreria de validations permite poner restricciones sobre cada atributo, como
 *  que la fecha noo sea futura, el dato no sea nulo, que sea positivo o cero.
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
@Table(name = "gastos")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "fecha", nullable = false)
    @NotNull(message = "La fecha de ingreso no puede estar vacía")
    @PastOrPresent(message = "La fecha de ingreso debe ser pasada o presente")
    private LocalDate fecha;

    @Column(name = "monto", nullable = false)
    @NotNull(message = "El monto no puede estar vacio")
    @PositiveOrZero
    private double monto;

    @Column(name = "descripcion", nullable = false)
    @NotNull(message = "La fecha de ingreso no puede estar vacía")
    private String descripcion;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
