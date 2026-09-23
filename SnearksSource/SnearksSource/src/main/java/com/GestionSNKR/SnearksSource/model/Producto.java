package com.GestionSNKR.SnearksSource.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precio")
    private Integer precio;

    @Column(name = "nombre_modelo")
    private String nombreModelo;

    @Column(name = "descripcion", length = 600)
    private String descripcion;

    @Column(name = "link_imagen")
    private String linkImagen;

    @ManyToOne
    @JoinColumn(name = "tipo_categoria_id")
    private TipoCategoria tipoCategoria;

    @Column(name = "stock")
    private Integer stock;

    public Producto(Integer precio, String nombreModelo, String descripcion, String linkImagen, TipoCategoria tipoCategoria, Integer stock) {
        this.precio = precio;
        this.nombreModelo = nombreModelo;
        this.descripcion = descripcion;
        this.linkImagen = linkImagen;
        this.tipoCategoria = tipoCategoria;
        this.stock = stock;
    }
}