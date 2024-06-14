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
        name = "ORDER_ITEMS"
)
@SequenceGenerator(name = ConstantesAplicacion.SCHEMA_MAIN + "_ORDER_ITEMS_SEQ", sequenceName = ConstantesAplicacion.SCHEMA_MAIN + ".ORDERS_ITEM_SEQ1", allocationSize = 1)
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=ConstantesAplicacion.SCHEMA_MAIN + "_ORDER_ITEMS_SEQ")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ID_ORDER")
    private Integer idOrder;

    @Column(name = "ID_PRODUCTO")
    private Integer producto;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @ElementCollection
    @CollectionTable(
            schema = ConstantesAplicacion.SCHEMA_MAIN,
            name = "ORDER_ITEMS_USERS",
            joinColumns = @JoinColumn(name = "ID")
    )
    @Column(name = "ID_USUARIO")
    private List<Integer> users;

    public OrderItem() {
    }

    public OrderItem(Integer id, Integer idOrder, Integer producto, Integer cantidad, List<Integer> users) {
        this.id = id;
        this.idOrder = idOrder;
        this.producto = producto;
        this.cantidad = cantidad;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
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
        OrderItem other = (OrderItem) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", idOrder=" + idOrder +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", users=" + users +
                '}';
    }
}