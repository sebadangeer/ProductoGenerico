package com.GestionSNKR.SnearksSource.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ElementCollection
    @CollectionTable(name = "boleta_items", joinColumns = @JoinColumn(name = "boleta_id"))
    private List<ItemCarrito> items = new ArrayList<>();

    private Double subtotal;
    private Double impuestos;
    private Double total;

    private LocalDateTime fecha;
    private String metodoPago;
    private String direccion;
    private String estado; // CREADA, PAGADA, ENVIADA

    public void calcularTotal() {
        this.subtotal = items == null ? 0.0 : items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
        this.total = this.subtotal + (this.impuestos != null ? this.impuestos : 0.0);
    }
}
