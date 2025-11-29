package com.aviasac.web_aviasac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Testimonio;
import com.aviasac.web_aviasac.model.Usuario;

public interface TestimonioRepository extends JpaRepository<Testimonio, Integer> {
    boolean existsByUsuario(Usuario usuario);
}
