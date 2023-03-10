package com.gasto.gasto.Modelo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "estado")
    private int estado;
    @Column(name = "nombre")

    private String nombre;
    @Column(name = "correo")
    private String correo;
    @Column(name = "fecha_ingreso")
    private Date fecha_ingreso;


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
     * get field @Column(name = "estado")
     *
     * @return estado @Column(name = "estado")

     */
    public int getEstado() {
        return this.estado;
    }

    /**
     * set field @Column(name = "estado")
     *
     * @param estado @Column(name = "estado")

     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * get field @Column(name = "nombre")
     *
     * @return nombre @Column(name = "nombre")


     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * set field @Column(name = "nombre")
     *
     * @param nombre @Column(name = "nombre")


     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * get field @Column(name = "correo")
     *
     * @return correo @Column(name = "correo")

     */
    public String getCorreo() {
        return this.correo;
    }

    /**
     * set field @Column(name = "correo")
     *
     * @param correo @Column(name = "correo")

     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * get field @Column(name = "fecha_ingreso")
     *
     * @return fecha_ingreso @Column(name = "fecha_ingreso")

     */
    public Date getFecha_ingreso() {
        return this.fecha_ingreso;
    }

    /**
     * set field @Column(name = "fecha_ingreso")
     *
     * @param fecha_ingreso @Column(name = "fecha_ingreso")

     */
    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }
}
