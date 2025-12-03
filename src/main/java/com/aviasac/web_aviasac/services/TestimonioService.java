package com.aviasac.web_aviasac.services;

import java.util.List;
import java.util.Optional;

import com.aviasac.web_aviasac.model.Testimonio;
import com.aviasac.web_aviasac.model.Usuario;

public interface TestimonioService {
    List<Testimonio> findAll();
    Optional<Testimonio> findById(Integer id);
    Testimonio save(Testimonio testimonio);
    void deleteById(Integer id);
    boolean existeTestimonioDeUsuario(Usuario usuario);
    Long testimoniosTotales();
}
