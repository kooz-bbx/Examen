package cl.duoc.inventario_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Representa el stock de un producto en un local especifico.")
public class InventarioDTO {

    @Schema(description = "Identificador unico del registro de inventario. No debe enviarse al crear.",
            example = "1")
    private Long id;

    @NotNull(message = "El ID del producto no puede ser nulo.")
    @Schema(description = "Identificador del producto asociado.", example = "3")
    private Long productoId;

    @NotNull(message = "El ID del local no puede ser nulo.")
    @Schema(description = "Identificador del local donde se encuentra el stock.", example = "1")
    private Long localId;

    @NotNull(message = "El stock no puede ser nulo.")
    @Min(0)
    @Schema(description = "Cantidad de unidades disponibles.", example = "50")
    private Integer stock;
}
