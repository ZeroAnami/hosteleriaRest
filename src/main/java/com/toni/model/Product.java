package com.toni.model;

import com.toni.base.ConstantesAplicacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Cacheable(false)
@Entity
@Table(
        schema = ConstantesAplicacion.SCHEMA_MAIN,
        name = "PRODUCTOS"
)
@SequenceGenerator(name = ConstantesAplicacion.SCHEMA_MAIN + "_PRODUCTOS_SEQ", sequenceName = ConstantesAplicacion.SCHEMA_MAIN + ".PRODUCTOS_SEQ", allocationSize = 1)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=ConstantesAplicacion.SCHEMA_MAIN + "_PRODUCTOS_SEQ")
    @Column(name = "ID_PRODUCTO")
    private Integer id;

    @Column(name = "ID_RESTAURANTE")
    private Integer idRestaurante;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "PRECIO")
    private Double precio;

    @Column(name = "CATEGORIA")
    private Integer categoria;

    @ElementCollection
    @CollectionTable(
            schema = ConstantesAplicacion.SCHEMA_MAIN,
            name = "ETIQUETAS_PRODUCTOS",
            joinColumns = @JoinColumn(name = "ID_PRODUCTO")
    )
    @Column(name = "ID_ETIQUETA")
    private List<Integer> etiquetas;

    public Product() {
    }

    public Product(Integer id, Integer idRestaurante, String nombre, Double precio, Integer categoria, List<Integer> etiquetas) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.etiquetas = etiquetas;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public List<Integer> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Integer> etiquetas) {
        this.etiquetas = etiquetas;
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
        Product other = (Product) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", idRestaurante=" + idRestaurante +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", categoria=" + categoria +
                ", etiquetas=" + etiquetas +
                '}';
    }
}