package cl.duoc.reservas_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La fecha no puede estar vacía.")
    @Size(max = 20, message = "La fecha no puede superar 20 caracteres.")
    private String fecha;

    @NotBlank(message = "La hora no puede estar vacía.")
    @Size(max = 10, message = "La hora no puede superar 10 caracteres.")
    private String hora;

    @NotNull(message = "La cantidad de personas no puede ser nula.")
    @Min(value = 1, message = "Debe haber al menos 1 persona.")
    @Max(value = 50, message = "No se pueden reservar más de 50 personas.")
    private Integer cantidadPersonas;

    @NotNull(message = "El ID del cliente no puede ser nulo.")
    private Long clienteId;

    @NotNull(message = "El ID del local no puede ser nulo.")
    private Long localId;
}
