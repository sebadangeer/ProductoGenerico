package com.GestionSNKR.SnearksSource.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    /**
     * Entidad JPA que representa un Cliente en la base de datos.
     */
    @Entity
    @Table(name = "blogs")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Blogs {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_posteo;
        @Column(name = "nombre_post", length = 250, nullable = false)
        private String nombre_post;

        @Column(name = "descripcion_post", length = 100)
        private String descripcion_post;

        @Column(name = "contenido_post", length = 500)
        private String contenido_post;

        @Column(name = "link_imagen_post", length = 250)
        private String link_imagen_post;
        }
