package cl.duoc.empleados_service.controller;

import cl.duoc.empleados_service.dto.EmpleadoDTO;
import cl.duoc.empleados_service.service.EmpleadoService;
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
@Tag(name = "Empleados", description = "Autor: Integrante 2 - Administracion del personal del restaurant")
@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Operation(summary = "Listar empleados", description = "Retorna todos los empleados registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = EmpleadoDTO.class)),
                            examples = @ExampleObject(name = "Listado de empleados", value = """
                                    [
                                      {"id": 1, "nombre": "Juan Soto", "cargo": "Cocinero", "salario": 650000.0, "correo": "juan.soto@restaurantenzo.cl", "turno": "TARDE", "fechaIngreso": "2025-03-15", "localId": 1}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listar() {
        return ResponseEntity.ok(empleadoService.listar());
    }

    @Operation(summary = "Buscar empleado por id", description = "Retorna un empleado segun su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EmpleadoDTO.class),
                            examples = @ExampleObject(name = "Empleado encontrado", value = """
                                    {"id": 1, "nombre": "Juan Soto", "cargo": "Cocinero", "salario": 650000.0, "correo": "juan.soto@restaurantenzo.cl", "turno": "TARDE", "fechaIngreso": "2025-03-15", "localId": 1}"""))),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Empleado no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Empleado no encontrado: 99"}""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.buscarPorId(id));
    }

    @Operation(summary = "Crear empleado", description = "Registra un nuevo empleado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EmpleadoDTO.class),
                            examples = @ExampleObject(name = "Empleado creado", value = """
                                    {"id": 1, "nombre": "Juan Soto", "cargo": "Cocinero", "salario": 650000.0, "correo": "juan.soto@restaurantenzo.cl", "turno": "TARDE", "fechaIngreso": "2025-03-15", "localId": 1}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El salario no puede ser nulo."}""")))
    })
    @PostMapping
    public ResponseEntity<EmpleadoDTO> guardar(@Valid @RequestBody EmpleadoDTO dto) {
        return ResponseEntity.ok(empleadoService.guardar(dto));
    }

    @Operation(summary = "Actualizar empleado", description = "Actualiza los datos de un empleado existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado actualizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EmpleadoDTO.class),
                            examples = @ExampleObject(name = "Empleado actualizado", value = """
                                    {"id": 1, "nombre": "Juan Soto", "cargo": "Jefe de Cocina", "salario": 780000.0, "correo": "juan.soto@restaurantenzo.cl", "turno": "TARDE", "fechaIngreso": "2025-03-15", "localId": 1}"""))),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Empleado no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Empleado no encontrado: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoDTO dto) {
        return ResponseEntity.ok(empleadoService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado existente por su id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empleado eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Empleado no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Empleado no encontrado: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar empleados de un local", description = "Retorna todos los empleados que trabajan en un local especifico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = EmpleadoDTO.class)),
                            examples = @ExampleObject(name = "Empleados por local", value = """
                                    [
                                      {"id": 1, "nombre": "Juan Soto", "cargo": "Cocinero", "salario": 650000.0, "correo": "juan.soto@restaurantenzo.cl", "turno": "TARDE", "fechaIngreso": "2025-03-15", "localId": 1}
                                    ]""")))
    })
    @GetMapping("/local/{localId}")
    public ResponseEntity<List<EmpleadoDTO>> porLocal(@PathVariable Long localId) {
        return ResponseEntity.ok(empleadoService.buscarPorLocal(localId));
    }
}
