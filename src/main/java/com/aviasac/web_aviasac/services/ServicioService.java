package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;

import com.aviasac.web_aviasac.model.Servicio;

public interface ServicioService {
    List<Servicio> findAll();
    Optional<Servicio> findById(Integer id);
    Servicio save(Servicio servicio);
    void deleteById(Integer id);
    Long serviciosTotales();
}
