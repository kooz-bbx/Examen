package cl.duoc.pagos_service.controller;

import cl.duoc.pagos_service.dto.PagoConPedidoDTO;
import cl.duoc.pagos_service.dto.PagoDTO;
import cl.duoc.pagos_service.service.PagoService;
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

@Tag(name = "Pagos", description = "Gestion de pagos asociados a pedidos del restaurant")
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Listar pagos", description = "Obtiene el listado completo de pagos registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PagoDTO.class)),
                            examples = @ExampleObject(name = "Listado de pagos", value = """
                                    [
                                      {"id": 1, "monto": 15990.0, "metodoPago": "TARJETA_CREDITO", "estado": "APROBADO", "pedidoId": 5}
                                    ]""")))
    })
    @GetMapping
    public ResponseEntity<List<PagoDTO>> listar() {
        return ResponseEntity.ok(pagoService.listar());
    }

    @Operation(summary = "Buscar pago por id",
            description = "Devuelve el pago solicitado junto con los datos completos del pedido asociado, " +
                    "obtenidos desde pedidos-service.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PagoConPedidoDTO.class),
                            examples = @ExampleObject(name = "Pago encontrado", value = """
                                    {
                                      "id": 1,
                                      "monto": 15990.0,
                                      "metodoPago": "TARJETA_CREDITO",
                                      "estado": "APROBADO",
                                      "pedido": {"id": 5, "clienteId": 10, "detalle": "2x Pizza Margarita, 1x Bebida", "total": 15990.0, "estado": "CONFIRMADO"}
                                    }"""))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Pago no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Pago no encontrado: 99"}""")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoConPedidoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.buscarConPedido(id));
    }

    @Operation(summary = "Registrar pago", description = "Crea un nuevo pago. Valida que el pedido asociado exista.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago creado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PagoDTO.class),
                            examples = @ExampleObject(name = "Pago creado", value = """
                                    {"id": 1, "monto": 15990.0, "metodoPago": "TARJETA_CREDITO", "estado": "APROBADO", "pedidoId": 5}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El monto no puede ser nulo."}"""))),
            @ApiResponse(responseCode = "404", description = "El pedido asociado no existe",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Pedido no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Pedido no encontrado: 5"}""")))
    })
    @PostMapping
    public ResponseEntity<PagoDTO> guardar(@Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.ok(pagoService.guardar(dto));
    }

    @Operation(summary = "Actualizar pago", description = "Actualiza los datos de un pago existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PagoDTO.class),
                            examples = @ExampleObject(name = "Pago actualizado", value = """
                                    {"id": 1, "monto": 15990.0, "metodoPago": "TARJETA_CREDITO", "estado": "RECHAZADO", "pedidoId": 5}"""))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Error de validacion", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 400, "error": "Error de validacion", "mensaje": "El estado no puede estar vacio."}"""))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Pago no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Pago no encontrado: 99"}""")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar pago", description = "Elimina un pago existente por su id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "Pago no encontrado", value = """
                                    {"timestamp": "2026-07-02T10:15:30", "status": 404, "error": "Registro no encontrado", "mensaje": "Pago no encontrado: 99"}""")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
