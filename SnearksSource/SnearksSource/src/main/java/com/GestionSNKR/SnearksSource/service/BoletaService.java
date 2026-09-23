package com.GestionSNKR.SnearksSource.service;

import com.GestionSNKR.SnearksSource.model.Boleta;
import com.GestionSNKR.SnearksSource.model.Carrito;
import com.GestionSNKR.SnearksSource.model.Cliente;
import com.GestionSNKR.SnearksSource.model.ItemCarrito;
import com.GestionSNKR.SnearksSource.model.Producto;
import com.GestionSNKR.SnearksSource.repository.BoletaRepository;
import com.GestionSNKR.SnearksSource.repository.CarritoRepository;
import com.GestionSNKR.SnearksSource.repository.ClienteRepository;
import com.GestionSNKR.SnearksSource.repository.ProductoRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoletaService {

    private final BoletaRepository boletaRepository;
    private final CarritoRepository carritoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final JavaMailSender mailSender;

    public BoletaService(BoletaRepository boletaRepository,
                         CarritoRepository carritoRepository,
                         ClienteRepository clienteRepository,
                         ProductoRepository productoRepository,
                         JavaMailSender mailSender) {
        this.boletaRepository = boletaRepository;
        this.carritoRepository = carritoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    public Boleta crearDesdeCarrito(Long clienteId, String metodoPago, String direccion) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

        Carrito carrito = carritoRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado para el cliente."));

        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío.");
        }

        // Descontar stock de cada producto
        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado ID: " + item.getProductoId()));

            if (producto.getStock() == null || producto.getStock() < item.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombreModelo());
            }

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        Boleta boleta = new Boleta();
        boleta.setCliente(cliente);
        List<ItemCarrito> itemsBoleta = new ArrayList<>();
        carrito.getItems().forEach(item -> itemsBoleta.add(item.copy()));
        boleta.setItems(itemsBoleta);
        boleta.setImpuestos(carrito.getImpuestos());
        boleta.setMetodoPago(metodoPago);
        boleta.setDireccion(direccion);
        boleta.setFecha(LocalDateTime.now());
        boleta.setEstado("CREADA");
        boleta.calcularTotal();

        Boleta guardada = boletaRepository.save(boleta);

        // Enviar boleta por email de forma asíncrona si existe email
        if (guardada.getCliente() != null && guardada.getCliente().getEmail() != null && !guardada.getCliente().getEmail().isBlank()) {
            enviarBoletaPorEmailAsync(guardada);
        }

        // Vaciar carrito después de crear boleta
        carrito.getItems().clear();
        carrito.calcularTotal();
        carritoRepository.save(carrito);

        return guardada;
    }

    @Async
    public void enviarBoletaPorEmailAsync(Boleta boleta) {
        try {
            enviarBoletaPorEmail(boleta);
        } catch (Exception ex) {
            System.err.println("Error enviando boleta por email: " + ex.getMessage());
        }
    }

    private void enviarBoletaPorEmail(Boleta boleta) throws Exception {
        String destino = boleta.getCliente().getEmail();
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setTo(destino);
        helper.setFrom("no-reply@snearks.com");
        helper.setSubject("Tu boleta - Pedido #" + boleta.getId());

        StringBuilder html = new StringBuilder();
        html.append("<h2>Gracias por tu compra</h2>");
        html.append("<p>Boleta #").append(boleta.getId()).append(" - ").append(boleta.getFecha()).append("</p>");
        html.append("<table border='1' cellpadding='6' cellspacing='0'>");
        html.append("<tr><th>Producto</th><th>Cantidad</th><th>Precio</th><th>Subtotal</th></tr>");
        boleta.getItems().forEach(i -> {
            html.append("<tr>");
            html.append("<td>").append(i.getNombreProducto()).append("</td>");
            html.append("<td>").append(i.getCantidad()).append("</td>");
            html.append("<td>").append(String.format("%.2f", i.getPrecioUnitario())).append("</td>");
            html.append("<td>").append(String.format("%.2f", i.getSubtotal())).append("</td>");
            html.append("</tr>");
        });
        html.append("</table>");
        html.append("<p><b>Total: </b>").append(String.format("%.2f", boleta.getTotal())).append("</p>");

        helper.setText(html.toString(), true);
        mailSender.send(msg);
    }

    public List<Boleta> listarPorCliente(Long clienteId) {
        return boletaRepository.findByClienteId(clienteId);
    }

    public Boleta obtener(Long clienteId, Long boletaId) {
        return boletaRepository.findById(boletaId)
                .filter(b -> b.getCliente() != null && b.getCliente().getId().equals(clienteId))
                .orElseThrow(() -> new IllegalArgumentException("Boleta no encontrada para ese cliente."));
    }
}