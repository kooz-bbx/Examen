package cl.duoc.productos_service.Mapper;

import cl.duoc.productos_service.Dto.ProductoDto;
import cl.duoc.productos_service.Model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public ProductoDto toDto(Producto p) {
        return ProductoDto.builder()
                .id(p.getId()).nombre(p.getNombre()).precio(p.getPrecio())
                .stock(p.getStock()).categoria(p.getCategoria()).disponible(p.getDisponible())
                .build();
    }
    public Producto toEntity(ProductoDto dto) {
        return Producto.builder()
                .id(dto.getId()).nombre(dto.getNombre()).precio(dto.getPrecio())
                .stock(dto.getStock()).categoria(dto.getCategoria()).disponible(dto.getDisponible())
                .build();
    }
}
