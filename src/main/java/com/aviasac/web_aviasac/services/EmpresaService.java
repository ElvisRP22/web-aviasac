package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;

import com.aviasac.web_aviasac.model.Empresa;

public interface EmpresaService {
    
    List<Empresa> findAll();
    Optional<Empresa> findById(Integer id);
    Empresa save(Empresa empresa);
    void deleteById(Integer id);
}
