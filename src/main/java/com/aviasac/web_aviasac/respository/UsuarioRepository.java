package com.aviasac.web_aviasac.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
}
