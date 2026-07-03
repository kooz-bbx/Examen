package cl.duoc.inventario_service.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Datos del producto obtenidos desde productos-service (via Feign).")
@Data
public class ProductoDTO {

    @Schema(description = "Identificador del producto.", example = "3")
    private Long id;

    @Schema(description = "Nombre del producto.", example = "Pizza Margarita")
    private String nombre;

    @Schema(description = "Precio unitario del producto.", example = "8990.0")
    private Double precio;

    @Schema(description = "Categoria del producto.", example = "Pizzas")
    private String categoria;

    @Schema(description = "Indica si el producto esta disponible para la venta.", example = "true")
    private Boolean disponible;
}
