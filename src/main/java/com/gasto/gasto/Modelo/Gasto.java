package com.gasto.gasto.Modelo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fecha")
    @NotNull(message = "La fecha del gasto no puede estar vacía")
    @PastOrPresent(message = "La fecha de ingreso debe ser pasada o presente")
    private Date fecha_gasto;

    @Column(name = "monto")
    @NotNull(message = "El gasto debe tener un monto")
    private float monto;

    @Column(name = "descripcion")
    @NotNull(message = "El gasto debe tener un monto")
    private String descripcion;
    @Column(name = "id_usuario")
    @NotNull(message = "El gasto debe tener un usuario valido")
    private int id_usuario;

    public Gasto(int id) {
        this.id = id;
    }


    /**
     * get field @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")

      *
      * @return id @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")

     */
    public int getId() {
        return this.id;
    }

    /**
     * set field @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")

      *
      * @param id @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")

     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get field @Column(name = "fecha")
     @NotNull(message = "La fecha del gasto no puede estar vacía")
     @PastOrPresent(message = "La fecha de ingreso debe ser pasada o presente")

      *
      * @return fecha_gasto @Column(name = "fecha")
     @NotNull(message = "La fecha del gasto no puede estar vacía")
     @PastOrPresent(message = "La fecha de ingreso debe ser pasada o presente")

     */
    public Date getFecha_gasto() {
        return this.fecha_gasto;
    }

    /**
     * set field @Column(name = "fecha")
     @NotNull(message = "La fecha del gasto no puede estar vacía")
     @PastOrPresent(message = "La fecha de ingreso debe ser pasada o presente")

      *
      * @param fecha_gasto @Column(name = "fecha")
     @NotNull(message = "La fecha del gasto no puede estar vacía")
     @PastOrPresent(message = "La fecha de ingreso debe ser pasada o presente")

     */
    public void setFecha_gasto(Date fecha_gasto) {
        this.fecha_gasto = fecha_gasto;
    }

    /**
     * get field @Column(name = "monto")
     @NotNull(message = "El gasto debe tener un monto")

      *
      * @return monto @Column(name = "monto")
     @NotNull(message = "El gasto debe tener un monto")

     */
    public float getMonto() {
        return this.monto;
    }

    /**
     * set field @Column(name = "monto")
     @NotNull(message = "El gasto debe tener un monto")

      *
      * @param monto @Column(name = "monto")
     @NotNull(message = "El gasto debe tener un monto")

     */
    public void setMonto(float monto) {
        this.monto = monto;
    }

    /**
     * get field @Column(name = "descripcion")
     @NotNull(message = "El gasto debe tener un monto")

      *
      * @return descripcion @Column(name = "descripcion")
     @NotNull(message = "El gasto debe tener un monto")

     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * set field @Column(name = "descripcion")
     @NotNull(message = "El gasto debe tener un monto")

      *
      * @param descripcion @Column(name = "descripcion")
     @NotNull(message = "El gasto debe tener un monto")

     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * get field @Column(name = "id_usuario")
     @NotNull(message = "El gasto debe tener un usuario valido")

      *
      * @return id_usuario @Column(name = "id_usuario")
     @NotNull(message = "El gasto debe tener un usuario valido")

     */
    public int getId_usuario() {
        return this.id_usuario;
    }

    /**
     * set field @Column(name = "id_usuario")
     @NotNull(message = "El gasto debe tener un usuario valido")

      *
      * @param id_usuario @Column(name = "id_usuario")
     @NotNull(message = "El gasto debe tener un usuario valido")

     */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
