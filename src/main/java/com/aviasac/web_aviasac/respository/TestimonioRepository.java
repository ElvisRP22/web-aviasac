package com.aviasac.web_aviasac.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aviasac.web_aviasac.model.Testimonio;

public interface TestimonioRepository extends JpaRepository<Testimonio, Integer> {
    
}
