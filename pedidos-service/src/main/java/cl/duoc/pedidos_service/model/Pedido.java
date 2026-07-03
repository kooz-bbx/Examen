package cl.duoc.pedidos_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del cliente no puede ser nulo.")
    private Long clienteId;

    @NotBlank(message = "El detalle no puede estar vacío.")
    @Size(max = 500, message = "El detalle no puede superar 500 caracteres.")
    private String detalle;

    @NotNull(message = "El total no puede ser nulo.")
    @Positive(message = "El total debe ser mayor a 0.")
    private BigDecimal total;

    @NotBlank(message = "El estado no puede estar vacío.")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres.")
    private String estado;
}
