package cl.duoc.locales_service.controller;

import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.service.LocalService;
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
 * Autor: Integrante 2 (reemplazar por el nombre real antes de la entrega).
 */
@Tag(name = "Locales", description = "Autor: Integrante 2 - Administracion de las sucursales del restaurant")
@RestController
@RequestMapping("/api/v1/locales")
public class LocalController {

    @Autowired
    private LocalService localService;

    @Operation(summary = "Listar locales", description = "Retorna todos los locales registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = LocalDTO.class)),
                            examples = @ExampleObject(name = "Listado de locales", value = """
                                    [
                                      {"id": 1, "nombre": "RestaurantEnzo Providencia", "direccion": "Av. Providencia 2222, Providencia", "comuna": "Providencia", "horario": "Lun-Dom 12:00-23:00", "activo": true}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<LocalDTO>> listar() {
        return ResponseEntity.ok(localService.listar());
    }

    @Operation(summary = "Buscar local por id", description = "Retorna un local segun su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Local encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LocalDTO.class),
                            examples = @ExampleObject(name = "Local encontrado", value = """
                                    {"id": 1, "nombre": "RestaurantEnzo Providencia", "direccion": "Av. Providencia 2222, Providencia", "comuna": "Providencia", "horario": "Lun-Dom 12:00-23:00", "activo": true}"""))),
            @ApiResponse(responseCode = "404", description = "Local no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Local no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Local no encontrado: 99"}""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LocalDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(localService.buscarPorId(id));
    }

    @Operation(summary = "Crear local", description = "Registra una nueva sucursal.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Local creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LocalDTO.class),
                            examples = @ExampleObject(name = "Local creado", value = """
                                    {"id": 1, "nombre": "RestaurantEnzo Providencia", "direccion": "Av. Providencia 2222, Providencia", "comuna": "Providencia", "horario": "Lun-Dom 12:00-23:00", "activo": true}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "La dirección no puede estar vacía."}""")))
    })
    @PostMapping
    public ResponseEntity<LocalDTO> guardar(@Valid @RequestBody LocalDTO dto) {
        return ResponseEntity.ok(localService.guardar(dto));
    }

    @Operation(summary = "Actualizar local", description = "Actualiza los datos de un local existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Local actualizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LocalDTO.class),
                            examples = @ExampleObject(name = "Local actualizado", value = """
                                    {"id": 1, "nombre": "RestaurantEnzo Providencia", "direccion": "Av. Providencia 2500, Providencia", "comuna": "Providencia", "horario": "Lun-Dom 12:00-00:00", "activo": true}"""))),
            @ApiResponse(responseCode = "404", description = "Local no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Local no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Local no encontrado: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<LocalDTO> actualizar(@PathVariable Long id, @Valid @RequestBody LocalDTO dto) {
        return ResponseEntity.ok(localService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar local", description = "Elimina un local existente por su id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Local eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Local no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Local no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Local no encontrado: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        localService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar locales activos", description = "Retorna solo los locales marcados como activos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = LocalDTO.class)),
                            examples = @ExampleObject(name = "Locales activos", value = """
                                    [
                                      {"id": 1, "nombre": "RestaurantEnzo Providencia", "direccion": "Av. Providencia 2222, Providencia", "comuna": "Providencia", "horario": "Lun-Dom 12:00-23:00", "activo": true}
                                    ]""")))
    })
    @GetMapping("/activos")
    public ResponseEntity<List<LocalDTO>> activos() {
        return ResponseEntity.ok(localService.buscarActivos());
    }
}
