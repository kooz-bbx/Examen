package cl.duoc.inventario_service.dto;

import cl.duoc.inventario_service.client.ProductoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Registro de inventario con los datos completos del producto embebidos (respuesta de GET /{id}).")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioConProductoDTO {

    @Schema(description = "Identificador del registro de inventario.", example = "1")
    private Long id;

    @Schema(description = "Identificador del local donde se encuentra el stock.", example = "1")
    private Long localId;

    @Schema(description = "Cantidad de unidades disponibles.", example = "50")
    private Integer stock;

    @Schema(description = "Datos completos del producto asociado, obtenidos desde productos-service.")
    private ProductoDTO producto;
}
