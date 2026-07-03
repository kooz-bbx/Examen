package cl.duoc.pagos_service.mapper;

import cl.duoc.pagos_service.dto.PagoDTO;
import cl.duoc.pagos_service.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {
    public PagoDTO toDto(Pago p) {
        return PagoDTO.builder()
                .id(p.getId()).monto(p.getMonto()).metodoPago(p.getMetodoPago())
                .estado(p.getEstado()).pedidoId(p.getPedidoId())
                .build();
    }
    public Pago toEntity(PagoDTO dto) {
        return Pago.builder()
                .id(dto.getId()).monto(dto.getMonto()).metodoPago(dto.getMetodoPago())
                .estado(dto.getEstado()).pedidoId(dto.getPedidoId())
                .build();
    }
}
