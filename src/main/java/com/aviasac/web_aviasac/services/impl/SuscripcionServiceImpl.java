package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.Suscripcion;
import com.aviasac.web_aviasac.respository.SuscripcionRepository;
import com.aviasac.web_aviasac.services.SuscripcionService;

@Service
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService {

    private final SuscripcionRepository repo;

    public SuscripcionServiceImpl(SuscripcionRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Suscripcion> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Suscripcion> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Suscripcion save(Suscripcion suscripcion) {
        return repo.save(suscripcion);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
