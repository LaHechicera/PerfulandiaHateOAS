package com.perfulandia.productservice.assembler;

import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.controller.ProductoController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).getHate(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listarHate()).withRel("productos")
                );
    }
}
