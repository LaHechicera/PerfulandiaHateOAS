package com.perfulandia.productservice.controller;

import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Nuevas importaciones DTO conexion al MS de usuario
import org.springframework.web.client.RestTemplate;
//Herramienta para hacer peticiones a HTTP  a otros microservicios
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.perfulandia.productservice.assembler.ProductoAssembler;
import org.springframework.hateoas.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin("*")
public class ProductoController {

    private final ProductoService servicio;
    private final RestTemplate restTemplate;
    private final ProductoAssembler productoAssembler;
    //final -> esto no va a cambiar durante la ejecucion del proyecto, funciona asi que no tocar si se ve
    public ProductoController(ProductoService servicio, RestTemplate restTemplate, ProductoAssembler productoAssembler) {
        this.servicio = servicio;
        this.restTemplate = restTemplate;
        this.productoAssembler = productoAssembler;
    }

    //Listar
    @GetMapping
    public List<Producto> listar(){
        return servicio.listar();
    }

    //Guardar
    @PostMapping
    public Producto guardar(@RequestBody Producto producto){
        return servicio.guardar(producto);
    }

    //Buscar por id
    @GetMapping("/{id}")
    public Producto buscar(@PathVariable long id){
        return servicio.buscarPorId(id);
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id){
        servicio.eliminar(id);
    }

    //Nuevo metodo
    @GetMapping("/usuario/{id}")
    public Usuario obtenerUsuario(@PathVariable long id){
        return restTemplate.getForObject("http://localhost:8081/api/v1/usuarios/"+id, Usuario.class);
    }

    //HATEOAS
    //GET
    @GetMapping("/hateoas/get")
    public CollectionModel<EntityModel<Producto>> listarHate() {

        // Esta parte estaba correcta. Crea una lista de EntityModel<Producto>.
        List<EntityModel<Producto>> productos = servicio.listar().stream()
                .map(productoAssembler::toModel)
                .collect(Collectors.toList());

        // Ahora la firma del método coincide con lo que CollectionModel.of() devuelve.
        // Esto ya no dará error.
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listarHate()).withSelfRel());
    }

    @GetMapping("/hateoas/get/{id}")
    public EntityModel<Producto> getHate(@PathVariable long id){
        Producto producto = servicio.buscarPorId(id);
        return productoAssembler.toModel(producto);
    }

    //POST
    @PostMapping("/hateoas/post")
    public ResponseEntity<EntityModel<Producto>> postHate(@RequestBody Producto producto){
        Producto producto1 = servicio.guardar(producto);
        EntityModel<Producto> productoModel = productoAssembler.toModel(producto1);
        return ResponseEntity
                .created(productoModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(productoModel);
    }

    //DELETE
    @DeleteMapping("/hateoas/delete/{id}")
    public void  deleteHate(@PathVariable long id){
        servicio.eliminar(id);
    }

}
