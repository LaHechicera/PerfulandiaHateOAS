package com.perfulandia.pedidoservice.service;

import com.perfulandia.pedidoservice.repository.PedidoRepository;
import com.perfulandia.pedidoservice.model.Pedido;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private PedidoRepository repo;
    public PedidoService(PedidoRepository repo, PedidoRepository pedidoRepository) {
        this.repo = repo;
        this.pedidoRepository = pedidoRepository;
    }

    //Listado pedido
    public List<Pedido> listar() {
        return repo.findAll();
    }

    //Guardar pedido
    public Pedido guardar(Pedido pedido) {
        return repo.save(pedido);
    }

    //Ver pedido
    public Pedido ver(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new RuntimeException("Pedido no encontrado"));
    }
    //Eliminar
    public void eliminar(long id){
        repo.deleteById(id);
    }


}
