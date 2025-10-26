package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;

import com.aviasac.web_aviasac.model.Rol;

public interface RolService {
    List<Rol> findAll();
    Optional<Rol> findById(Integer id);
    Rol save(Rol rol);
    void deleteById(Integer id);
}
