package com.aviasac.web_aviasac.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.TipoDeCultivo;

public interface TipoDeCultivoRepository extends JpaRepository<TipoDeCultivo, Integer> {
    
}
