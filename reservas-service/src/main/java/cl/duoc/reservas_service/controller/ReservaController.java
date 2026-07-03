package cl.duoc.reservas_service.controller;

import cl.duoc.reservas_service.dto.ReservaDTO;
import cl.duoc.reservas_service.dto.ReservaDetalladaDTO;
import cl.duoc.reservas_service.service.ReservaService;
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

@Tag(name = "Reservas", description = "Gestion de reservas de mesa de clientes en los locales del restaurant")
@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Listar reservas", description = "Obtiene el listado completo de reservas registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ReservaDTO.class)),
                            examples = @ExampleObject(name = "Listado de reservas", value = """
                                    [
                                      {"id": 1, "fecha": "2026-07-10", "hora": "20:30", "cantidadPersonas": 4, "clienteId": 10, "localId": 1}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    @Operation(summary = "Buscar reserva por id",
            description = "Devuelve la reserva solicitada junto con los datos completos del cliente y del local, " +
                    "obtenidos desde clientes-service y locales-service.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaDetalladaDTO.class),
                            examples = @ExampleObject(name = "Reserva encontrada", value = """
                                    {
                                      "id": 1,
                                      "fecha": "2026-07-10",
                                      "hora": "20:30",
                                      "cantidadPersonas": 4,
                                      "cliente": {"id": 10, "nombre": "Maria Perez", "email": "maria.perez@email.com", "telefono": "+56912345678", "ciudad": "Santiago"},
                                      "local": {"id": 1, "nombre": "RestaurantEnzo Providencia", "direccion": "Av. Providencia 2222, Providencia", "comuna": "Providencia", "horario": "Lun-Dom 12:00-23:00", "activo": true}
                                    }"""))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Reserva no encontrada", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Reserva no encontrada: 99"}""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDetalladaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarDetallada(id));
    }

    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva. Valida que el cliente y el local asociados existan.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva creada correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaDTO.class),
                            examples = @ExampleObject(name = "Reserva creada", value = """
                                    {"id": 1, "fecha": "2026-07-10", "hora": "20:30", "cantidadPersonas": 4, "clienteId": 10, "localId": 1}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "La fecha no puede estar vacía."}"""))),
            @ApiResponse(responseCode = "404", description = "El cliente o el local asociado no existen",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Cliente no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Cliente no encontrado: 10"}""")))
    })
    @PostMapping
    public ResponseEntity<ReservaDTO> guardar(@Valid @RequestBody ReservaDTO dto) {
        return ResponseEntity.ok(reservaService.guardar(dto));
    }

    @Operation(summary = "Actualizar reserva", description = "Actualiza los datos de una reserva existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReservaDTO.class),
                            examples = @ExampleObject(name = "Reserva actualizada", value = """
                                    {"id": 1, "fecha": "2026-07-10", "hora": "21:00", "cantidadPersonas": 6, "clienteId": 10, "localId": 1}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "La cantidad de personas no puede ser nula."}"""))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Reserva no encontrada", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Reserva no encontrada: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ReservaDTO dto) {
        return ResponseEntity.ok(reservaService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva existente por su id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Reserva no encontrada", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Reserva no encontrada: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
