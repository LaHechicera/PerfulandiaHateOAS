package com.perfulandia.pedidoservice.assembler;

import com.perfulandia.pedidoservice.controller.PedidoController;
import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.service.PedidoService;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,

                linkTo(methodOn(PedidoController.class).getHate(pedido.getIdPedido())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarHate()).withRel("pedidos")
                );
    }
}

