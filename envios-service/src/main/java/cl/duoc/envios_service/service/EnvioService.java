package cl.duoc.envios_service.service;

import cl.duoc.envios_service.client.PedidoClient;
import cl.duoc.envios_service.client.PedidoDTO;
import cl.duoc.envios_service.dto.EnvioConPedidoDTO;
import cl.duoc.envios_service.dto.EnvioDTO;
import cl.duoc.envios_service.exception.ResourceNotFoundException;
import cl.duoc.envios_service.mapper.EnvioMapper;
import cl.duoc.envios_service.model.Envio;
import cl.duoc.envios_service.repository.EnvioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvioService {

    private final EnvioRepository repository;
    private final EnvioMapper mapper;
    private final PedidoClient pedidoClient;

    public EnvioService(EnvioRepository repository, EnvioMapper mapper, PedidoClient pedidoClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.pedidoClient = pedidoClient;
    }

    public List<EnvioDTO> listar() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public EnvioDTO buscarPorId(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + id)));
    }

    public EnvioDTO guardar(EnvioDTO dto) {
        // Valida que el pedido exista antes de crear el envío
        pedidoClient.obtenerPedido(dto.getPedidoId());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public EnvioConPedidoDTO buscarConPedido(Long id) {
        Envio envio = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + id));
        PedidoDTO pedido = pedidoClient.obtenerPedido(envio.getPedidoId());
        return new EnvioConPedidoDTO(envio.getId(), envio.getDireccion(), envio.getEstado(), envio.getFechaEnvio(), pedido);
    }

    public EnvioDTO actualizar(Long id, EnvioDTO dto) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + id));
        dto.setId(id);
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void eliminar(Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Envio no encontrado: " + id));
        repository.deleteById(id);
    }

    public List<EnvioDTO> buscarPorEstado(String estado) {
        return repository.findByEstado(estado).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<EnvioDTO> buscarPorPedido(Long pedidoId) {
        return repository.findByPedidoId(pedidoId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<EnvioDTO> buscarPorDireccion(String direccion) {
        return repository.findByDireccionContainingIgnoreCase(direccion).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
