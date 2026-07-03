package cl.duoc.envios_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "envios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del pedido no puede ser nulo.")
    private Long pedidoId;

    @NotBlank(message = "La direccion no puede estar vacia.")
    @Size(max = 255, message = "La direccion no puede superar 255 caracteres.")
    private String direccion;

    @NotBlank(message = "El estado no puede estar vacio.")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres.")
    private String estado;

    @NotBlank(message = "La fecha de envio no puede estar vacia.")
    @Size(max = 50)
    private String fechaEnvio;
}
