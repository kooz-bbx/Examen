package cl.duoc.empleados_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa un empleado que trabaja en un local del restaurant.")
public class EmpleadoDTO {

    @Schema(description = "Identificador unico del empleado. No debe enviarse al crear.", example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 150)
    @Schema(description = "Nombre completo del empleado.", example = "Juan Soto")
    private String nombre;

    @NotBlank(message = "El cargo no puede estar vacío.")
    @Size(max = 100)
    @Schema(description = "Cargo o puesto del empleado.", example = "Cocinero")
    private String cargo;

    @NotNull(message = "El salario no puede ser nulo.")
    @Positive
    @Schema(description = "Salario mensual del empleado.", example = "650000.0")
    private Double salario;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email
    @Size(max = 150)
    @Schema(description = "Correo electronico del empleado.", example = "juan.soto@restaurantenzo.cl")
    private String correo;

    @NotBlank(message = "El turno no puede estar vacío.")
    @Size(max = 50)
    @Schema(description = "Turno asignado al empleado.", example = "TARDE",
            allowableValues = {"MANANA", "TARDE", "NOCHE"})
    private String turno;

    @NotNull(message = "La fecha de ingreso no puede ser nula.")
    @Schema(description = "Fecha en que el empleado ingreso a trabajar (formato yyyy-MM-dd).", example = "2025-03-15")
    private LocalDate fechaIngreso;

    @NotNull
    @Schema(description = "Identificador del local donde trabaja el empleado.", example = "1")
    private Long localId;
}
