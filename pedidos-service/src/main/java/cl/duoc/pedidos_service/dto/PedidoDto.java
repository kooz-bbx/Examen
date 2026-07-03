package cl.duoc.pedidos_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa un pedido realizado por un cliente del restaurant.")
public class PedidoDto {

    @Schema(description = "Identificador unico del pedido. No debe enviarse al crear.", example = "5")
    private Long id;

    @NotNull(message = "El ID del cliente no puede ser nulo.")
    @Schema(description = "Identificador del cliente que realiza el pedido.", example = "10")
    private Long clienteId;

    @NotBlank(message = "El detalle no puede estar vacío.")
    @Size(max = 500, message = "El detalle no puede superar 500 caracteres.")
    @Schema(description = "Detalle o glosa del pedido.", example = "2x Pizza Margarita, 1x Bebida")
    private String detalle;

    @NotNull(message = "El total no puede ser nulo.")
    @Positive(message = "El total debe ser mayor a 0.")
    @Schema(description = "Monto total del pedido.", example = "15990.0")
    private BigDecimal total;

    @NotBlank(message = "El estado no puede estar vacío.")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres.")
    @Schema(description = "Estado actual del pedido.", example = "CONFIRMADO",
            allowableValues = {"PENDIENTE", "CONFIRMADO", "EN_PREPARACION", "ENTREGADO", "CANCELADO"})
    private String estado;
}
