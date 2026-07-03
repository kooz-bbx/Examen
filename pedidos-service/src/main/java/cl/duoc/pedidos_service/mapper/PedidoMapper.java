package cl.duoc.pedidos_service.mapper;

import cl.duoc.pedidos_service.dto.PedidoDto;
import cl.duoc.pedidos_service.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {
    public PedidoDto toDto(Pedido p) {
        return PedidoDto.builder()
                .id(p.getId()).clienteId(p.getClienteId()).detalle(p.getDetalle())
                .total(p.getTotal()).estado(p.getEstado())
                .build();
    }
    public Pedido toEntity(PedidoDto dto) {
        return Pedido.builder()
                .id(dto.getId()).clienteId(dto.getClienteId()).detalle(dto.getDetalle())
                .total(dto.getTotal()).estado(dto.getEstado())
                .build();
    }

    public Pedido toModel(PedidoDto dto) {

        return Pedido.builder()
                .id(dto.getId())
                .clienteId(dto.getClienteId())
                .estado(dto.getEstado())
                .build();
    }{
    }
}
