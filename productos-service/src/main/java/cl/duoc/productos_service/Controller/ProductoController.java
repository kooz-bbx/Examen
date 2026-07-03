package cl.duoc.productos_service.Controller;

import cl.duoc.productos_service.Dto.ProductoDto;
import cl.duoc.productos_service.Service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Autor: Integrante 3 (reemplazar por el nombre real antes de la entrega).
 */
@Tag(name = "Productos", description = "Autor: Integrante 3 - Catalogo de productos del restaurant")
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar productos", description = "Retorna todos los productos del catalogo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductoDto.class)),
                            examples = @ExampleObject(name = "Listado de productos", value = """
                                    [
                                      {"id": 1, "nombre": "Pizza Margarita", "precio": 8990.0, "stock": 20, "categoria": "Pizzas", "disponible": true}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<ProductoDto>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @Operation(summary = "Crear producto", description = "Registra un nuevo producto en el catalogo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductoDto.class),
                            examples = @ExampleObject(name = "Producto creado", value = """
                                    {"id": 1, "nombre": "Pizza Margarita", "precio": 8990.0, "stock": 20, "categoria": "Pizzas", "disponible": true}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El precio debe ser mayor a 0."}""")))
    })
    @PostMapping
    public ResponseEntity<ProductoDto> guardar(@Valid @RequestBody ProductoDto dto) {
        return ResponseEntity.ok(productoService.guardar(dto));
    }

    @Operation(summary = "Buscar producto por id", description = "Retorna un producto segun su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductoDto.class),
                            examples = @ExampleObject(name = "Producto encontrado", value = """
                                    {"id": 1, "nombre": "Pizza Margarita", "precio": 8990.0, "stock": 20, "categoria": "Pizzas", "disponible": true}"""))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Producto no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Producto no encontrado: 99"}""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductoDto.class),
                            examples = @ExampleObject(name = "Producto actualizado", value = """
                                    {"id": 1, "nombre": "Pizza Margarita", "precio": 9990.0, "stock": 15, "categoria": "Pizzas", "disponible": true}"""))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Producto no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Producto no encontrado: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDto dto) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto existente por su id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Producto no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Producto no encontrado: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar productos por categoria", description = "Retorna los productos que pertenecen a una categoria dada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductoDto.class)),
                            examples = @ExampleObject(name = "Productos por categoria", value = """
                                    [
                                      {"id": 1, "nombre": "Pizza Margarita", "precio": 8990.0, "stock": 20, "categoria": "Pizzas", "disponible": true}
                                    ]""")))
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoDto>> porCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(categoria));
    }

    @Operation(summary = "Listar productos disponibles", description = "Retorna solo los productos marcados como disponibles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductoDto.class)),
                            examples = @ExampleObject(name = "Productos disponibles", value = """
                                    [
                                      {"id": 1, "nombre": "Pizza Margarita", "precio": 8990.0, "stock": 20, "categoria": "Pizzas", "disponible": true}
                                    ]""")))
    })
    @GetMapping("/disponibles")
    public ResponseEntity<List<ProductoDto>> disponibles() {
        return ResponseEntity.ok(productoService.buscarDisponibles());
    }

    @Operation(summary = "Listar productos por rango de precio", description = "Retorna los productos cuyo precio esta entre min y max.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductoDto.class)),
                            examples = @ExampleObject(name = "Productos por rango de precio", value = """
                                    [
                                      {"id": 1, "nombre": "Pizza Margarita", "precio": 8990.0, "stock": 20, "categoria": "Pizzas", "disponible": true}
                                    ]""")))
    })
    @GetMapping("/precio")
    public ResponseEntity<List<ProductoDto>> porRangoPrecio(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(productoService.buscarPorRangoPrecio(min, max));
    }

    @Operation(summary = "Buscar productos por nombre (coincidencia parcial)", description = "Retorna los productos cuyo nombre contiene el texto indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductoDto.class)),
                            examples = @ExampleObject(name = "Productos encontrados", value = """
                                    [
                                      {"id": 1, "nombre": "Pizza Margarita", "precio": 8990.0, "stock": 20, "categoria": "Pizzas", "disponible": true}
                                    ]""")))
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }
}
