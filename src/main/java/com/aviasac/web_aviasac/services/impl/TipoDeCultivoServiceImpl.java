package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.TipoDeCultivo;
import com.aviasac.web_aviasac.respository.TipoDeCultivoRepository;
import com.aviasac.web_aviasac.services.TipoDeCultivoService;

@Service
@Transactional
public class TipoDeCultivoServiceImpl implements TipoDeCultivoService {

    private final TipoDeCultivoRepository repo;

    public TipoDeCultivoServiceImpl(TipoDeCultivoRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDeCultivo> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDeCultivo> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public TipoDeCultivo save(TipoDeCultivo t) {
        return repo.save(t);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
