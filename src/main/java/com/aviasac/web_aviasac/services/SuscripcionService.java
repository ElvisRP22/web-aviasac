package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;

import com.aviasac.web_aviasac.model.Suscripcion;

public interface SuscripcionService {
    List<Suscripcion> findAll();
    Optional<Suscripcion> findById(Integer id);
    Suscripcion save(Suscripcion suscripcion);
    void deleteById(Integer id);
    boolean existsByEmail(String email);
    boolean existById(Integer id);
    Long suscriptoresTotales();
}
