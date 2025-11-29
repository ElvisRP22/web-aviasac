package com.aviasac.web_aviasac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
    
}
