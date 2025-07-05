# 🧾 Proyecto: Transformación Digital - Perfulandia SPA

Este repositorio contiene el desarrollo técnico del sistema basado en microservicios para la empresa Perfulandia SPA, como parte de la Evaluación Parcial 2 de la asignatura **Desarrollo Full Stack I**.

## 📦 Descripción General del Proyecto

Se esta implementando un nuevo sistema de ventas para la compañia Perfulandia SPA, ya que su antigua sistema comenzo a fallar, presentando problemas de rendimiento y disponibilidad, nosotros al realizar el reemplazo del sistema monolitico vamos a beneficiar a la empresa dandoles un sistema con buena escalabilidad y eficiencia en el tiempo.

## 🧩 Arquitectura de Microservicios

> 📝 La estructura del sistema está conformado por cuatro microservicios, que consta de un servicio de 'Usuario', que se encarga de generar el usuario como cliente, agregando sus datos al sistema, luego esta el servicio de 'Producto', que se enlaza al servicio 'Usuario' para relacionar los productos escogidos con el usuario. Después está el servicio de 'Carrito', que tambien se enlaza pero con el servicio de 'Producto' para agregar los datos de producto al carrito de compras, sumando en esté, atributos de cantidad de productos y un método de pago. Y finalmente un servicio de 'Pedido' enlazado al servicio de 'Carrito', que registrará con una id de pedido la información del estado del pedido, con la id de compra que fué generada por el carrito para buscar los productos dentro el pedido.

La arquitectura del servicio de Perfulandia con sus servicios y clases:
```
Perfulandia./

├── pedidoservice/📦
│   └── controller📂
│       └── PedidoController.java
│   └── model📂
│       └── Carrito.java
│       └── Pedido.java
│   └── repository📂
│       └── PedidoRepository.java
│   └── service📂
│       └── PedidoService.java
└── 

├── carritoservice/📦
│   └── controller📂
│       └── CarritoController.java
│   └── model📂
│       └── Carrito.java
│       └── Producto.java
│   └── repository📂
│       └── CarritoRepository.java
│   └── service📂
│       └── CarritoService.java
└── 

├── productservice/📦
│   └── controller📂
│       └── ProductoController.java
│   └── model📂
│       └── Usuario.java
│       └── Producto.java
│   └── repository📂
│       └── ProductoRepository.java
│   └── service📂
│       └── ProductoService.java
└── 

├── usuarioservice/📦
│   └── controller📂
│       └── UsuarioController.java
│   └── model📂
│       └── Usuario.java
│   └── repository📂
│       └── UsuarioRepository.java
│   └── service📂
│       └── UsuarioService.java
└── 
```
### Microservicios Desarrollados

- `usuarioservice`: > 📝 Agrega nuevos usuarios.
- `productoservice`: > 📝 Agrega o elimina productos.
- `carritoservice`: > 📝 Crea carritos de compra, en base a la lista de productos en base de datos.
- `pedidoservice`: > 📝 Crea estados de pedidos, en base a la lista de carritos de la base de datos

## 🛠️ Tecnologías Utilizadas

Las herramientas utilizadas para el proyecto fueron:

- Spring Boot
- Maven
- MySQL
- Postman
- GitHub

Y utilizamos como framework:

- IntelliJ IDEA 2025

## 🗄️ Configuración de Bases de Datos

La Base de Datos se realizo con Laragon, el cual nos permitio crear un servidor local el que guardo los datos subidos a los microservicios. La configuracion de las conexiones se realizo mediante el archivo `application.properties` de la siguiente manera:
```
spring.application.name=carritoservice

server.port=8083

spring.datasource.url=jdbc:mysql://localhost:3306/perfulandia_carrito_01v
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
Para cada microservicio contiene un diferente `server.port=8083`, para que asi no hayan errores al momento de dar inicio a los microservisios en simultaneo.

## Definicion de Tablas y campos
Cada microservicio cuenta con una tabla en la base de datos local y columnas donde se almacenan los datos que se requieren.

- Microservicio Usuario: este contiene la tabla usuario, en la cual se deben rellenar los datos de "nombre", "correo" y "rol".
- Microservicio Producto: tiene la tabla producto, donde se rellenan los datos de "nombre", "precio" y "stock".
- Microservicio Carrito: contiene la tabla carrito, donde se rellenan los datos de "cantidad_productos", "metodo_pago" y "nombre_producto".
- Microservicio Pedido: contiene la tabla pedido, se rellenan los siguientes campos "id_numero_pedido" y "estado_pedido"


## 📮 Endpoints y Pruebas

Endpoints Microservicio Usuario
- GET: http://localhost:8081/api/v1/usuario
- GET por id: http://localhost:8081/api/v1/usuario/{id}
- POST: http://localhost:8081/api/v1/usuario
- DELETE: http://localhost:8081/api/v1/usuario/{id}

Endpoints Microservicio Producto
- GET: http://localhost:8082/api/producto
- GET por id: http://localhost:8082/api/producto/{id}
- GET id a MS Usuario: http://localhost:8082/api/producto/usuario/{id}
- POST: http://localhost:8082/api/producto
- DELETE por id: http://localhost:8082/api/producto/{id}

Endpoints Microservicio Carrito
- GET: http://localhost:8083/api/carrito
- GET por id: http://localhost:8083/api/carrito/{id}
- GET lista a MS Productos: http://localhost:8083/api/carrito/producto/lista
- POST: http://localhost:8083/api/carrito
- DELETE: http://localhost:8083/api/carrito

Endpoints Microservicio Pedido
- GET: http://localhost:8084/api/pedido
- GET por id: http://localhost:8084/api/pedido/{id}
- GET lista a MS Carrito: http://localhost:8084/api/pedido/estado
- GET id a MS Carrito: http://localhost:8084/api/pedido/estado/{id}
- POST: http://localhost:8084/api/pedido
- DELETE: http://localhost:8084/api/pedido

## 🧑‍💻 Integrantes del Equipo

| Nombre                  | Rol en el proyecto         | Servicio principal trabajado |
|-------------------------|----------------------------|------------------------------|
| Camila González | Backend  | Carrito               |
| Andy Villarroel | Backend  | Pedidos       |

## 📂 Estructura del Repositorio

El repositorio cuenta con 5 carpetas las cuales 4 corresponden a los diferentes microservicios, cada uno con su propio `pom.xml`, carpeta src la cual contiene los archivos principales para que los micro servicios funcionen y puedan conectarse a la base de datos.

A continuacion se observa como se ve el repositorio.
```

📦 PerfulandiaBackendV1
├── .idea
├── carritoservice
├── pedidoservice
├── productoservice
├── usuarioservice
└── README.md

```

## 👥 Colaboración en GitHub

Primero que todo se realizo la creación del repositorio en GitHub y se crearon las ramas `main` que contribuyo principalmete a los Pull Request, las ramas `Andy-Villarroel` (PedidoService) y `Camila-Gonzalez` (CarritoService) las cuales trabajaron en los microservicios correspondientes.

Como equipo consideramos que la mejor manera de trabajar colaborativamente fue la comunicacion, los que nos ayudo a coordinar commits frecuentemente cada vez que se realizaba avance en cualquier microservicio.

Se realizaron `push` constantes por parte de los colaboradores, para mantener lo más actualizado el repositorio y la rama `master`.


## 📈 Lecciones Aprendidas

Fue un trabajo bastante arduo, con varias complicaciones en el camino, tales como tratar de hacer el trabajo en GitHub ya que de vez en cuando se tenian problemas al realizar un `git pull`, o al tratar de hacer que los microservicios se conecten de manera correcta. Realizar este trabajo nos ayudo a comprender la cantidad de tiempo que demora el hacer microservicios que se comuniquen entre si y lograr que lo hagan de la manera correcta, lo que nos da un vistazo de como funcionan y como podemos verlos dia a dia con las diferentes apliaciones que utilizamos cotidianamente.

---
