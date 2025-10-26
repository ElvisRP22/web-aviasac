package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.Rol;
import com.aviasac.web_aviasac.respository.RolRepository;
import com.aviasac.web_aviasac.services.RolService;

@Service
@Transactional
public class RolServiceImpl implements RolService {

    private final RolRepository repo;

    public RolServiceImpl(RolRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Rol save(Rol rol) {
        return repo.save(rol);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}

