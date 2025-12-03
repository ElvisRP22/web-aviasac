package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.Testimonio;
import com.aviasac.web_aviasac.model.Usuario;
import com.aviasac.web_aviasac.repository.TestimonioRepository;
import com.aviasac.web_aviasac.services.TestimonioService;

@Service
@Transactional
public class TestimonioServiceImpl implements TestimonioService {

    private final TestimonioRepository repo;

    public TestimonioServiceImpl(TestimonioRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Testimonio> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Testimonio> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Testimonio save(Testimonio testimonio) {
        return repo.save(testimonio);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeTestimonioDeUsuario(Usuario usuario) {
        return repo.existsByUsuario(usuario);
    }

    @Override
    public Long testimoniosTotales() {
        return repo.count();
    }
}
