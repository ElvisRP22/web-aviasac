package com.aviasac.web_aviasac.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
}
