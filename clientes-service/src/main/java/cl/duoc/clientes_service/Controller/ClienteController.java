package cl.duoc.clientes_service.Controller;

import cl.duoc.clientes_service.Dto.ClienteDTO;
import cl.duoc.clientes_service.Service.ClienteService;
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
 * Autor: Integrante 1 (reemplazar por el nombre real antes de la entrega).
 */
@Tag(name = "Clientes", description = "Autor: Integrante 1 - Administracion de clientes del restaurant")
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Operation(summary = "Listar clientes", description = "Retorna todos los clientes registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class)),
                            examples = @ExampleObject(name = "Listado de clientes", value = """
                                    [
                                      {"id": 10, "nombre": "Maria Perez", "email": "maria.perez@email.com", "telefono": "+56912345678", "ciudad": "Santiago"}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar cliente por id", description = "Retorna un cliente segun su identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteDTO.class),
                            examples = @ExampleObject(name = "Cliente encontrado", value = """
                                    {"id": 10, "nombre": "Maria Perez", "email": "maria.perez@email.com", "telefono": "+56912345678", "ciudad": "Santiago"}"""))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Cliente no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Cliente no encontrado: 99"}""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteDTO.class),
                            examples = @ExampleObject(name = "Cliente creado", value = """
                                    {"id": 10, "nombre": "Maria Perez", "email": "maria.perez@email.com", "telefono": "+56912345678", "ciudad": "Santiago"}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El email debe tener un formato válido."}""")))
    })
    @PostMapping
    public ResponseEntity<ClienteDTO> guardar(@Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteDTO.class),
                            examples = @ExampleObject(name = "Cliente actualizado", value = """
                                    {"id": 10, "nombre": "Maria Perez", "email": "maria.perez@email.com", "telefono": "+56999998888", "ciudad": "Providencia"}"""))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Cliente no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Cliente no encontrado: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente por su id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Cliente no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Cliente no encontrado: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar cliente por email", description = "Retorna un cliente segun su correo electronico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClienteDTO.class),
                            examples = @ExampleObject(name = "Cliente encontrado", value = """
                                    {"id": 10, "nombre": "Maria Perez", "email": "maria.perez@email.com", "telefono": "+56912345678", "ciudad": "Santiago"}"""))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Cliente no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Cliente no encontrado con email: noexiste@email.com"}""")))
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteDTO> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @Operation(summary = "Buscar clientes por ciudad", description = "Retorna los clientes que viven en la ciudad indicada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class)),
                            examples = @ExampleObject(name = "Clientes por ciudad", value = """
                                    [
                                      {"id": 10, "nombre": "Maria Perez", "email": "maria.perez@email.com", "telefono": "+56912345678", "ciudad": "Santiago"}
                                    ]""")))
    })
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<ClienteDTO>> buscarPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(service.buscarPorCiudad(ciudad));
    }

    @Operation(summary = "Buscar clientes por nombre (coincidencia parcial)", description = "Retorna los clientes cuyo nombre contiene el texto indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class)),
                            examples = @ExampleObject(name = "Clientes encontrados", value = """
                                    [
                                      {"id": 10, "nombre": "Maria Perez", "email": "maria.perez@email.com", "telefono": "+56912345678", "ciudad": "Santiago"}
                                    ]""")))
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }
}
