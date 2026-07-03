package cl.duoc.inventario_service.mapper;

import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.model.Inventario;
import org.springframework.stereotype.Component;

@Component
public class InventarioMapper {
    public InventarioDTO toDto(Inventario i) {
        return InventarioDTO.builder()
                .id(i.getId()).productoId(i.getProductoId())
                .localId(i.getLocalId()).stock(i.getStock())
                .build();
    }
    public Inventario toEntity(InventarioDTO dto) {
        return Inventario.builder()
                .id(dto.getId()).productoId(dto.getProductoId())
                .localId(dto.getLocalId()).stock(dto.getStock())
                .build();
    }

}
