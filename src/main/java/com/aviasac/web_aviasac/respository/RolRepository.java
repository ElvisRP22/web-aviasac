package com.aviasac.web_aviasac.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
}
