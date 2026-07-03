# RestaurantEnzo - Arquitectura de Microservicios con Spring Boot

Sistema de gestion para una cadena de restaurantes, construido con una
arquitectura de microservicios (Spring Boot 4 + Spring Cloud) compuesta por
un **Eureka Server** (descubrimiento), un **Config Server** (configuracion
centralizada), un **API Gateway** (unico punto de entrada) y 10
microservicios de negocio, cada uno con su propia base de datos.

---

## 1. Contexto

RestaurantEnzo necesita digitalizar la operacion de sus locales: registrar
clientes, empleados y productos; tomar pedidos y reservas; gestionar pagos,
envios e inventario por local. Para soportar esto sin acoplar todo en una
sola aplicacion, el sistema se separo en microservicios independientes,
cada uno responsable de un dominio especifico, todos accesibles unicamente
a traves de un API Gateway centralizado y registrados en un Eureka Server
para poder escalar y ubicarse dinamicamente entre si.

## 2. Creditos

| Integrante                           | Microservicios documentados (Swagger + Testing) |
|--------------------------------------|---|
| **Integrante 1** *(Leandro Varela)*  | `autenticacion-service`, `clientes-service` |
| **Integrante 2** *(Nicolas Ortiz)*   | `empleados-service`, `locales-service` |
| **Integrante 3** (Benjamin Alegria)* | `productos-service`, `pedidos-service` |

> Reemplazar "Integrante 1/2/3" por los nombres reales en este archivo y en
> las clases `OpenApiConfig` / anotaciones `@Tag` de cada uno de los 6
> microservicios listados arriba (se deja una nota en cada archivo).

## 3. Arquitectura - Microservicios implementados

| Microservicio | Responsabilidad | Base de datos |
|---|---|---|
| `eureka-service` | Service Discovery | - |
| `config-server` | Configuracion centralizada (modo nativo) | - |
| `api-gateway` | Unico punto de entrada, ruteo + Swagger agregado | - |
| `autenticacion-service` | Usuarios y credenciales | `bd_autenticacion` |
| `clientes-service` | Clientes del restaurant | `bd_clientes` |
| `empleados-service` | Personal y turnos | `bd_empleados` |
| `productos-service` | Catalogo de productos | `bd_productos` |
| `pedidos-service` | Pedidos (consume `clientes-service` via Feign) | `bd_pedidos` |
| `reservas-service` | Reservas (consume `clientes-service` y `locales-service`) | `bd_reservas` |
| `locales-service` | Sucursales / locales | `bd_locales` |
| `envios-service` | Despacho de pedidos | `bd_envios` |
| `inventario-service` | Stock por local | `bd_inventario` |
| `pagos-service` | Pagos de pedidos | `bd_pagos` |

Cada microservicio de negocio:
- Usa **YAML** (`application.yml`), no `.properties`.
- Tiene `ddl-auto: validate` (el esquema lo crea **Flyway**, Hibernate solo valida).
- Usa `server.port: 0` (puerto aleatorio, registrado en Eureka).
- Solo es alcanzable **a traves del API Gateway** (no publica puertos en Docker).

## 4. Networking - Rutas principales del API Gateway

Unico puerto expuesto: **`http://localhost:8080`**

| Ruta | Microservicio destino |
|---|---|
| `/api/v1/usuarios/**` | autenticacion-service |
| `/api/v1/clientes/**` | clientes-service |
| `/api/v1/productos/**` | productos-service |
| `/api/v1/pedidos/**` | pedidos-service |
| `/api/v1/reservas/**` | reservas-service |
| `/api/v1/empleados/**` | empleados-service |
| `/api/v1/locales/**` | locales-service |
| `/api/v1/envios/**` | envios-service |
| `/api/v1/inventario/**` | inventario-service |
| `/api/v1/pagos/**` | pagos-service |

> **Nota de correccion:** varias rutas del Gateway original no coincidian
> con el `@RequestMapping` real del controlador (ej: apuntaban a `/empleados`
> en vez de `/api/v1/empleados`), por lo que esas peticiones nunca llegaban
> al microservicio. Se corrigieron todas para que coincidan exactamente.

## 5. Accesos - Documentacion Swagger

- **Swagger UI centralizado (Gateway):** `http://localhost:8080/swagger-ui.html`
  El selector ("Select a definition") permite elegir cualquiera de los 10
  microservicios activos.
- Swagger individual de cada servicio (uso interno/debug, vía Gateway):
  `http://localhost:8080/<servicio>/v3/api-docs` (ej: `/clientes-service/v3/api-docs`)
- Eureka Dashboard: `http://localhost:8761`
- Config Server: `http://localhost:8888`

## 6. Pruebas unitarias (Testing)

Se implementaron pruebas unitarias (JUnit 5 + Mockito) para la capa
`@Service` y la capa `@RestController` de los 6 microservicios asignados:

| Microservicio | Test `@Service` | Test `@RestController` |
|---|---|---|
| autenticacion-service | `UsuarioServiceTest` | `UsuarioControllerTest` |
| clientes-service | `ClienteServiceTest` | `ClienteControllerTest` |
| empleados-service | `EmpleadoServiceTest` | `EmpleadoControllerTest` |
| locales-service | `LocalServiceTest` | `LocalControllerTest` |
| productos-service | `ProductoServiceTest` | `ProductoControllerTest` |
| pedidos-service | `PedidoServiceTest` | `PedidoControllerTest` |

**Idea simple de cada tipo de test:**
- **Tests de Service:** se simulan (mock) el Repository y el Mapper con
  Mockito. Asi se prueba solo la logica del Service (busquedas, validaciones,
  excepciones), sin conectarse a una base de datos real.
- **Tests de Controller:** se usa `@WebMvcTest`, que levanta unicamente la
  capa web (sin base de datos ni Eureka). El Service se reemplaza por un mock
  (`@MockitoBean`) y se valida que cada endpoint responda el codigo HTTP y el
  JSON correctos (200, 400 por validacion, 204 al eliminar, etc).

Para correr los tests de un microservicio:
```bash
cd <microservicio>
./mvnw test
```

## 7. Guia de despliegue

### Opcion A - Docker (recomendada)

Requiere Docker y Docker Compose instalados.

```bash
# Desde la raiz del proyecto
docker compose up -d --build

# Ver logs de un servicio puntual
docker compose logs -f api-gateway

# Apagar todo
docker compose down

# Apagar y borrar tambien los datos de MySQL
docker compose down -v
```

Esto levanta: MySQL (con las 10 bases creadas automaticamente via
`init-db/01-init-databases.sql`), `eureka-service`, `config-server`,
`api-gateway` y los 10 microservicios de negocio. **Solo el puerto 8080
(API Gateway) queda expuesto al host.**

> Nota: el `Dockerfile` de cada modulo es un Dockerfile multi-stage
> estandar (Maven + JDK 25) pensado para evitar el error 429 de Maven
> Central (cachea las dependencias en una capa separada del codigo fuente).
> Si en clases se entrego un Dockerfile oficial distinto, ese debe
> reemplazar a este sin modificar sus directivas, segun la pauta.

### Opcion B - Local / hibrido (sin Docker)

Si Docker no esta disponible, el sistema corre igual en local **sin cambios
de codigo**, gracias a las variables de entorno con valores por defecto
(`localhost`) que ya trae cada `application.yml`.

1. Levantar una instancia de MySQL local en el puerto 3306 (usuario `root`,
   sin contraseña) y ejecutar el script `init-db/01-init-databases.sql` una
   vez para crear las 10 bases.
2. Levantar los servicios **en este orden**, cada uno en su propia terminal:
   ```bash
   cd eureka-service   && ./mvnw spring-boot:run
   cd config-server    && ./mvnw spring-boot:run
   cd api-gateway       && ./mvnw spring-boot:run
   cd autenticacion-service && ./mvnw spring-boot:run
   cd clientes-service      && ./mvnw spring-boot:run
   # ... y asi con el resto de microservicios de negocio
   ```
3. Acceder siempre a traves del Gateway: `http://localhost:8080`.

Si se necesita apuntar a una base de datos en otro host/puerto/usuario,
basta con definir las variables de entorno `DB_HOST`, `DB_PORT`,
`DB_USERNAME`, `DB_PASSWORD`, `EUREKA_HOST` y `CONFIG_HOST` antes de
levantar cada servicio (todas tienen un valor por defecto razonable).



## 8. Resumen de cambios (primera pasada)


- ✅ Todos los `application.properties` se migraron a `application.yml`
  (incluyendo `config-microservicios`). No queda ningun `.properties` de
  configuracion en el proyecto.
- ✅ `ddl-auto` fijado en `validate` en los 10 microservicios de negocio.
- ✅ Flyway ya estaba configurado por microservicio; se dejo `enabled: true`
  y `baseline-on-migrate: true` de forma explicita.
- ✅ Carpeta `init-db/` en la raiz con el script de creacion automatica de
  las 10 bases de datos.
- ✅ `server.port: 0` (puerto aleatorio) en los 10 microservicios de negocio.
- ✅ Rutas del API Gateway corregidas para que coincidan con los
  controladores reales (varias estaban rotas).
- ✅ `config-server` pasado a modo **native** (leia un repositorio Git de
  relleno que no existia; ahora funciona apuntando a `config-microservicios`).
- ✅ Docker: `Dockerfile` multi-stage en cada microservicio + `docker-compose.yml`
  en la raiz, con MySQL, red interna y un unico puerto expuesto (Gateway).
- ✅ Swagger/OpenAPI (`springdoc-openapi`) agregado a los 10 microservicios;
  el Gateway agrega y centraliza la documentacion de todos ellos.
- ✅ Documentacion Swagger detallada (`@Tag`, `@Operation`, `@ApiResponses`
  y autor en la cabecera) en los 6 microservicios asignados al equipo.
- ✅ Pruebas unitarias de capa Service y Controller para esos mismos 6
  microservicios (12 clases de test en total).
- ✅ Se elimino `schema_completo.sql` (script manual antiguo, en la raiz):
  usaba nombres de base de datos inconsistentes con los reales (`db_x` /
  `x_db` vs `bd_x`) y quedaba duplicado con Flyway + `init-db/`.

### Pendiente para el equipo antes de entregar
- Reemplazar 
  los 6 archivos `OpenApiConfig` / `@Tag` correspondientes.
- Subir el proyecto a un repositorio publico de GitHub y dar acceso al
  docente.
