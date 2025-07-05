package com.perfulandia.usuarioservice.controller;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import com.perfulandia.usuarioservice.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//Llama metodos del controlador
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Permite agregar links HATEOAS basados en metodos
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.perfulandia.usuarioservice.assembler.UsuarioAssembler;

import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioAssembler assembler;
    //Constructor para poder consumir la interface
    public UsuarioController(UsuarioService service, UsuarioAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public List<Usuario> listar(){
        return service.listar();
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario){
        return service.guardar(usuario);
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }

    //HATEOAS
    //GET
    @GetMapping("/hateoas/get")
    public CollectionModel<EntityModel<Usuario>> listarHate() {

        // CAMBIO 2: La lista ahora es del tipo correcto: List<EntityModel<Usuario>>
        List<EntityModel<Usuario>> usuarios = service.listar().stream()
                .map(assembler::toModel) // o (usuario -> assembler.toModel(usuario))
                .collect(Collectors.toList()); // Esto ahora funciona sin error

        // CAMBIO 3 (Recomendado): El enlace "self" debe apuntar a este mismo método (listarHate)
        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).listarHate()).withSelfRel()
        );
    }

    @GetMapping("/hateoas/get/{id}")
    public EntityModel<Usuario> getHate(@PathVariable Long id){
        Usuario usuario = service.buscar(id);
        return assembler.toModel(usuario);
    }

    //POST
    @PostMapping("/hateoas/post")
    public ResponseEntity<EntityModel<Usuario>> postHate(@RequestBody Usuario usuario){
        Usuario usuario1 = service.guardar(usuario);
        EntityModel<Usuario> usuarioModel = assembler.toModel(usuario1);
        return ResponseEntity
                .created(usuarioModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(usuarioModel);
    }

    //DELETE
    @DeleteMapping("/hateoas/delete/{id}")
    public void deleteHate(@PathVariable Long id){
        service.eliminar(id);
    }

}
