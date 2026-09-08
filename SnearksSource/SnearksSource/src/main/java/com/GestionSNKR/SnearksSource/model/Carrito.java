package com.GestionSNKR.SnearksSource.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", unique = true)
    @JsonIgnore
    private Cliente cliente;

    @ElementCollection
    @CollectionTable(name = "carrito_items", joinColumns = @JoinColumn(name = "carrito_id"))
    private List<ItemCarrito> items = new ArrayList<>();

    private Double subtotal;
    private Double impuestos;
    private Double total;
    private String estado; // "ABIERTO", "PROCESADO", "ABANDONADO"
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaModificacion;

    public void calcularTotal() {
        this.subtotal = items == null ? 0.0 : items.stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
        this.total = this.subtotal + (this.impuestos != null ? this.impuestos : 0.0);
    }
}
