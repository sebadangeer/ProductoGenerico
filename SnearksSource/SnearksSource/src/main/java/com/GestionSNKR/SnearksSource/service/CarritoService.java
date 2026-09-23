package com.GestionSNKR.SnearksSource.service;

import com.GestionSNKR.SnearksSource.model.Carrito;
import com.GestionSNKR.SnearksSource.model.Cliente;
import com.GestionSNKR.SnearksSource.model.ItemCarrito;
import com.GestionSNKR.SnearksSource.model.Producto;
import com.GestionSNKR.SnearksSource.repository.CarritoRepository;
import com.GestionSNKR.SnearksSource.repository.ClienteRepository;
import com.GestionSNKR.SnearksSource.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(CarritoRepository carritoRepository, ClienteRepository clienteRepository, ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    // ==========================================
    // MÉTODOS BASADOS EN BASE DE DATOS (CLIENTE)
    // ==========================================

    public Carrito obtenerCarrito(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

        return carritoRepository.findByClienteId(clienteId)
                .orElseGet(() -> {
                    Carrito carrito = new Carrito();
                    carrito.setCliente(cliente);
                    carrito.setItems(new ArrayList<>());
                    carrito.setEstado("ABIERTO");
                    carrito.setImpuestos(0.0);
                    carrito.setFechaCreacion(LocalDateTime.now());
                    carrito.setFechaUltimaModificacion(LocalDateTime.now());
                    carrito.calcularTotal();
                    return carritoRepository.save(carrito);
                });
    }

    public Carrito agregarProducto(Long clienteId, Long productoId, Integer cantidad) {
        if (productoId == null) {
            throw new IllegalArgumentException("Debe indicar el producto.");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));

        if (producto.getStock() == null || producto.getStock() < cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock disponible.");
        }

        Carrito carrito = carritoRepository.findByClienteId(clienteId).orElseGet(() -> {
            Carrito nuevo = new Carrito();
            nuevo.setCliente(cliente);
            nuevo.setItems(new ArrayList<>());
            nuevo.setEstado("ABIERTO");
            nuevo.setImpuestos(0.0);
            nuevo.setFechaCreacion(LocalDateTime.now());
            nuevo.setFechaUltimaModificacion(LocalDateTime.now());
            return nuevo;
        });

        ItemCarrito itemExistente = carrito.getItems().stream()
                .filter(item -> Objects.equals(item.getProductoId(), productoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
        } else {
            ItemCarrito item = new ItemCarrito();
            item.setId(System.currentTimeMillis());
            item.setProductoId(producto.getId());
            item.setNombreProducto(producto.getNombreModelo());
            item.setCantidad(cantidad);
            item.setPrecioUnitario(producto.getPrecio() != null ? producto.getPrecio().doubleValue() : 0.0);
            carrito.getItems().add(item);
        }

        carrito.setFechaUltimaModificacion(LocalDateTime.now());
        carrito.calcularTotal();
        return carritoRepository.save(carrito);
    }

    public Carrito actualizarCantidad(Long clienteId, Long productoId, Integer cantidad) {
        if (productoId == null) {
            throw new IllegalArgumentException("Debe indicar el producto.");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        Carrito carrito = obtenerCarrito(clienteId);

        ItemCarrito item = carrito.getItems().stream()
                .filter(i -> Objects.equals(i.getProductoId(), productoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en el carrito."));

        item.setCantidad(cantidad);
        carrito.setFechaUltimaModificacion(LocalDateTime.now());
        carrito.calcularTotal();
        return carritoRepository.save(carrito);
    }

    public Carrito eliminarProducto(Long clienteId, Long productoId) {
        if (productoId == null) {
            throw new IllegalArgumentException("Debe indicar el producto.");
        }

        Carrito carrito = obtenerCarrito(clienteId);

        carrito.getItems().removeIf(item -> Objects.equals(item.getProductoId(), productoId));

        carrito.setFechaUltimaModificacion(LocalDateTime.now());
        carrito.calcularTotal();
        return carritoRepository.save(carrito);
    }

    public Carrito vaciarCarrito(Long clienteId) {
        Carrito carrito = obtenerCarrito(clienteId);
        carrito.getItems().clear();
        carrito.setFechaUltimaModificacion(LocalDateTime.now());
        carrito.calcularTotal();
        return carritoRepository.save(carrito);
    }

    // ==========================================
    // MÉTODOS BASADOS EN SESIÓN (INVITADO)
    // ==========================================

    public Carrito obtenerCarrito(HttpSession session) {
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setItems(new ArrayList<>());
            carrito.setEstado("ABIERTO");
            carrito.setImpuestos(0.0);
            carrito.setFechaCreacion(LocalDateTime.now());
            carrito.setFechaUltimaModificacion(LocalDateTime.now());
            carrito.calcularTotal();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }

    public Carrito agregarProducto(HttpSession session, Long productoId, Integer cantidad) {
        if (productoId == null) {
            throw new IllegalArgumentException("Debe indicar el producto.");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));

        if (producto.getStock() == null || producto.getStock() < cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock disponible.");
        }

        Carrito carrito = obtenerCarrito(session);

        ItemCarrito itemExistente = carrito.getItems().stream()
                .filter(item -> Objects.equals(item.getProductoId(), productoId))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() + cantidad);
        } else {
            ItemCarrito item = new ItemCarrito();
            item.setId(System.currentTimeMillis());
            item.setProductoId(producto.getId());
            item.setNombreProducto(producto.getNombreModelo());
            item.setCantidad(cantidad);
            item.setPrecioUnitario(producto.getPrecio() != null ? producto.getPrecio().doubleValue() : 0.0);
            carrito.getItems().add(item);
        }

        carrito.setFechaUltimaModificacion(LocalDateTime.now());
        carrito.calcularTotal();
        session.setAttribute("carrito", carrito);
        return carrito;
    }

    public Carrito actualizarCantidad(HttpSession session, Long productoId, Integer cantidad) {
        if (productoId == null) {
            throw new IllegalArgumentException("Debe indicar el producto.");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        Carrito carrito = obtenerCarrito(session);

        ItemCarrito item = carrito.getItems().stream()
                .filter(i -> Objects.equals(i.getProductoId(), productoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en el carrito."));

        item.setCantidad(cantidad);
        carrito.setFechaUltimaModificacion(LocalDateTime.now());
        carrito.calcularTotal();
        session.setAttribute("carrito", carrito);
        return carrito;
    }

    public Carrito eliminarProducto(HttpSession session, Long productoId) {
        if (productoId == null) {
            throw new IllegalArgumentException("Debe indicar el producto.");
        }

        Carrito carrito = obtenerCarrito(session);

        carrito.getItems().removeIf(item -> Objects.equals(item.getProductoId(), productoId));

        carrito.setFechaUltimaModificacion(LocalDateTime.now());
        carrito.calcularTotal();
        session.setAttribute("carrito", carrito);
        return carrito;
    }

    public Carrito vaciarCarrito(HttpSession session) {
        Carrito carrito = obtenerCarrito(session);
        carrito.getItems().clear();
        carrito.setFechaUltimaModificacion(LocalDateTime.now());
        carrito.calcularTotal();
        session.setAttribute("carrito", carrito);
        return carrito;
    }
}