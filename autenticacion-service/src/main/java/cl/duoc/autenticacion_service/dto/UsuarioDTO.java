package cl.duoc.autenticacion_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Representa un usuario con credenciales de acceso al sistema.")
public class UsuarioDTO {

    @Schema(description = "Identificador unico del usuario. No debe enviarse al crear.", example = "1")
    private Long id;

    @NotBlank(message = "El username no puede estar vacio.")
    @Size(max = 100)
    @Schema(description = "Nombre de usuario para iniciar sesion.", example = "jsoto")
    private String username;

    @NotBlank(message = "La contrasena no puede estar vacia.")
    @Size(min = 4, max = 255)
    @Schema(description = "Contrasena del usuario (se recomienda enviarla ya encriptada).", example = "P@ssw0rd123")
    private String password;

    @NotBlank(message = "El rol no puede estar vacio.")
    @Size(max = 50)
    @Schema(description = "Rol asignado al usuario dentro del sistema.", example = "ADMIN",
            allowableValues = {"ADMIN", "EMPLEADO", "CLIENTE"})
    private String rol;
}
