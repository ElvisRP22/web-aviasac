package com.aviasac.web_aviasac.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aviasac.web_aviasac.model.Usuario;
import com.aviasac.web_aviasac.respository.UsuarioRepository;
import com.aviasac.web_aviasac.services.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioServiceImpl(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Usuario> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
