package com.GestionSNKR.SnearksSource.controller;

import com.GestionSNKR.SnearksSource.DTO.AgregarCarritoRequest;
import com.GestionSNKR.SnearksSource.model.Carrito;
import com.GestionSNKR.SnearksSource.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    // ==========================================
    // ENDPOINTS PARA CLIENTE AUTENTICADO
    // ==========================================

    @GetMapping("/api/clientes/{clienteId}/carrito")
    public ResponseEntity<Carrito> obtenerCarritoCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(carritoService.obtenerCarrito(clienteId));
    }

    @PostMapping("/api/clientes/{clienteId}/carrito/items")
    public ResponseEntity<?> agregarProductoCliente(
            @PathVariable Long clienteId,
            @RequestBody AgregarCarritoRequest request) {
        try {
            Carrito carrito = carritoService.agregarProducto(
                    clienteId,
                    request.getProductoId(),
                    request.getCantidad()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(carrito);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/clientes/{clienteId}/carrito/items/{productoId}")
    public ResponseEntity<?> actualizarCantidadCliente(
            @PathVariable Long clienteId,
            @PathVariable Long productoId,
            @RequestBody AgregarCarritoRequest request) {
        try {
            request.setProductoId(productoId);
            Carrito carrito = carritoService.actualizarCantidad(
                    clienteId,
                    request.getProductoId(),
                    request.getCantidad()
            );
            return ResponseEntity.ok(carrito);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/clientes/{clienteId}/carrito/items/{productoId}")
    public ResponseEntity<?> eliminarProductoCliente(
            @PathVariable Long clienteId,
            @PathVariable Long productoId) {
        try {
            Carrito carrito = carritoService.eliminarProducto(clienteId, productoId);
            return ResponseEntity.ok(carrito);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/clientes/{clienteId}/carrito")
    public ResponseEntity<Carrito> vaciarCarritoCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(carritoService.vaciarCarrito(clienteId));
    }

    // ==========================================
    // ENDPOINTS PARA INVITADO (SESIÓN)
    // ==========================================

    @GetMapping("/api/carrito")
    public ResponseEntity<Carrito> obtenerCarrito(HttpSession session) {
        return ResponseEntity.ok(carritoService.obtenerCarrito(session));
    }

    @PostMapping("/api/carrito/items")
    public ResponseEntity<?> agregarProducto(@RequestBody AgregarCarritoRequest request, HttpSession session) {
        try {
            Carrito carrito = carritoService.agregarProducto(
                    session,
                    request.getProductoId(),
                    request.getCantidad()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(carrito);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/api/carrito/items/{productoId}")
    public ResponseEntity<?> actualizarCantidad(
            @PathVariable Long productoId,
            @RequestBody AgregarCarritoRequest request,
            HttpSession session) {
        try {
            request.setProductoId(productoId);
            Carrito carrito = carritoService.actualizarCantidad(
                    session,
                    request.getProductoId(),
                    request.getCantidad()
            );
            return ResponseEntity.ok(carrito);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/carrito/items/{productoId}")
    public ResponseEntity<?> eliminarProducto(
            @PathVariable Long productoId,
            HttpSession session) {
        try {
            Carrito carrito = carritoService.eliminarProducto(session, productoId);
            return ResponseEntity.ok(carrito);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/api/carrito")
    public ResponseEntity<Carrito> vaciarCarrito(HttpSession session) {
        return ResponseEntity.ok(carritoService.vaciarCarrito(session));
    }
}