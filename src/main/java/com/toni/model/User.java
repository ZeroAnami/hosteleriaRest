package com.toni.model;

import com.toni.base.ConstantesAplicacion;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = ConstantesAplicacion.SCHEMA_MAIN, name = "USUARIOS")
@SequenceGenerator(name = ConstantesAplicacion.SCHEMA_MAIN + "_USUARIOS_SEQ", sequenceName = ConstantesAplicacion.SCHEMA_MAIN + ".USUARIOS_SEQ", allocationSize = 1)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=ConstantesAplicacion.SCHEMA_MAIN + "_USUARIOS_SEQ")
    @Column(name = "ID_USUARIO")
    private Integer id;

    @Column(name = "ID_CONEXION")
    private Integer idConexion;

    @Column(name = "NOMBRE")
    private String nombre;

    public User() {
    }

    public User(Integer id, Integer idConexion, String nombre) {
        this.id = id;
        this.idConexion = idConexion;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdConexion() {
        return idConexion;
    }

    public void setIdConexion(Integer idConexion) {
        this.idConexion = idConexion;
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
        User other = (User) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", idConexion=" + idConexion +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}