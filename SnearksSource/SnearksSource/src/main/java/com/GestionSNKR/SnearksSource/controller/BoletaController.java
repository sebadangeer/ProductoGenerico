package com.GestionSNKR.SnearksSource.controller;

import com.GestionSNKR.SnearksSource.DTO.CheckoutRequest;
import com.GestionSNKR.SnearksSource.model.Boleta;
import com.GestionSNKR.SnearksSource.service.BoletaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/boletas")
@CrossOrigin(origins = "*")
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    @PostMapping
    public ResponseEntity<?> crearBoleta(@PathVariable Long clienteId, @RequestBody CheckoutRequest request) {
        try {
            Boleta boleta = boletaService.crearDesdeCarrito(clienteId, request.getMetodoPago(), request.getDireccion());
            return ResponseEntity.status(HttpStatus.CREATED).body(boleta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Boleta>> listarBoletas(@PathVariable Long clienteId) {
        return ResponseEntity.ok(boletaService.listarPorCliente(clienteId));
    }

    @GetMapping("/{boletaId}")
    public ResponseEntity<?> obtenerBoleta(@PathVariable Long clienteId, @PathVariable Long boletaId) {
        try {
            Boleta b = boletaService.obtener(clienteId, boletaId);
            return ResponseEntity.ok(b);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
