package com.perfulandia.carritoservice.controller;

import com.perfulandia.carritoservice.model.Producto;
import com.perfulandia.carritoservice.model.Carrito;
import com.perfulandia.carritoservice.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

//Llama metodos del controlador
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Permite agregar links HATEOAS basados en metodos
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.perfulandia.carritoservice.assembler.CarritoAssembler;

import java.util.List;

import org.springframework.hateoas.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin("*")
public class CarritoController {

    private final CarritoService servicio;
    private final CarritoAssembler assembler;
    private final RestTemplate restTemplate; //Permite realizar solicitudes HTTP
    //final -> esto no va a cambiar durante la ejecucion del proyecto, funciona asi que no tocar si se ve

    public CarritoController(CarritoService servicio, RestTemplate restTemplate, CarritoAssembler assembler) {
        this.servicio = servicio;
        this.restTemplate = restTemplate;
        this.assembler = assembler;
    }

    //Listar
    @GetMapping
    public List<Carrito> listar() {
        return servicio.listar();
    }

    //buscar por id
    @GetMapping("/{id}")
    public Carrito buscar(@PathVariable long id) {
        return servicio.bucscarId(id);
    }

    //Guardar datos
    @PostMapping
    public Carrito guardar(@RequestBody Carrito carrito) {
        return servicio.guardar(carrito);
    }
    //Eliminar carritos por id
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id) {
        servicio.eliminar(id);
    }

    //Listar productos desde MS Producto
    @GetMapping("/producto/lista")
    public Producto[] productos() {
        return restTemplate.getForObject("http://localhost:8082/api/productos", Producto[].class);
    }

    //HATEOAS
    //GET
    @GetMapping("/hateoas/get")
    public CollectionModel<EntityModel<Carrito>> listarHate() {
        List<EntityModel<Carrito>> carritos = servicio.listar().stream()
                .map(assembler::toModel)//Se transforma cada producto en modelo de entidad EntityModel
                .collect(Collectors.toList());
        return CollectionModel.of(carritos, linkTo(methodOn(CarritoController.class).listarHate()).withSelfRel()); // se lista a si mismo
    }

    //GET por id
    @GetMapping("/hateoas/get/{id}")
    public EntityModel<Carrito> getHate(@PathVariable long id) {
        Carrito carrito = servicio.bucscarId(id); //busca por id
        return assembler.toModel(carrito);//devuelve producto con entity model
    }

    //POST
    @PostMapping("/hateoas/post")
    public ResponseEntity<EntityModel<Carrito>> postHate(@RequestBody Carrito carrito) {
        Carrito carrito1 = servicio.guardar(carrito);
        EntityModel<Carrito> carritoModel = assembler.toModel(carrito1);
        return ResponseEntity
                .created(carritoModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(carritoModel);
    }

    //DELETE
    @DeleteMapping("/hateoas/delete/{id}")
    public void deleteHate(@PathVariable long id) {
        servicio.eliminar(id);
    }

}
