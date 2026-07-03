package cl.duoc.reservas_service.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Datos del cliente obtenidos desde clientes-service (via Feign).")
@Data
public class ClienteDTO {

    @Schema(description = "Identificador del cliente.", example = "10")
    private Long id;

    @Schema(description = "Nombre completo del cliente.", example = "Maria Perez")
    private String nombre;

    @Schema(description = "Correo electronico del cliente.", example = "maria.perez@email.com")
    private String email;

    @Schema(description = "Telefono de contacto del cliente.", example = "+56912345678")
    private String telefono;

    @Schema(description = "Ciudad del cliente.", example = "Santiago")
    private String ciudad;
}
