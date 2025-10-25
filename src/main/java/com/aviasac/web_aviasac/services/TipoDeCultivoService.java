package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;

import com.aviasac.web_aviasac.model.TipoDeCultivo;

public interface TipoDeCultivoService {
    List<TipoDeCultivo> findAll();
    Optional<TipoDeCultivo> findById(Integer id);
    TipoDeCultivo save(TipoDeCultivo tipoDeCultivo);
    void deleteById(Integer id);
}
