package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;

import com.aviasac.web_aviasac.model.PreguntaFrecuente;

public interface PreguntaFrecuenteService {
    List<PreguntaFrecuente> findAll();
    List<PreguntaFrecuente> findOnlyActive();
     Optional<PreguntaFrecuente> findById(Integer id);
    PreguntaFrecuente save(PreguntaFrecuente preguntaFrecuente);
    void deleteById(Integer id);
}
