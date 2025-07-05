package com.perfulandia.pedidoservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Carrito {
    private long id;
    private String metodoPago;
    private double cantidadProductos;
    private String nombreProducto;
}
