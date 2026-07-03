package cl.duoc.clientes_service.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa un cliente del restaurant.")
public class ClienteDTO {

    @Schema(description = "Identificador unico del cliente. No debe enviarse al crear.", example = "10")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres.")
    @Schema(description = "Nombre completo del cliente.", example = "Maria Perez")
    private String nombre;

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El email debe tener un formato válido.")
    @Size(max = 150, message = "El email no puede superar 150 caracteres.")
    @Schema(description = "Correo electronico del cliente.", example = "maria.perez@email.com")
    private String email;

    @Size(max = 20, message = "El teléfono no puede superar 20 caracteres.")
    @Schema(description = "Telefono de contacto del cliente.", example = "+56912345678")
    private String telefono;

    @Size(max = 100, message = "La ciudad no puede superar 100 caracteres.")
    @Schema(description = "Ciudad de residencia del cliente.", example = "Santiago")
    private String ciudad;
}
