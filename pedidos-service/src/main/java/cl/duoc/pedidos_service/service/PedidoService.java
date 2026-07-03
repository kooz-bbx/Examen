package cl.duoc.pedidos_service.service;

import cl.duoc.pedidos_service.client.ClienteClient;
import cl.duoc.pedidos_service.client.ClienteDTO;
import cl.duoc.pedidos_service.dto.PedidoConClienteDTO;
import cl.duoc.pedidos_service.dto.PedidoDto;
import cl.duoc.pedidos_service.exception.ResourceNotFoundException;
import cl.duoc.pedidos_service.mapper.PedidoMapper;
import cl.duoc.pedidos_service.model.Pedido;
import cl.duoc.pedidos_service.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;
    private final ClienteClient clienteClient;

    public PedidoService(PedidoRepository repository, PedidoMapper mapper, ClienteClient clienteClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.clienteClient = clienteClient;
    }

    public List<PedidoDto> listar() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public PedidoDto guardar(PedidoDto dto) {
        // Valida que el cliente exista antes de crear el pedido
        clienteClient.obtenerCliente(dto.getClienteId());
        return mapper.toDto(repository.save(mapper.toModel(dto)));
    }

    public PedidoDto buscarPorId(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id)));
    }

    public PedidoConClienteDTO buscarConCliente(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id));
        ClienteDTO cliente = clienteClient.obtenerCliente(pedido.getClienteId());
        return new PedidoConClienteDTO(
                pedido.getId(),
                pedido.getDetalle(),
                pedido.getTotal(),
                pedido.getEstado(),
                cliente
        );
    }

    public PedidoDto actualizar(Long id, PedidoDto dto) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id));
        dto.setId(id);
        return mapper.toDto(repository.save(mapper.toModel(dto)));
    }

    public void eliminar(Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado: " + id));
        repository.deleteById(id);
    }

    public List<PedidoDto> buscarPorEstado(String estado) {
        return repository.findByEstado(estado).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<PedidoDto> buscarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<PedidoDto> buscarPorTotalMinimo(Double minimo) {
        return repository.findByTotalMayorA(minimo).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<PedidoDto> listarPorTotal() {
        return repository.findAllOrderByTotalDesc().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
