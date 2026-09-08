package com.GestionSNKR.SnearksSource.controller;

import com.GestionSNKR.SnearksSource.DTO.LoginDTO;
import com.GestionSNKR.SnearksSource.model.Cliente;
import com.GestionSNKR.SnearksSource.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) { this.service = service; }

    @GetMapping
    public List<Cliente> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> get(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente c) {
        Cliente saved = service.save(c);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente c) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        c.setId(id);
        Cliente updated = service.update(id, c);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return service.login(loginDTO.getEmail(), loginDTO.getContrasena())
                .<ResponseEntity<?>>map(cliente -> ResponseEntity.ok(cliente))
                .orElseGet(() -> ResponseEntity.status(401).body("Credenciales incorrectas o usuario no encontrado"));
    }
}
