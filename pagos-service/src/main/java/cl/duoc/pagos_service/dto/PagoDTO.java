package cl.duoc.pagos_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Representa el pago asociado a un pedido del restaurant.")
public class PagoDTO {

    @Schema(description = "Identificador unico del pago. No debe enviarse al crear.", example = "1")
    private Long id;

    @NotNull(message = "El monto no puede ser nulo.")
    @Positive
    @Schema(description = "Monto pagado.", example = "15990.0")
    private BigDecimal monto;

    @NotBlank(message = "El metodo de pago no puede estar vacio.")
    @Size(max = 50)
    @Schema(description = "Metodo utilizado para el pago.", example = "TARJETA_CREDITO",
            allowableValues = {"EFECTIVO", "TARJETA_DEBITO", "TARJETA_CREDITO", "TRANSFERENCIA"})
    private String metodoPago;

    @NotBlank(message = "El estado no puede estar vacio.")
    @Size(max = 50)
    @Schema(description = "Estado actual del pago.", example = "APROBADO",
            allowableValues = {"PENDIENTE", "APROBADO", "RECHAZADO"})
    private String estado;

    @NotNull(message = "El ID del pedido no puede ser nulo.")
    @Schema(description = "Identificador del pedido asociado a este pago.", example = "5")
    private Long pedidoId;
}
