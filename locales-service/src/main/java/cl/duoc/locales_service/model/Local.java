package cl.duoc.locales_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "locales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres.")
    private String nombre;

    @NotBlank(message = "La dirección no puede estar vacía.")
    @Size(max = 255, message = "La dirección no puede superar 255 caracteres.")
    private String direccion;

    @NotBlank(message = "La comuna no puede estar vacía.")
    @Size(max = 100, message = "La comuna no puede superar 100 caracteres.")
    private String comuna;

    @NotBlank(message = "El horario no puede estar vacío.")
    @Size(max = 100, message = "El horario no puede superar 100 caracteres.")
    private String horario;

    @NotNull(message = "El campo activo no puede ser nulo.")
    private Boolean activo;
}
