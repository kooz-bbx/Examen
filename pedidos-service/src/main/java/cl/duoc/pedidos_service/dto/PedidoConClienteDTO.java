package cl.duoc.pedidos_service.dto;

import cl.duoc.pedidos_service.client.ClienteDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Pedido con los datos completos del cliente asociado embebidos (respuesta de GET /{id}).")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoConClienteDTO {

    @Schema(description = "Identificador del pedido.", example = "5")
    private Long id;

    @Schema(description = "Detalle o glosa del pedido.", example = "2x Pizza Margarita, 1x Bebida")
    private String detalle;

    @Schema(description = "Monto total del pedido.", example = "15990.0")
    private BigDecimal total;

    @Schema(description = "Estado actual del pedido.", example = "CONFIRMADO")
    private String estado;

    @Schema(description = "Datos completos del cliente asociado, obtenidos desde clientes-service.")
    private ClienteDTO cliente;
}
