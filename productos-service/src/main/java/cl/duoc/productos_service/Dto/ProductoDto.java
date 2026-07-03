package cl.duoc.productos_service.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa un producto del catalogo del restaurant.")
public class ProductoDto {

    @Schema(description = "Identificador unico del producto. No debe enviarse al crear.", example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres.")
    @Schema(description = "Nombre del producto.", example = "Pizza Margarita")
    private String nombre;

    @NotNull(message = "El precio no puede ser nulo.")
    @Positive(message = "El precio debe ser mayor a 0.")
    @Schema(description = "Precio de venta unitario.", example = "8990.0")
    private BigDecimal precio;

    @NotNull(message = "El stock no puede ser nulo.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    @Schema(description = "Cantidad de unidades disponibles.", example = "20")
    private Integer stock;

    @NotBlank(message = "La categoría no puede estar vacía.")
    @Size(max = 100, message = "La categoría no puede superar 100 caracteres.")
    @Schema(description = "Categoria a la que pertenece el producto.", example = "Pizzas")
    private String categoria;

    @NotNull(message = "El campo disponible no puede ser nulo.")
    @Schema(description = "Indica si el producto esta disponible para la venta.", example = "true")
    private Boolean disponible;
}
