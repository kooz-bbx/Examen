package cl.duoc.inventario_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "inventario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del producto no puede ser nulo.")
    private Long productoId;

    @NotNull(message = "El ID del local no puede ser nulo.")
    private Long localId;

    @NotNull(message = "El stock no puede ser nulo.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer stock;
}
