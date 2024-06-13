package com.toni.model;

import com.toni.base.ConstantesAplicacion;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Cacheable(false)
@Entity
@Table(
        schema = ConstantesAplicacion.SCHEMA_MAIN,
        name = "CONEXIONES"
)
@SequenceGenerator(name = ConstantesAplicacion.SCHEMA_MAIN + "_CONEXIONES_SEQ", sequenceName = ConstantesAplicacion.SCHEMA_MAIN + ".CONEXIONES_SEQ", allocationSize = 1)
public class Conexion implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=ConstantesAplicacion.SCHEMA_MAIN + "_CONEXIONES_SEQ")
    @Column(name = "ID_CONEXION")
    private Integer id;

    @Column(name = "ID_RESTAURANTE")
    private Integer idRestaurante;
    @OneToMany
    @JoinColumn(name = "ID_CONEXION", referencedColumnName = "ID_CONEXION")
    private List<Order> pedidosList;

    @OneToMany
    @JoinColumn(name = "ID_CONEXION", referencedColumnName = "ID_CONEXION")
    private List<User> userList;

    @Column(name = "ESTADO")
    private Integer estado;

    public Conexion() {
    }

    public Conexion(Integer id, Integer idRestaurante, List<Order> pedidosList, List<User> userList) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.pedidosList = pedidosList;
        this.userList = userList;
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

    public List<Order> getPedidosList() {
        return pedidosList;
    }

    public void setPedidosList(List<Order> pedidosList) {
        this.pedidosList = pedidosList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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
        Conexion other = (Conexion) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Conexion{" +
                "id=" + id +
                ", idRestaurante=" + idRestaurante +
                ", pedidosList=" + pedidosList +
                ", userList=" + userList +
                ", estado=" + estado +
                '}';
    }
}