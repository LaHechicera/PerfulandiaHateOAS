package com.perfulandia.carritoservice.service;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.repository.CarritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepo;
    //Constructor para consumir Interface
    public CarritoService(CarritoRepository carritoRepo) {
        this.carritoRepo = carritoRepo;
    }

    //Listar carritos
    public List<Carrito> listar(){
        return carritoRepo.findAll();
    }

    //Guardar Carrito
    public Carrito guardar(Carrito carrito){
        return carritoRepo.save(carrito);
    }

    //Eliminar por id
    public void eliminar(long id){
        carritoRepo.deleteById(id);
    }

    public Carrito bucscarId(long id){
        return carritoRepo.findById(id).orElse(null);
    }
    //Agregar productos al carro
    //Esta en veremos :v

}
