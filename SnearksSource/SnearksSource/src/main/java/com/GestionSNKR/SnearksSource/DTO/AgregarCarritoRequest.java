package com.GestionSNKR.SnearksSource.DTO;

import lombok.Data;

@Data
public class AgregarCarritoRequest {
    private Long productoId;
    private Integer cantidad;
}
