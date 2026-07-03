package cl.duoc.envios_service.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Datos del pedido obtenidos desde pedidos-service (via Feign).")
@Data
public class PedidoDTO {

    @Schema(description = "Identificador del pedido.", example = "5")
    private Long id;

    @Schema(description = "Identificador del cliente que realizo el pedido.", example = "10")
    private Long clienteId;

    @Schema(description = "Detalle o glosa del pedido.", example = "2x Pizza Margarita, 1x Bebida")
    private String detalle;

    @Schema(description = "Monto total del pedido.", example = "15990.0")
    private Double total;

    @Schema(description = "Estado actual del pedido.", example = "CONFIRMADO")
    private String estado;
}
