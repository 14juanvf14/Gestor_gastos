package com.gasto.gasto.Modelo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

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
