package com.perfulandia.usuarioservice;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository repo;

    public DataLoader(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args){
        repo.save(new Usuario(null, "Camila Gonzalez", "cami@gmail.com", "Gerente"));
    }
}
