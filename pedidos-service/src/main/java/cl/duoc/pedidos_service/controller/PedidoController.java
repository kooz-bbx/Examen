package cl.duoc.pedidos_service.controller;

import cl.duoc.pedidos_service.dto.PedidoConClienteDTO;
import cl.duoc.pedidos_service.dto.PedidoDto;
import cl.duoc.pedidos_service.service.PedidoService;
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
@Tag(name = "Pedidos", description = "Autor: Integrante 3 - Gestion de pedidos del restaurant")
@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Listar pedidos", description = "Retorna todos los pedidos registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PedidoDto.class)),
                            examples = @ExampleObject(name = "Listado de pedidos", value = """
                                    [
                                      {"id": 5, "clienteId": 10, "detalle": "2x Pizza Margarita, 1x Bebida", "total": 15990.0, "estado": "CONFIRMADO"}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<PedidoDto>> listar() {
        return ResponseEntity.ok(pedidoService.listar());
    }

    @Operation(summary = "Buscar pedido por id (con datos del cliente)",
            description = "Devuelve el pedido junto con los datos del cliente, obtenidos en vivo desde clientes-service via Feign.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PedidoConClienteDTO.class),
                            examples = @ExampleObject(name = "Pedido encontrado", value = """
                                    {
                                      "id": 5,
                                      "detalle": "2x Pizza Margarita, 1x Bebida",
                                      "total": 15990.0,
                                      "estado": "CONFIRMADO",
                                      "cliente": {"id": 10, "nombre": "Maria Perez", "email": "maria.perez@email.com", "telefono": "+56912345678", "ciudad": "Santiago"}
                                    }"""))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Pedido no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Pedido no encontrado: 99"}""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoConClienteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarConCliente(id));
    }

    @Operation(summary = "Crear pedido", description = "Valida que el cliente exista (via clientes-service) y crea el pedido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PedidoDto.class),
                            examples = @ExampleObject(name = "Pedido creado", value = """
                                    {"id": 5, "clienteId": 10, "detalle": "2x Pizza Margarita, 1x Bebida", "total": 15990.0, "estado": "PENDIENTE"}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El total debe ser mayor a 0."}"""))),
            @ApiResponse(responseCode = "404", description = "El cliente indicado no existe",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Cliente no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Cliente no encontrado: 10"}""")))
    })
    @PostMapping
    public ResponseEntity<PedidoDto> guardar(@Valid @RequestBody PedidoDto dto) {
        return ResponseEntity.ok(pedidoService.guardar(dto));
    }

    @Operation(summary = "Actualizar pedido", description = "Actualiza los datos de un pedido existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PedidoDto.class),
                            examples = @ExampleObject(name = "Pedido actualizado", value = """
                                    {"id": 5, "clienteId": 10, "detalle": "2x Pizza Margarita, 1x Bebida", "total": 15990.0, "estado": "EN_PREPARACION"}"""))),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Pedido no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Pedido no encontrado: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> actualizar(@PathVariable Long id, @Valid @RequestBody PedidoDto dto) {
        return ResponseEntity.ok(pedidoService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido existente por su id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Pedido no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Pedido no encontrado: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar pedidos de un cliente", description = "Retorna todos los pedidos asociados a un cliente especifico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PedidoDto.class)),
                            examples = @ExampleObject(name = "Pedidos del cliente", value = """
                                    [
                                      {"id": 5, "clienteId": 10, "detalle": "2x Pizza Margarita, 1x Bebida", "total": 15990.0, "estado": "CONFIRMADO"}
                                    ]""")))
    })
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDto>> porCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.buscarPorCliente(clienteId));
    }
}
