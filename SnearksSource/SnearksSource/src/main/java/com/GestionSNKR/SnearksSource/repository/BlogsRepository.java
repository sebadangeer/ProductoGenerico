package com.GestionSNKR.SnearksSource.repository;

import com.GestionSNKR.SnearksSource.model.Blogs;
import com.GestionSNKR.SnearksSource.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogsRepository extends JpaRepository<Blogs, Long> {

}
