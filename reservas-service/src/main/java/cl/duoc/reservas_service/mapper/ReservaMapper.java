package cl.duoc.reservas_service.mapper;

import cl.duoc.reservas_service.dto.ReservaDTO;
import cl.duoc.reservas_service.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {
    public ReservaDTO toDto(Reserva r) {
        return ReservaDTO.builder()
                .id(r.getId()).fecha(r.getFecha()).hora(r.getHora())
                .cantidadPersonas(r.getCantidadPersonas())
                .clienteId(r.getClienteId()).localId(r.getLocalId())
                .build();
    }
    public Reserva toEntity(ReservaDTO dto) {
        return Reserva.builder()
                .id(dto.getId()).fecha(dto.getFecha()).hora(dto.getHora())
                .cantidadPersonas(dto.getCantidadPersonas())
                .clienteId(dto.getClienteId()).localId(dto.getLocalId())
                .build();
    }

}
