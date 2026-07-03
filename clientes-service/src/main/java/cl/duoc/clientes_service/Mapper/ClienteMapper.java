package cl.duoc.clientes_service.Mapper;

import cl.duoc.clientes_service.Dto.ClienteDTO;
import cl.duoc.clientes_service.Model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public ClienteDTO toDTO(Cliente c) {
        return ClienteDTO.builder()
                .id(c.getId()).nombre(c.getNombre()).email(c.getEmail())
                .telefono(c.getTelefono()).ciudad(c.getCiudad())
                .build();
    }
    public Cliente toEntity(ClienteDTO dto) {
        return Cliente.builder()
                .id(dto.getId()).nombre(dto.getNombre()).email(dto.getEmail())
                .telefono(dto.getTelefono()).ciudad(dto.getCiudad())
                .build();
    }
}
