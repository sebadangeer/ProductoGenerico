package com.GestionSNKR.SnearksSource.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precio")
    private Integer precio;

    @Column(name = "nombre_modelo")
    private String nombreModelo;

    @Column(name = "descripcion",length = 600)
    private String descripcion;

    @Column(name = "link_imagen")
    private String linkImagen;

    @Column(name = "tipo_categoria")
    private String tipoCategoria;

    @ElementCollection
    @CollectionTable(
            name = "producto_tallas",
            joinColumns = @JoinColumn(name = "producto_id")
    )
    @MapKeyColumn(name = "talla")
    @Column(name = "cantidad")
    private Map<String, Integer> tallasDisponibles = new HashMap<>();

    public Producto(Long id, Integer precio, String nombreModelo, String descripcion, String linkImagen, String tipoCategoria) {
        this.id = id;
        this.precio = precio;
        this.nombreModelo = nombreModelo;
        this.descripcion = descripcion;
        this.linkImagen = linkImagen;
        this.tipoCategoria = tipoCategoria;
        this.tallasDisponibles = new HashMap<>();
    }

    public Producto(Long id, Integer precio, String nombreModelo, String descripcion, String linkImagen, String tipoCategoria, Map<String, Integer> tallasDisponibles) {
        this.id = id;
        this.precio = precio;
        this.nombreModelo = nombreModelo;
        this.descripcion = descripcion;
        this.linkImagen = linkImagen;
        this.tipoCategoria = tipoCategoria;
        this.tallasDisponibles = tallasDisponibles == null ? new HashMap<>() : new HashMap<>(tallasDisponibles);
    }

}