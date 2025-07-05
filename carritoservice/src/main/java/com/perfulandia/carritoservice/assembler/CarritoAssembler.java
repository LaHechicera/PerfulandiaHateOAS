package com.perfulandia.carritoservice.assembler;

import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.controller.CarritoController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;//Convierte modelo en HATEOAS
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>>  {

    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                linkTo(methodOn(CarritoController.class).getHate(carrito.getId())).withSelfRel(),

                //Enlace a toda la lista
                linkTo(methodOn(CarritoController.class).listarHate()).withRel("carritos")
                );
    }
}
