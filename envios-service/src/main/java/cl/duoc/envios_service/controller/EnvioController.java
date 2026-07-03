package cl.duoc.envios_service.controller;

import cl.duoc.envios_service.dto.EnvioConPedidoDTO;
import cl.duoc.envios_service.dto.EnvioDTO;
import cl.duoc.envios_service.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Envios", description = "Gestion de envios asociados a pedidos del restaurant")
@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Operation(summary = "Listar envios", description = "Obtiene el listado completo de envios registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = EnvioDTO.class)),
                            examples = @ExampleObject(name = "Listado de envios", value = """
                                    [
                                      {"id": 1, "pedidoId": 5, "direccion": "Av. Libertador Bernardo O'Higgins 1234, Santiago", "estado": "PENDIENTE", "fechaEnvio": "2026-07-02"}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<EnvioDTO>> listar() {
        return ResponseEntity.ok(envioService.listar());
    }

    @Operation(summary = "Buscar envio por id",
            description = "Devuelve el envio solicitado junto con los datos completos del pedido asociado, " +
                    "obtenidos desde pedidos-service.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Envio encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EnvioConPedidoDTO.class),
                            examples = @ExampleObject(name = "Envio encontrado", value = """
                                    {
                                      "id": 1,
                                      "direccion": "Av. Libertador Bernardo O'Higgins 1234, Santiago",
                                      "estado": "PENDIENTE",
                                      "fechaEnvio": "2026-07-02",
                                      "pedido": {"id": 5, "clienteId": 10, "detalle": "2x Pizza Margarita, 1x Bebida", "total": 15990.0, "estado": "CONFIRMADO"}
                                    }"""))),
            @ApiResponse(responseCode = "404", description = "Envio no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Envio no encontrado", value = """
                                    {
                                      "timestamp": "2026-07-02T10:15:30",
                                      "status": 404,
                                      "error": "Registro no encontrado",
                                      "mensaje": "Envio no encontrado: 99"
                                    }""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnvioConPedidoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioService.buscarConPedido(id));
    }

    @Operation(summary = "Crear envio", description = "Registra un nuevo envio. Valida que el pedido asociado exista.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Envio creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EnvioDTO.class),
                            examples = @ExampleObject(name = "Envio creado", value = """
                                    {"id": 1, "pedidoId": 5, "direccion": "Av. Libertador Bernardo O'Higgins 1234, Santiago", "estado": "PENDIENTE", "fechaEnvio": "2026-07-02"}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "La direccion no puede estar vacia."}"""))),
            @ApiResponse(responseCode = "404", description = "El pedido asociado no existe",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Pedido no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Pedido no encontrado: 5"}""")))
    })
    @PostMapping
    public ResponseEntity<EnvioDTO> guardar(@Valid @RequestBody EnvioDTO dto) {
        return ResponseEntity.ok(envioService.guardar(dto));
    }

    @Operation(summary = "Actualizar envio", description = "Actualiza los datos de un envio existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Envio actualizado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = EnvioDTO.class),
                            examples = @ExampleObject(name = "Envio actualizado", value = """
                                    {"id": 1, "pedidoId": 5, "direccion": "Av. Libertador Bernardo O'Higgins 1234, Santiago", "estado": "EN_TRANSITO", "fechaEnvio": "2026-07-02"}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El estado no puede estar vacio."}"""))),
            @ApiResponse(responseCode = "404", description = "Envio no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Envio no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Envio no encontrado: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EnvioDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EnvioDTO dto) {
        return ResponseEntity.ok(envioService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar envio", description = "Elimina un envio existente por su id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Envio eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Envio no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Envio no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Envio no encontrado: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        envioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
