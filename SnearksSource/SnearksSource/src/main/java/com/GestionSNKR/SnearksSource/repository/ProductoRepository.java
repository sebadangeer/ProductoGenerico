package com.GestionSNKR.SnearksSource.repository;

import com.GestionSNKR.SnearksSource.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

        // Método derivado para buscar productos por categoría exacta
        List<Producto> findByTipoCategoriaIgnoreCase(String tipoCategoria);
    }
