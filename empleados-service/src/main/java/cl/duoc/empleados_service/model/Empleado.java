package cl.duoc.empleados_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres.")
    private String nombre;

    @NotBlank(message = "El cargo no puede estar vacío.")
    @Size(max = 100, message = "El cargo no puede superar 100 caracteres.")
    private String cargo;

    @NotNull(message = "El salario no puede ser nulo.")
    @Positive(message = "El salario debe ser mayor a 0.")
    private Double salario;

    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "El correo debe tener un formato válido.")
    @Size(max = 150)
    private String correo;

    @NotBlank(message = "El turno no puede estar vacío.")
    @Size(max = 50, message = "El turno no puede superar 50 caracteres.")
    private String turno;

    @NotNull(message = "La fecha de ingreso no puede ser nula.")
    private LocalDate fechaIngreso;

    @NotNull(message = "El ID del local no puede ser nulo.")
    private Long localId;
}
