package cl.duoc.inventario_service.service;

import cl.duoc.inventario_service.client.ProductoClient;
import cl.duoc.inventario_service.client.ProductoDTO;
import cl.duoc.inventario_service.dto.InventarioConProductoDTO;
import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.exception.ResourceNotFoundException;
import cl.duoc.inventario_service.mapper.InventarioMapper;
import cl.duoc.inventario_service.model.Inventario;
import cl.duoc.inventario_service.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {

    private final InventarioRepository repository;
    private final InventarioMapper mapper;
    private final ProductoClient productoClient;

    public InventarioService(InventarioRepository repository, InventarioMapper mapper, ProductoClient productoClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.productoClient = productoClient;
    }

    public List<InventarioDTO> listar() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public InventarioDTO buscarPorId(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado: " + id)));
    }

    public InventarioDTO guardar(InventarioDTO dto) {
        // Valida que el producto exista antes de registrar stock
        productoClient.obtenerProducto(dto.getProductoId());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public InventarioConProductoDTO buscarConProducto(Long id) {
        Inventario inv = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado: " + id));
        ProductoDTO producto = productoClient.obtenerProducto(inv.getProductoId());
        return new InventarioConProductoDTO(inv.getId(), inv.getLocalId(), inv.getStock(), producto);
    }

    public InventarioDTO actualizar(Long id, InventarioDTO dto) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado: " + id));
        dto.setId(id);
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void eliminar(Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado: " + id));
        repository.deleteById(id);
    }

    public List<InventarioDTO> buscarPorLocal(Long localId) {
        return repository.findByLocalId(localId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<InventarioDTO> buscarPorProducto(Long productoId) {
        return repository.findByProductoId(productoId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<InventarioDTO> stockBajo(Integer umbral) {
        return repository.findStockBajoUmbral(umbral).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<InventarioDTO> sinStock() {
        return repository.findProductosSinStock().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
