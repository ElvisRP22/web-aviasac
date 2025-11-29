package com.aviasac.web_aviasac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Trabajo;

public interface TrabajoRepository extends JpaRepository<Trabajo, Integer> {
    
}
