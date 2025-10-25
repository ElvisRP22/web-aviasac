package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;
import com.aviasac.web_aviasac.model.Solicitud;

public interface SolicitudService {
    List<Solicitud> findAll();
    Optional<Solicitud> findById(Integer id);
    Solicitud save(Solicitud solicitud);
    void deleteById(Integer id);
}
