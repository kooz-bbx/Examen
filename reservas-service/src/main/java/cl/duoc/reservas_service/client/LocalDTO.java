package cl.duoc.reservas_service.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Datos del local obtenidos desde locales-service (via Feign).")
@Data
public class LocalDTO {

    @Schema(description = "Identificador del local.", example = "1")
    private Long id;

    @Schema(description = "Nombre del local.", example = "RestaurantEnzo Providencia")
    private String nombre;

    @Schema(description = "Direccion del local.", example = "Av. Providencia 2222, Providencia")
    private String direccion;

    @Schema(description = "Comuna donde se ubica el local.", example = "Providencia")
    private String comuna;

    @Schema(description = "Horario de atencion del local.", example = "Lun-Dom 12:00-23:00")
    private String horario;

    @Schema(description = "Indica si el local se encuentra activo.", example = "true")
    private Boolean activo;
}
