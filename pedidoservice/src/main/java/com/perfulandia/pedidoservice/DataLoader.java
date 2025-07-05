package com.perfulandia.pedidoservice;

import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.repository.PedidoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final PedidoRepository repo;

    public DataLoader(PedidoRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args){
        repo.save(new Pedido(null, 56, "Completado"));
        repo.save(new Pedido(null, 45, "En proceso"));
        repo.save(new Pedido(null, 21, "En proceso"));
    }
}
