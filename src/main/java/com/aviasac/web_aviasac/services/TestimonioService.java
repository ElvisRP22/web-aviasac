package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;

import com.aviasac.web_aviasac.model.Testimonio;

public interface TestimonioService {
    List<Testimonio> findAll();
    Optional<Testimonio> findById(Integer id);
    Testimonio save(Testimonio testimonio);
    void deleteById(Integer id);
}
