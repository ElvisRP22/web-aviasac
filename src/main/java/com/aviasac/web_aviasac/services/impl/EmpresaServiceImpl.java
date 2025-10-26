package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.Empresa;
import com.aviasac.web_aviasac.respository.EmpresaRepository;
import com.aviasac.web_aviasac.services.EmpresaService;

@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empresa> findById(Integer id) {
        return empresaRepository.findById(id);
    }

    @Override
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public void deleteById(Integer id) {
        empresaRepository.deleteById(id);
    }
}

