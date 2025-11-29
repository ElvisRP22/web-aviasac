package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.Trabajo;
import com.aviasac.web_aviasac.repository.TrabajoRepository;
import com.aviasac.web_aviasac.services.TrabajoService;

@Service
@Transactional
public class TrabajoServiceImpl implements TrabajoService {

    private final TrabajoRepository repo;

    public TrabajoServiceImpl(TrabajoRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trabajo> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Trabajo> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Trabajo save(Trabajo trabajo) {
        return repo.save(trabajo);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
