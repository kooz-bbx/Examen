package cl.duoc.clientes_service.Service;

import cl.duoc.clientes_service.Dto.ClienteDTO;
import cl.duoc.clientes_service.Exception.ResourceNotFoundException;
import cl.duoc.clientes_service.Mapper.ClienteMapper;
import cl.duoc.clientes_service.Model.Cliente;
import cl.duoc.clientes_service.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private ClienteMapper mapper;

    public List<ClienteDTO> listar() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    public ClienteDTO buscar(Long id) {
        Cliente cliente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return mapper.toDTO(cliente);
    }

    public ClienteDTO guardar(ClienteDTO dto) {
        Cliente cliente = mapper.toEntity(dto);
        return mapper.toDTO(repo.save(cliente));
    }

    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        dto.setId(id);
        return mapper.toDTO(repo.save(mapper.toEntity(dto)));
    }

    public void eliminar(Long id) {
        repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        repo.deleteById(id);
    }

    // REPORTES
    public ClienteDTO buscarPorEmail(String email) {
        return mapper.toDTO(repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con email: " + email)));
    }

    public List<ClienteDTO> buscarPorCiudad(String ciudad) {
        return repo.findByCiudad(ciudad).stream().map(mapper::toDTO).toList();
    }

    public List<ClienteDTO> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre).stream().map(mapper::toDTO).toList();
    }
}
