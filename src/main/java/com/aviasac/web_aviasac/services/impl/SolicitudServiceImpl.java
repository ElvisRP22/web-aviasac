package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.Solicitud;
import com.aviasac.web_aviasac.repository.SolicitudRepository;
import com.aviasac.web_aviasac.services.SolicitudService;

@Service
@Transactional
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository repo;

    public SolicitudServiceImpl(SolicitudRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Solicitud> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Solicitud> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        return repo.save(solicitud);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Long solicitudesTotales() {
        return repo.count();
    }
}

