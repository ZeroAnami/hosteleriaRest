package com.toni.model;

import com.toni.base.ConstantesAplicacion;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Cacheable(false)
@Entity
@Table(
        schema = ConstantesAplicacion.SCHEMA_MAIN,
        name = "ETIQUETAS"
)
@SequenceGenerator(name = ConstantesAplicacion.SCHEMA_MAIN + "_ETIQUETAS_SEQ", sequenceName = ConstantesAplicacion.SCHEMA_MAIN + ".ETIQUETAS_SEQ", allocationSize = 1)
public class Etiqueta implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=ConstantesAplicacion.SCHEMA_MAIN + "_ETIQUETAS_SEQ")
    @Column(name = "ID_ETIQUETA")
    private Integer id;

    @Column(name = "ID_RESTAURANTE")
    private Integer idRestaurante;

    @Column(name = "NOMBRE")
    private String nombre;

    public Etiqueta() {
    }

    public Etiqueta(Integer id, Integer idRestaurante, String nombre) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Integer idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Etiqueta other = (Etiqueta) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Etiqueta{" +
                "id=" + id +
                ", idRestaurante=" + idRestaurante +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}