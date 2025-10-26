package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.Servicio;
import com.aviasac.web_aviasac.respository.ServicioRepository;
import com.aviasac.web_aviasac.services.ServicioService;

@Service
@Transactional
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository repo;

    public ServicioServiceImpl(ServicioRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Servicio> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Servicio> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Servicio save(Servicio servicio) {
        return repo.save(servicio);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
