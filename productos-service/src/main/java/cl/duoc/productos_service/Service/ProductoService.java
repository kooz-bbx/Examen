package cl.duoc.productos_service.Service;

import cl.duoc.productos_service.Dto.ProductoDto;
import cl.duoc.productos_service.Exception.ResourceNotFoundException;
import cl.duoc.productos_service.Mapper.ProductoMapper;
import cl.duoc.productos_service.Model.Producto;
import cl.duoc.productos_service.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public List<ProductoDto> obtenerTodos() {
        return productoRepository.findAll().stream().map(productoMapper::toDto).collect(Collectors.toList());
    }

    public ProductoDto guardar(ProductoDto dto) {
        Producto p = productoMapper.toEntity(dto);
        return productoMapper.toDto(productoRepository.save(p));
    }

    public ProductoDto buscarPorId(Long id) {
        return productoMapper.toDto(productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id)));
    }

    public ProductoDto actualizar(Long id, ProductoDto dto) {
        productoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        dto.setId(id);
        return productoMapper.toDto(productoRepository.save(productoMapper.toEntity(dto)));
    }

    public void eliminar(Long id) {
        productoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        productoRepository.deleteById(id);
    }

    public List<ProductoDto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria).stream().map(productoMapper::toDto).collect(Collectors.toList());
    }

    public List<ProductoDto> buscarDisponibles() {
        return productoRepository.findByDisponible(true).stream().map(productoMapper::toDto).collect(Collectors.toList());
    }

    public List<ProductoDto> buscarPorRangoPrecio(Double min, Double max) {
        return productoRepository.findByPrecioEntre(min, max).stream().map(productoMapper::toDto).collect(Collectors.toList());
    }

    public List<ProductoDto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream().map(productoMapper::toDto).collect(Collectors.toList());
    }
}
