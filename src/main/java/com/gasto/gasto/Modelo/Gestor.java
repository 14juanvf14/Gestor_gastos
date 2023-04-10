package com.gasto.gasto.Modelo;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "gestores")
public class Gestor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
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

    @OneToMany(mappedBy = "gestor")
    private List<Usuario> usuarios;
}
