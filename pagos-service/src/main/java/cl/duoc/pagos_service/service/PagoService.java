package cl.duoc.pagos_service.service;

import cl.duoc.pagos_service.client.PedidoClient;
import cl.duoc.pagos_service.client.PedidoDTO;
import cl.duoc.pagos_service.dto.PagoConPedidoDTO;
import cl.duoc.pagos_service.dto.PagoDTO;
import cl.duoc.pagos_service.exception.ResourceNotFoundException;
import cl.duoc.pagos_service.mapper.PagoMapper;
import cl.duoc.pagos_service.model.Pago;
import cl.duoc.pagos_service.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagoRepository repository;
    private final PagoMapper mapper;
    private final PedidoClient pedidoClient;

    public PagoService(PagoRepository repository, PagoMapper mapper, PedidoClient pedidoClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.pedidoClient = pedidoClient;
    }

    public List<PagoDTO> listar() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public PagoDTO buscarPorId(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado: " + id)));
    }

    public PagoDTO guardar(PagoDTO dto) {
        // Valida que el pedido exista antes de registrar el pago
        pedidoClient.obtenerPedido(dto.getPedidoId());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public PagoConPedidoDTO buscarConPedido(Long id) {
        Pago pago = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado: " + id));
        PedidoDTO pedido = pedidoClient.obtenerPedido(pago.getPedidoId());
        return new PagoConPedidoDTO(pago.getId(), pago.getMonto(), pago.getMetodoPago(), pago.getEstado(), pedido);
    }

    public PagoDTO actualizar(Long id, PagoDTO dto) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado: " + id));
        dto.setId(id);
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void eliminar(Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado: " + id));
        repository.deleteById(id);
    }

    public List<PagoDTO> porEstado(String estado) {
        return repository.findByEstado(estado).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<PagoDTO> porMetodoPago(String metodo) {
        return repository.findByMetodoPago(metodo).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<PagoDTO> porPedido(Long pedidoId) {
        return repository.findByPedidoId(pedidoId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Double totalAprobados() {
        Double total = repository.totalPagosAprobados();
        return total != null ? total : 0.0;
    }
}
