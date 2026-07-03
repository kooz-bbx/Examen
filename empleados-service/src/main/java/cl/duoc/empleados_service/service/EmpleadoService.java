package cl.duoc.empleados_service.service;

import cl.duoc.empleados_service.dto.EmpleadoDTO;
import cl.duoc.empleados_service.exception.ResourceNotFoundException;
import cl.duoc.empleados_service.mapper.EmpleadoMapper;
import cl.duoc.empleados_service.model.Empleado;
import cl.duoc.empleados_service.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repository;
    private final EmpleadoMapper mapper;

    public EmpleadoService(EmpleadoRepository repository, EmpleadoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<EmpleadoDTO> listar() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public EmpleadoDTO buscarPorId(Long id) {
        Empleado e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));
        return mapper.toDto(e);
    }

    public EmpleadoDTO guardar(EmpleadoDTO dto) {
        Empleado e = mapper.toEntity(dto);
        return mapper.toDto(repository.save(e));
    }

    public EmpleadoDTO actualizar(Long id, EmpleadoDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));
        dto.setId(id);
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void eliminar(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));
        repository.deleteById(id);
    }

    // REPORTES
    public EmpleadoDTO buscarPorCorreo(String correo) {
        return mapper.toDto(repository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con correo: " + correo)));
    }

    public List<EmpleadoDTO> buscarPorCargo(String cargo) {
        return repository.findByCargo(cargo).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<EmpleadoDTO> buscarPorLocal(Long localId) {
        return repository.findByLocalId(localId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<EmpleadoDTO> buscarPorRangoSalario(Double min, Double max) {
        return repository.findBySalarioEntre(min, max).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<EmpleadoDTO> buscarPorTurno(String turno) {
        return repository.findByTurno(turno).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
