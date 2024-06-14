package com.toni.model;

import com.toni.base.ConstantesAplicacion;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = ConstantesAplicacion.SCHEMA_MAIN, name = "ORDERS")
@SequenceGenerator(name = ConstantesAplicacion.SCHEMA_MAIN + "_ORDERS_SEQ", sequenceName = ConstantesAplicacion.SCHEMA_MAIN + ".ORDERS_SEQ", allocationSize = 1)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=ConstantesAplicacion.SCHEMA_MAIN + "_ORDERS_SEQ")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ID_CONEXION")
    private Integer idConexion;

    @OneToMany
    @JoinColumn(name = "ID_ORDER", referencedColumnName = "ID_ORDER")
    private List<OrderItem> orderItemList;

    public Order() {
    }

    public Order(Integer id, Integer idConexion, List<OrderItem> orderItemList) {
        this.id = id;
        this.idConexion = idConexion;
        this.orderItemList = orderItemList;
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

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
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
        Order other = (Order) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", idConexion=" + idConexion +
                ", orderItemList=" + orderItemList +
                '}';
    }
}