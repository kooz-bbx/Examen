package cl.duoc.productos_service.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres.")
    private String nombre;

    @NotNull(message = "El precio no puede ser nulo.")
    @Positive(message = "El precio debe ser mayor a 0.")
    private BigDecimal precio;

    @NotNull(message = "El stock no puede ser nulo.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer stock;

    @NotBlank(message = "La categoría no puede estar vacía.")
    @Size(max = 100, message = "La categoría no puede superar 100 caracteres.")
    private String categoria;

    @NotNull(message = "El campo disponible no puede ser nulo.")
    private Boolean disponible;
}
