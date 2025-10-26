package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.PreguntaFrecuente;
import com.aviasac.web_aviasac.respository.PreguntaFrecuenteRepository;
import com.aviasac.web_aviasac.services.PreguntaFrecuenteService;

@Service
@Transactional
public class PreguntaFrecuenteServiceImpl implements PreguntaFrecuenteService {

    private final PreguntaFrecuenteRepository repo;

    public PreguntaFrecuenteServiceImpl(PreguntaFrecuenteRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaFrecuente> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PreguntaFrecuente> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public PreguntaFrecuente save(PreguntaFrecuente pf) {
        return repo.save(pf);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
