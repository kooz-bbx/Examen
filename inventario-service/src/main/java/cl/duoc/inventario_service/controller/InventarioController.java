package cl.duoc.inventario_service.controller;

import cl.duoc.inventario_service.dto.InventarioConProductoDTO;
import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.service.InventarioService;
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

@Tag(name = "Inventario", description = "Gestion del stock de productos por local")
@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Operation(summary = "Listar inventario", description = "Obtiene el listado completo de registros de inventario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = InventarioDTO.class)),
                            examples = @ExampleObject(name = "Listado de inventario", value = """
                                    [
                                      {"id": 1, "productoId": 3, "localId": 1, "stock": 50}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<InventarioDTO>> listar() {
        return ResponseEntity.ok(inventarioService.listar());
    }

    @Operation(summary = "Buscar registro de inventario por id",
            description = "Devuelve el registro de inventario junto con los datos completos del producto asociado, " +
                    "obtenidos desde productos-service.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InventarioConProductoDTO.class),
                            examples = @ExampleObject(name = "Inventario encontrado", value = """
                                    {
                                      "id": 1,
                                      "localId": 1,
                                      "stock": 50,
                                      "producto": {"id": 3, "nombre": "Pizza Margarita", "precio": 8990.0, "categoria": "Pizzas", "disponible": true}
                                    }"""))),
            @ApiResponse(responseCode = "404", description = "Registro de inventario no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Inventario no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Inventario no encontrado: 99"}""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventarioConProductoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.buscarConProducto(id));
    }

    @Operation(summary = "Registrar stock", description = "Crea un nuevo registro de inventario. Valida que el producto asociado exista.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InventarioDTO.class),
                            examples = @ExampleObject(name = "Inventario creado", value = """
                                    {"id": 1, "productoId": 3, "localId": 1, "stock": 50}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El stock no puede ser nulo."}"""))),
            @ApiResponse(responseCode = "404", description = "El producto asociado no existe",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Producto no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Producto no encontrado: 3"}""")))
    })
    @PostMapping
    public ResponseEntity<InventarioDTO> guardar(@Valid @RequestBody InventarioDTO dto) {
        return ResponseEntity.ok(inventarioService.guardar(dto));
    }

    @Operation(summary = "Actualizar inventario", description = "Actualiza un registro de inventario existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InventarioDTO.class),
                            examples = @ExampleObject(name = "Inventario actualizado", value = """
                                    {"id": 1, "productoId": 3, "localId": 1, "stock": 35}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El stock no puede ser negativo."}"""))),
            @ApiResponse(responseCode = "404", description = "Registro de inventario no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Inventario no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Inventario no encontrado: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> actualizar(@PathVariable Long id, @Valid @RequestBody InventarioDTO dto) {
        return ResponseEntity.ok(inventarioService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar registro de inventario", description = "Elimina un registro de inventario existente por su id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Registro de inventario no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Inventario no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Inventario no encontrado: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar inventario por local", description = "Obtiene todos los registros de inventario de un local especifico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = InventarioDTO.class)),
                            examples = @ExampleObject(name = "Inventario por local", value = """
                                    [
                                      {"id": 1, "productoId": 3, "localId": 1, "stock": 50}
                                    ]""")))
    })
    @GetMapping("/local/{localId}")
    public ResponseEntity<List<InventarioDTO>> porLocal(@PathVariable Long localId) {
        return ResponseEntity.ok(inventarioService.buscarPorLocal(localId));
    }
}
