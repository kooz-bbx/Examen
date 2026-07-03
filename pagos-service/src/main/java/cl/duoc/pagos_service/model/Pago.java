package cl.duoc.pagos_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pagos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El monto no puede ser nulo.")
    @Positive(message = "El monto debe ser mayor a 0.")
    private BigDecimal monto;

    @NotBlank(message = "El metodo de pago no puede estar vacio.")
    @Size(max = 50, message = "El metodo de pago no puede superar 50 caracteres.")
    private String metodoPago;

    @NotBlank(message = "El estado no puede estar vacio.")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres.")
    private String estado;

    @NotNull(message = "El ID del pedido no puede ser nulo.")
    private Long pedidoId;
}
