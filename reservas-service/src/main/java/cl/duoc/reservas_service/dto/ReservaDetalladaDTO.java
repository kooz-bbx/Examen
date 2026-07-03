package cl.duoc.reservas_service.dto;

import cl.duoc.reservas_service.client.ClienteDTO;
import cl.duoc.reservas_service.client.LocalDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Reserva con los datos completos del cliente y del local embebidos (respuesta de GET /{id}).")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDetalladaDTO {

    @Schema(description = "Identificador de la reserva.", example = "1")
    private Long id;

    @Schema(description = "Fecha de la reserva.", example = "2026-07-10")
    private String fecha;

    @Schema(description = "Hora de la reserva.", example = "20:30")
    private String hora;

    @Schema(description = "Cantidad de personas incluidas en la reserva.", example = "4")
    private Integer cantidadPersonas;

    @Schema(description = "Datos completos del cliente, obtenidos desde clientes-service.")
    private ClienteDTO cliente;

    @Schema(description = "Datos completos del local, obtenidos desde locales-service.")
    private LocalDTO local;
}
