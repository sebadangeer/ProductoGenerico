package com.GestionSNKR.SnearksSource.service;

import com.GestionSNKR.SnearksSource.model.Cliente;
import com.GestionSNKR.SnearksSource.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> findAll() { return repo.findAll(); }

    public Optional<Cliente> findById(Long id) { return repo.findById(id); }

    public Cliente save(Cliente c) { return repo.save(c); }

    public Cliente update(Long id, Cliente c) {
        // Ensure the entity id matches the path id before saving
        c.setId(id);
        return repo.save(c);
    }

    public void deleteById(Long id) { repo.deleteById(id); }

    // Método para buscar al cliente por correo
    public Optional<Cliente> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public Optional<Cliente> login(String email, String contrasena) {
        Optional<Cliente> clienteOpt = repo.findByEmail(email);

        if (clienteOpt.isEmpty()) {
            System.out.println("DEBUG: No se encontró ningún cliente con el correo: " + email);
            return Optional.empty();
        }

        Cliente cliente = clienteOpt.get();
        if (!cliente.getContrasena().equals(contrasena)) {
            System.out.println("DEBUG: La contraseña no coincide. Recibida: " + contrasena + " | En BD: " + cliente.getContrasena());
            return Optional.empty();
        }

        return Optional.of(cliente);
    }
}
