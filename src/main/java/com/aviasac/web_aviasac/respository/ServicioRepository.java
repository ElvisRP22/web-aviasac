package com.aviasac.web_aviasac.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    
}
