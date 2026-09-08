package com.GestionSNKR.SnearksSource.controller;

import com.GestionSNKR.SnearksSource.model.Producto;
import com.GestionSNKR.SnearksSource.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Permite peticiones desde el frontend (JS local/fetch)
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 1. Obtener todos los productos (GET /api/productos)
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/jordan")
    public ResponseEntity<List<Producto>> obtenerCategoriaJordan() {
        List<Producto> productos = productoService.obtenerPorCategoria("jordan");
        return ResponseEntity.ok(productos);
    }
    @GetMapping("/categoria/sports")
    public ResponseEntity<List<Producto>> obtenerCategoriaSports() {
        List<Producto> productos = productoService.obtenerPorCategoria("Nike Sports");
        return ResponseEntity.ok(productos);
    }
    @GetMapping("/categoria/urban")
    public ResponseEntity<List<Producto>> obtenerCategoriaUrban() {
        List<Producto> productos = productoService.obtenerPorCategoria("Nike Urbano");
        return ResponseEntity.ok(productos);
    }

    // 2. Obtener un producto por ID (GET /api/productos/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. Crear un nuevo producto (POST /api/productos)
    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // 4. Actualizar un producto existente (PUT /api/productos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoService.actualizarProducto(id, producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Eliminar un producto (DELETE /api/productos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoService.eliminarProducto(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}