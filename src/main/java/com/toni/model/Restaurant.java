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
        name = "RESTAURANTES"
)
@SequenceGenerator(name = ConstantesAplicacion.SCHEMA_MAIN + "_RESTAURANTES_SEQ", sequenceName = ConstantesAplicacion.SCHEMA_MAIN + ".RESTAURANTES_SEQ", allocationSize = 1)
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=ConstantesAplicacion.SCHEMA_MAIN + "_RESTAURANTES_SEQ")
    @Column(name = "ID_RESTAURANTE")
    private Integer id;

    @Column(name = "NOMBRE")
    private String nombre;

    @OneToMany
    @JoinColumn(name = "ID_RESTAURANTE", referencedColumnName = "ID_RESTAURANTE")
    private List<Product> productsList;

    @OneToMany
    @JoinColumn(name = "ID_RESTAURANTE", referencedColumnName = "ID_RESTAURANTE")
    private List<Categoria> categoriasList;

    @OneToMany
    @JoinColumn(name = "ID_RESTAURANTE", referencedColumnName = "ID_RESTAURANTE")
    private List<Etiqueta> etiquetas;

    public Restaurant() {
    }

    public Restaurant(Integer id, String nombre, List<Product> productsList, List<Categoria> categoriasList, List<Etiqueta> etiquetas) {
        this.id = id;
        this.nombre = nombre;
        this.productsList = productsList;
        this.categoriasList = categoriasList;
        this.etiquetas = etiquetas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public List<Categoria> getCategoriasList() {
        return categoriasList;
    }

    public void setCategoriasList(List<Categoria> categoriasList) {
        this.categoriasList = categoriasList;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
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
        Restaurant other = (Restaurant) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", productsList=" + productsList +
                ", categoriasList=" + categoriasList +
                ", etiquetas=" + etiquetas +
                '}';
    }
}