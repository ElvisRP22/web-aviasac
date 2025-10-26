package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;

import com.aviasac.web_aviasac.model.Trabajo;

public interface TrabajoService {
    List<Trabajo> findAll();
    Optional<Trabajo> findById(Integer id);
    Trabajo save(Trabajo trabajo);
    void deleteById(Integer id);
}
