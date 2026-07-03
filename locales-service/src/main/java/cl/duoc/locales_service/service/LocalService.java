package cl.duoc.locales_service.service;

import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.exception.ResourceNotFoundException;
import cl.duoc.locales_service.mapper.LocalMapper;
import cl.duoc.locales_service.model.Local;
import cl.duoc.locales_service.repository.LocalRepository;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository repository;
    private final LocalMapper mapper;

    // CREAR
    public LocalDTO guardar(@Valid LocalDTO dto) {

        Local local = mapper.toEntity(dto);

        Local guardado = repository.save(local);

        return mapper.toDTO(guardado);
    }

    // LISTAR
    public List<LocalDTO> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // BUSCAR POR ID
    public LocalDTO buscarPorId(Long id) {

        Local local = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Local no encontrado: " + id));

        return mapper.toDTO(local);
    }

    // ACTUALIZAR
    public LocalDTO actualizar(Long id, LocalDTO dto) {

        repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Local no encontrado: " + id));

        Local local = mapper.toEntity(dto);

        local.setId(id);

        Local actualizado = repository.save(local);

        return mapper.toDTO(actualizado);
    }

    // ELIMINAR
    public void eliminar(Long id) {

        repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Local no encontrado: " + id));

        repository.deleteById(id);
    }

    // BUSCAR POR COMUNA
    public List<LocalDTO> buscarPorComuna(String comuna) {

        return repository.findByComuna(comuna)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // BUSCAR ACTIVOS
    public List<LocalDTO> buscarActivos() {

        return repository.findByActivo(true)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // BUSCAR POR NOMBRE
    public List<LocalDTO> buscarPorNombre(String nombre) {

        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}