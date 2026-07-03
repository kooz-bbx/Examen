package cl.duoc.envios_service.dto;

import cl.duoc.envios_service.client.PedidoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Envio con los datos completos del pedido asociado embebidos (respuesta de GET /{id}).")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioConPedidoDTO {

    @Schema(description = "Identificador del envio.", example = "1")
    private Long id;

    @Schema(description = "Direccion de destino del envio.", example = "Av. Libertador Bernardo O'Higgins 1234, Santiago")
    private String direccion;

    @Schema(description = "Estado actual del envio.", example = "PENDIENTE")
    private String estado;

    @Schema(description = "Fecha en que se despacha el envio.", example = "2026-07-02")
    private String fechaEnvio;

    @Schema(description = "Datos completos del pedido asociado, obtenidos desde pedidos-service.")
    private PedidoDTO pedido;
}
