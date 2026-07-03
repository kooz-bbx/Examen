package cl.duoc.reservas_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa una reserva de mesa realizada por un cliente en un local.")
public class ReservaDTO {

    @Schema(description = "Identificador unico de la reserva. No debe enviarse al crear.", example = "1")
    private Long id;

    @NotBlank(message = "La fecha no puede estar vacía.")
    @Size(max = 20)
    @Schema(description = "Fecha de la reserva (formato yyyy-MM-dd).", example = "2026-07-10")
    private String fecha;

    @NotBlank(message = "La hora no puede estar vacía.")
    @Size(max = 10)
    @Schema(description = "Hora de la reserva (formato HH:mm).", example = "20:30")
    private String hora;

    @NotNull(message = "La cantidad de personas no puede ser nula.")
    @Min(1) @Max(50)
    @Schema(description = "Cantidad de personas incluidas en la reserva.", example = "4")
    private Integer cantidadPersonas;

    @NotNull(message = "El ID del cliente no puede ser nulo.")
    @Schema(description = "Identificador del cliente que realiza la reserva.", example = "10")
    private Long clienteId;

    @NotNull(message = "El ID del local no puede ser nulo.")
    @Schema(description = "Identificador del local donde se realiza la reserva.", example = "1")
    private Long localId;
}
