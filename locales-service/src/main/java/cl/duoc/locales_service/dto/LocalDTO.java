package cl.duoc.locales_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa una sucursal (local) del restaurant.")
public class LocalDTO {

    @Schema(description = "Identificador unico del local. No debe enviarse al crear.", example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 150)
    @Schema(description = "Nombre del local.", example = "RestaurantEnzo Providencia")
    private String nombre;

    @NotBlank(message = "La dirección no puede estar vacía.")
    @Size(max = 255)
    @Schema(description = "Direccion fisica del local.", example = "Av. Providencia 2222, Providencia")
    private String direccion;

    @NotBlank(message = "La comuna no puede estar vacía.")
    @Size(max = 100)
    @Schema(description = "Comuna donde se ubica el local.", example = "Providencia")
    private String comuna;

    @NotBlank(message = "El horario no puede estar vacío.")
    @Size(max = 100)
    @Schema(description = "Horario de atencion del local.", example = "Lun-Dom 12:00-23:00")
    private String horario;

    @NotNull
    @Schema(description = "Indica si el local se encuentra activo.", example = "true")
    private Boolean activo;
}
