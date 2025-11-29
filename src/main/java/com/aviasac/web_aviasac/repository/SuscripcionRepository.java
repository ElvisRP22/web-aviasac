package com.aviasac.web_aviasac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Suscripcion;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Integer> {
    boolean existsByEmail(String email);
}
