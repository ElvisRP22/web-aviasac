package com.aviasac.web_aviasac.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    
}
