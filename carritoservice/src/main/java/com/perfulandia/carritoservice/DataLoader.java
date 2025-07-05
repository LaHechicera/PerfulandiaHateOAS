package com.perfulandia.carritoservice;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.repository.CarritoRepository;

import org.springframework.boot.CommandLineRunner; //Permite ejecutar codigo automaticamente
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CarritoRepository repo;

    public DataLoader(CarritoRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args){
        //Se hacen pruebas con producto en la BD
        repo.save(new Carrito(null, "Credito", 5, "Perfume kul"));
        repo.save(new Carrito(null, "Debito", 1, "Perfumito"));
        repo.save(new Carrito(null, "Efectivo", 2, "Agatha"));

    }
}
