package com.GestionSNKR.SnearksSource.repository;

import com.GestionSNKR.SnearksSource.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByClienteId(Long clienteId);
}
