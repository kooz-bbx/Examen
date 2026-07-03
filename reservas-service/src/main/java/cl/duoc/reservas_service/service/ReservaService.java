package cl.duoc.reservas_service.service;

import cl.duoc.reservas_service.client.ClienteClient;
import cl.duoc.reservas_service.client.ClienteDTO;
import cl.duoc.reservas_service.client.LocalClient;
import cl.duoc.reservas_service.client.LocalDTO;
import cl.duoc.reservas_service.dto.ReservaDTO;
import cl.duoc.reservas_service.dto.ReservaDetalladaDTO;
import cl.duoc.reservas_service.exception.ResourceNotFoundException;
import cl.duoc.reservas_service.mapper.ReservaMapper;
import cl.duoc.reservas_service.model.Reserva;
import cl.duoc.reservas_service.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository repository;
    private final ReservaMapper mapper;
    private final ClienteClient clienteClient;
    private final LocalClient localClient;

    public ReservaService(ReservaRepository repository, ReservaMapper mapper,
                          ClienteClient clienteClient, LocalClient localClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.clienteClient = clienteClient;
        this.localClient = localClient;
    }

    public ReservaDTO guardar(ReservaDTO dto) {
        // Valida que tanto el cliente como el local existan antes de crear la reserva
        clienteClient.obtenerCliente(dto.getClienteId());
        localClient.obtenerLocal(dto.getLocalId());
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public List<ReservaDTO> listar() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public ReservaDTO buscarPorId(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada: " + id)));
    }

    public ReservaDetalladaDTO buscarDetallada(Long id) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada: " + id));
        ClienteDTO cliente = clienteClient.obtenerCliente(reserva.getClienteId());
        LocalDTO local = localClient.obtenerLocal(reserva.getLocalId());
        return new ReservaDetalladaDTO(
                reserva.getId(), reserva.getFecha(), reserva.getHora(),
                reserva.getCantidadPersonas(), cliente, local
        );
    }

    public ReservaDTO actualizar(Long id, ReservaDTO dto) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada: " + id));
        dto.setId(id);
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void eliminar(Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada: " + id));
        repository.deleteById(id);
    }

    public List<ReservaDTO> buscarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<ReservaDTO> buscarPorLocal(Long localId) {
        return repository.findByLocalId(localId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<ReservaDTO> buscarPorFecha(String fecha) {
        return repository.findByFecha(fecha).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<ReservaDTO> buscarPorPersonasMinimas(Integer cantidad) {
        return repository.findByCantidadPersonasGreaterThanEqual(cantidad).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
