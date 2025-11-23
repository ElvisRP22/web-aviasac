package com.aviasac.web_aviasac.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.PreguntaFrecuente;

public interface PreguntaFrecuenteRepository extends JpaRepository<PreguntaFrecuente, Integer> {
    List<PreguntaFrecuente> findByEstadoTrue();
}
