package cl.duoc.envios_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Representa un envio asociado a un pedido del restaurant.")
public class EnvioDTO {

    @Schema(description = "Identificador unico del envio. Se genera automaticamente y no debe enviarse al crear.",
            example = "1")
    private Long id;

    @NotNull(message = "El ID del pedido no puede ser nulo.")
    @Schema(description = "Identificador del pedido asociado a este envio.", example = "5")
    private Long pedidoId;

    @NotBlank(message = "La direccion no puede estar vacia.")
    @Size(max = 255)
    @Schema(description = "Direccion de destino del envio.", example = "Av. Libertador Bernardo O'Higgins 1234, Santiago")
    private String direccion;

    @NotBlank(message = "El estado no puede estar vacio.")
    @Size(max = 50)
    @Schema(description = "Estado actual del envio.", example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "EN_TRANSITO", "ENTREGADO", "CANCELADO"})
    private String estado;

    @NotBlank(message = "La fecha de envio no puede estar vacia.")
    @Size(max = 50)
    @Schema(description = "Fecha en que se despacha el envio (formato yyyy-MM-dd).", example = "2026-07-02")
    private String fechaEnvio;
}
