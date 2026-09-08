package com.GestionSNKR.SnearksSource.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ItemCarrito {
    private Long id;
    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private String talla;

    public ItemCarrito copy() {
        return new ItemCarrito(this.id, this.productoId, this.nombreProducto, this.cantidad, this.precioUnitario, this.talla);
    }

    public Double getSubtotal() {
        return (precioUnitario != null && cantidad != null) ? precioUnitario * cantidad : 0.0;
    }
}