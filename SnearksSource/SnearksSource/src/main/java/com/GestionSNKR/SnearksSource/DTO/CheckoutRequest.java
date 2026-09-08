package com.GestionSNKR.SnearksSource.DTO;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String metodoPago;
    private String direccion;
}
