package com.GestionSNKR.SnearksSource; // Ajusta el paquete si lo moviste a config

import com.GestionSNKR.SnearksSource.model.Blogs;
import com.GestionSNKR.SnearksSource.model.Producto;
import com.GestionSNKR.SnearksSource.repository.BlogsRepository;
import com.GestionSNKR.SnearksSource.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class ConfiguracionInicial {

    private static Map<String, Integer> inventarioBase() {
        return Map.of(
                "35", 1,
                "36", 2,
                "37", 4,
                "38", 5,
                "39", 3,
                "40", 4,
                "41", 2,
                "42", 1
        );
    }

    @Bean
    CommandLineRunner iniciarBaseDeDatos(ProductoRepository productoRepository, BlogsRepository blogsRepository) {
        return args -> {

            // ==========================================
            // PRECARGA DE ZAPATILLAS
            // ==========================================
            if (productoRepository.count() == 0) {
                System.out.println("Iniciando la precarga de zapatillas en la base de datos H2...");

                List<Producto> zapatillas = List.of(
                        new Producto(null, 149000, "Air Jordan Retro 1", "Air Jordan 1 Retro en cuero de Jordan con puntera redonda, suela de goma, logo Nike, al tobillo.", "img/re1.png", "Jordan", inventarioBase()),
                        new Producto(null, 165000, "Air Jordan Retro 3", "Air Jordan 3 Retro OG black/cement en cuero, logo en relieve en la lengüeta, cierre con agujetas en la parte delantera.", "img/re3.png", "Jordan", inventarioBase()),
                        new Producto(null, 175000, "Air Jordan Retro 4", "Jumpman característico, puntera redonda, cierre con agujetas en la parte delantera, parche del logo en la lengüeta.", "img/re4.png", "Jordan", inventarioBase()),
                        new Producto(null, 189000, "Air Jordan 5 “UNC”", "Azul claro, Jumpman característico, cierre con agujetas en la parte delantera, lengüeta oversize , plantilla con logo y suela de goma.", "img/re5.png", "Jordan", inventarioBase()),
                        new Producto(null, 199000, "Air Jordan 11 Retro", "Air Jordan 11 Retro de Jordan con puntera redonda, cierre con cordones en la parte delantera, plantilla con logo, acolchado en los tobillos y suela dentada de goma.", "img/re11.png", "Jordan", inventarioBase()),
                        new Producto(null, 210000, "Air Jordan 12 Retro", "Air Jordan 12 Retro en cuero de color rojo, blanco y negro de Jordan con puntera de almendra, agujetas en la parte delantera, logo en relieve en la lengüeta, plantilla con logo, acolchado en los tobillos, lengüeta en la parte posterior, logo en el contrafuerte y suela de goma.", "img/re12.png", "Jordan", inventarioBase()),
                        new Producto(null, 230000, "Air Jordan 13 Retro", "Air Jordan 13 Retro en goma de Jordan, en color axul con blanco, lengüeta oversize.", "img/re13.png", "Jordan", inventarioBase()),
                        new Producto(null, 250000, "Air Jordan 1 Low Travis Scott", "Air Jordan 1 Low OG SP de Jordan x Travis Scott, cuero, logo Air Jordan Wings característico, Colaboración exclusiva con Travis Scott", "img/tra1.png", "Jordan", inventarioBase()),
                        new Producto(null, 250000000, "Maxi Black Cat", "Colaboración exclusiva con Travis Scott", "img/maxi.jpg", "Viejas Desnudas", inventarioBase()),
                        new Producto(null, 149000, "Mercurial Vapor 16", "¿La velocidad es tu obsesión? También lo es para las mayores estrellas del fútbol. Por eso creamos estos tacos Elite con una unidad Air Zoom mejorada de 3/4 de largo. Entregan una sensación de propulsión que te ayuda a despegar desde la línea de fondo con máxima rapidez.", "img/imgsp/m12.png", "Nike Sports", inventarioBase()),
                        new Producto(null, 165000, "Phantom 6 Low Elite", "Phantom 6 Elite con el revolucionario Gripknit para potenciar tu precisión. La textura adherente mejora el contacto del pie con la pelota. ", "img/imgsp/m2.png", "Nike Sports", inventarioBase()),
                        new Producto(null, 175000, "Mercurial Vapor 17 Elite", "Diseñados para impulsar sprints rápidos, la suave capellada NikeSkin de los Vapor 17 Academy te prepara para el despegue. ", "img/imgsp/m31.png", "Nike Sports", inventarioBase()),
                        new Producto(null, 189000, "Tiempo Maestro Academy", "Con los Tiempo Maestro Academy, tendrás el control de cada toque de la pelota. Confeccionados con cuero FlyTouch, tienen una textura suave que te ofrece un control total de la pelota.", "img/imgsp/ti.png", "Nike Sports", inventarioBase()),
                        new Producto(null, 199000, "Mercurial Superfly 11 Elite SE", "Hecho para atacar en un parpadeo, el Superfly 11 Elite libera una velocidad explosiva al combinar la amortiguación Air Zoom con la espuma ZoomX, ultrarreactiva.", "img/imgsp/e2.png", "Nike Sports", inventarioBase()),
                        new Producto(null, 210000, "Phantom 6 Low Elite \"Alexia Putellas\"", "Desde el trono de Alexia, lo ve todo. Para asegurarse de que pueda dominar la cancha con precisión, las Phantom 6 Elite están equipadas con la revolucionaria tecnología Gripknit y una placa de tracción Cyclone 360.", "img/imgsp/f1 (1).png", "Nike Sports", inventarioBase()),
                        new Producto(null, 230000, "Mercurial Superfly 11 Elite", "Los Superfly 11 Elite son increíblemente rápidos. Desatan una velocidad explosiva al combinar la amortiguación elástica Air Zoom con la espuma ultraresponsiva ZoomX.", "img/imgsp/g1.png", "Nike Sports", inventarioBase()),
                        new Producto(null, 250000, "United Tiempo Maestro Academy", "Te permiten dominar cada toque. Su cuero sintético FlyTouch, con textura suave, te da total control del balón para desestabilizar cualquier defensa.", "img/imgsp/h1.png", "Nike Sports", inventarioBase()),
                                new Producto(null, 122990, "Air Force 1 '07", "Diseño icónico de corte bajo con silueta de cuero y amortiguación Nike Air para confort diario.", "img/urban/a1 (3).png", "Nike Urbano", Map.of("39", 8, "40", 12, "41", 10, "42", 6)),
                                new Producto(null, 57990, "SB Dunk Low Pro ISO", "Edición de skateboarding con acolchado acolchado en la lengüeta y unidad Zoom Air en el talón.", "img/urban/e1.png", "Nike Urbano", Map.of("38", 4, "39", 6, "40", 8, "41", 5)),
                                new Producto(null, 86990, "Dunk Low Retro", "Ícono del baloncesto retro con revestimientos de cuero duradero y combinación de colores clásicos.", "img/urban/b3.png", "Nike Urbano", Map.of("39", 5, "40", 9, "41", 11, "42", 7)),
                                new Producto(null, 69990, "Court Vision Low", "Estilo ochentero inspirado en las canchas con materiales sintéticos sustentables y perfil bajo.", "img/urban/court.png", "Nike Urbano", Map.of("39", 10, "40", 15, "41", 12, "42", 8)),
                                new Producto(null, 139990, "Air Max 90", "Clásico atemporal con suela con diseño gofre, revestimientos cosidos y la emblemática cámara de aire.", "img/urban/90.png", "Nike Urbano", Map.of("38", 3, "39", 7, "40", 10, "41", 9)),
                                new Producto(null, 99990, "Blazer Mid '77 Vintage", "Estilo retro con acabado en cuero y detalles en gamuza, entresuela vintage y cuello acolchado alto.", "img/urban/blazer.png", "Nike Urbano", Map.of("39", 6, "40", 11, "41", 8, "42", 7)),
                                new Producto(null, 149990, "Air Max 1", "El modelo pionero de la cápsula de aire visible con una mezcla elegante de malla y gamuza.", "img/urban/air.png", "Nike Urbano", Map.of("38", 5, "39", 8, "40", 10, "41", 6)),
                                new Producto(null, 84990, "Air Max Plus Tn", "Impulsa tu actitud con el Nike Air Max Plus, una experiencia Air optimizada que ofrece una estabilidad premium y una amortiguación increíble", "img/urban/tn.png", "Nike Urbano", Map.of("39", 8, "40", 12, "41", 10, "42", 6))
                );

                productoRepository.saveAll(zapatillas);
                System.out.println("¡Zapatillas precargadas con éxito!");
            }




            // ==========================================
            // PRECARGA DE BLOGS
            // ==========================================
            if (blogsRepository.count() == 0) {
                System.out.println("Iniciando la precarga de blogs en la base de datos H2...");

                List<Blogs> posts = List.of(
                        new Blogs(null,
                                "La historia de las Air Jordan 1",
                                "Descubre cómo un par de zapatillas cambió el mundo del baloncesto.",
                                "En 1985, Nike y Michael Jordan se unieron para crear una de las siluetas más icónicas de la historia. A pesar de las multas de la NBA por romper el código de vestimenta, las Jordan 1 se convirtieron en un fenómeno cultural que perdura hasta el día de hoy.",
                                "https://static.nike.com/a/images/f_auto,cs_srgb/w_1920,c_limit/89c121fc-3d07-4de0-aef6-bcc9c2764a2c/air-jordan-1-2022-lost-and-found-chicago-la-inspiraci%C3%B3n-detr%C3%A1s-del-dise%C3%B1o.jpg"),

                        new Blogs(null,
                                "Cómo cuidar tus sneakers",
                                "Tips y trucos para mantener tus zapatillas como nuevas.",
                                "Mantener tus zapatillas limpias es fundamental para que duren más tiempo. Recuerda usar cepillos de cerdas suaves, productos de limpieza específicos para cada material (cuero, gamuza, tela) y nunca, jamás, meterlas a la lavadora o secadora.",
                                "https://images.unsplash.com/photo-1600185365483-26d7a4cc7519?auto=format&fit=crop&w=600&q=80"),

                        new Blogs(null,
                                "Lanzamientos más esperados de este año",
                                "El calendario definitivo para los amantes del streetwear.",
                                "Este año promete colaboraciones increíbles. Desde nuevas ediciones de J balvin retro 4 hasta los rediseños clásicos de Nike Dunk y Yeezy. Mantente atento a nuestras redes para las fechas exactas de lanzamiento en nuestra tienda.",
                                "https://cdn.shopify.com/s/files/1/0631/6644/7787/files/Screenshot_106_2048x2048.png?v=1781704062")





                        );

                blogsRepository.saveAll(posts);
                System.out.println("¡Blogs precargados con éxito!");
            }
        };

    }
}