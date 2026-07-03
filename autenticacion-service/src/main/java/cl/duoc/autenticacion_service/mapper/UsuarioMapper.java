package cl.duoc.autenticacion_service.mapper;

import cl.duoc.autenticacion_service.dto.UsuarioDTO;
import cl.duoc.autenticacion_service.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioDTO toDto(Usuario u) {
        return UsuarioDTO.builder()
                .id(u.getId()).username(u.getUsername())
                .password(u.getPassword()).rol(u.getRol())
                .build();
    }
    public Usuario toEntity(UsuarioDTO dto) {
        return Usuario.builder()
                .id(dto.getId()).username(dto.getUsername())
                .password(dto.getPassword()).rol(dto.getRol())
                .build();
    }
}
