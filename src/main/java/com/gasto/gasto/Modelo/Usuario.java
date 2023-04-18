package com.gasto.gasto.Modelo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 *  Usuario
 *  Esta entidad representa la tabla "usuarios" de la crud
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;
    @Column(name = "estado", nullable = false)
    @Min(0)
    @Max(1)
    private int estado;
    @Column(name = "nombre", nullable = false)
    @NotBlank(message = "El nombre no puede estar vacio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre no puede contener números o caracteres extraños")
    private String nombre;
    @Column(name = "email", nullable = false)
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no es válido")
    private String email;
    @Column(name = "fecha_ingreso", nullable = false)
    @NotNull(message = "La fecha de ingreso no puede estar vacía")
    @PastOrPresent(message = "La fecha de ingreso debe ser pasada o presente")
    private LocalDate fecha_ingreso;

    @OneToMany(mappedBy = "usuario")
    private List<Gasto> gastos;

    @ManyToOne
    @JoinColumn(name = "gestor_id")
    private Gestor gestor;
}
