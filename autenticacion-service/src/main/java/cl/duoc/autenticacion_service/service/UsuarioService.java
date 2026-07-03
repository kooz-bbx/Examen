package cl.duoc.autenticacion_service.service;

import cl.duoc.autenticacion_service.dto.UsuarioDTO;
import cl.duoc.autenticacion_service.exception.ResourceNotFoundException;
import cl.duoc.autenticacion_service.mapper.UsuarioMapper;
import cl.duoc.autenticacion_service.model.Usuario;
import cl.duoc.autenticacion_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public List<UsuarioDTO> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public UsuarioDTO buscar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return mapper.toDto(usuario);
    }

    public UsuarioDTO buscarPorUsername(String username) {
        Usuario usuario = repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        return mapper.toDto(usuario);
    }

    public List<UsuarioDTO> buscarPorRol(String rol) {
        return repository.findAll().stream()
                .filter(u -> u.getRol().equalsIgnoreCase(rol))
                .map(mapper::toDto)
                .toList();
    }

    public UsuarioDTO guardar(UsuarioDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        return mapper.toDto(repository.save(usuario));
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        dto.setId(id);
        Usuario usuario = mapper.toEntity(dto);
        return mapper.toDto(repository.save(usuario));
    }

    public void eliminar(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        repository.deleteById(id);
    }

    public UsuarioDTO buscarPorId(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuario no encontrado con id: " + id));

        return mapper.toDto(usuario);
    }
}