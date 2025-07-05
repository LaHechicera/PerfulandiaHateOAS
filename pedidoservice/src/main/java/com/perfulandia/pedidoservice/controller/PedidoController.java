package com.perfulandia.pedidoservice.controller;

import com.perfulandia.pedidoservice.assembler.PedidoAssembler;
import com.perfulandia.pedidoservice.model.Carrito;
import com.perfulandia.pedidoservice.model.Pedido;
import com.perfulandia.pedidoservice.service.PedidoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

// Importaciones estáticas para HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService service;
    private final RestTemplate restTemplate;
    private final PedidoAssembler assembler;

    public PedidoController(PedidoService service, RestTemplate restTemplate, PedidoAssembler assembler) {
        this.service = service;
        this.restTemplate = restTemplate;
        this.assembler = assembler;
    }

    @GetMapping
    public List<Pedido> listar() {
        return service.listar();
    }

    @PostMapping
    public Pedido enviar (@RequestBody Pedido pedido) {
        return service.guardar(pedido);
    }

    @GetMapping("/estado/{id}")
    public Carrito estado (@PathVariable long id) {
        return restTemplate.getForObject("http://localhost:8083/api/carrito/"+id,Carrito.class);
    }

    //Listar productos
    @GetMapping("/estado")
    public Carrito[] productos() {
        return restTemplate.getForObject("http://localhost:8083/api/carrito", Carrito[].class);
    }

    @PostMapping("/generar")
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return service.guardar(pedido);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id) {
        service.eliminar(id);
    }


    //HATEOAS
    @GetMapping("/hateoas/get")
    public CollectionModel<EntityModel<Pedido>> listarHate() {
        // 1. Obtenemos la lista de pedidos del servicio
        List<EntityModel<Pedido>> pedidos = service.listar().stream()
                // 2. Convertimos cada Pedido a un EntityModel usando el Assembler
                .map(assembler::toModel)
                // 3. Recolectamos los resultados en una nueva lista
                .collect(Collectors.toList());

        // 4. Creamos el CollectionModel con la lista y un enlace "self" a este mismo método
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).listarHate()).withSelfRel());
    }

    @GetMapping("/hateoas/get/{id}")
    public EntityModel<Pedido> getHate(@PathVariable long id) {
        Pedido pedido = service.ver(id);
        return assembler.toModel(pedido);
    }


    @PostMapping("/hateoas/post")
// CAMBIO CLAVE: El tipo de retorno ahora es ResponseEntity<EntityModel<Pedido>>
    public ResponseEntity<EntityModel<Pedido>> postHate(@RequestBody Pedido pedido) {
        // 1. Guardas el nuevo pedido en la base de datos
        Pedido pedidoGuardado = service.guardar(pedido);

        // 2. Usas el assembler para convertir el objeto Pedido a un EntityModel (con enlaces HATEOAS)
        EntityModel<Pedido> pedidoModel = assembler.toModel(pedidoGuardado);

        // 3. Creas y devuelves una respuesta HTTP completa
        return ResponseEntity
                // .created() establece el código de estado 201 y la cabecera "Location"
                // con la URL del recurso recién creado (obtenida del enlace "self").
                .created(pedidoModel.getRequiredLink(IanaLinkRelations.SELF).toUri())

                // .body() establece el cuerpo de la respuesta con el modelo del pedido.
                .body(pedidoModel);
    }

    @DeleteMapping("/hateoas/delete/{id}")
    public void deleteHate(@PathVariable long id) {
        service.eliminar(id);
    }


}



