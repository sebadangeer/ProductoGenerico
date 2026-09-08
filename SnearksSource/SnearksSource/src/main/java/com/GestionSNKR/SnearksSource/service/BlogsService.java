package com.GestionSNKR.SnearksSource.service;

import com.GestionSNKR.SnearksSource.model.Blogs;
import com.GestionSNKR.SnearksSource.repository.BlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogsService {
    private final BlogsRepository repo;

    @Autowired
    public BlogsService(BlogsRepository repo) {
        this.repo = repo;
    }

    public List<Blogs> findAll() {
        return repo.findAll();
    }

    public Optional<Blogs> findById(Long id) {
        return repo.findById(id);
    }

    public Blogs save(Blogs blog) {
        return repo.save(blog);
    }

    public Blogs update(Long id, Blogs blog) {
        return repo.findById(id).map(existing -> {
            existing.setNombre_post(blog.getNombre_post());
            existing.setDescripcion_post(blog.getDescripcion_post());
            existing.setContenido_post(blog.getContenido_post());
            existing.setLink_imagen_post(blog.getLink_imagen_post());
            return repo.save(existing);
        }).orElseGet(() -> {
            blog.setId_posteo(id);
            return repo.save(blog);
        });
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
