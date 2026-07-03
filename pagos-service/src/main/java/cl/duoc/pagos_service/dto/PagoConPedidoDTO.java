package cl.duoc.pagos_service.dto;

import cl.duoc.pagos_service.client.PedidoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Pago con los datos completos del pedido asociado embebidos (respuesta de GET /{id}).")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoConPedidoDTO {

    @Schema(description = "Identificador del pago.", example = "1")
    private Long id;

    @Schema(description = "Monto pagado.", example = "15990.0")
    private BigDecimal monto;

    @Schema(description = "Metodo utilizado para el pago.", example = "TARJETA_CREDITO")
    private String metodoPago;

    @Schema(description = "Estado actual del pago.", example = "APROBADO")
    private String estado;

    @Schema(description = "Datos completos del pedido asociado, obtenidos desde pedidos-service.")
    private PedidoDTO pedido;
}
