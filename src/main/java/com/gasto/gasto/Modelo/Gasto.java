package com.gasto.gasto.Modelo;


import javax.persistence.*;
import lombok.*;
import javax.validation.constraints.*;
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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
