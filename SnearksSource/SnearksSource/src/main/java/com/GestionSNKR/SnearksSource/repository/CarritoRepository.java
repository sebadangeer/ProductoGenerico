package com.GestionSNKR.SnearksSource.repository;

import com.GestionSNKR.SnearksSource.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByClienteId(Long clienteId);
}
