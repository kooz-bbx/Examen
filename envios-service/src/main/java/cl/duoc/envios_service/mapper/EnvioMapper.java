package cl.duoc.envios_service.mapper;

import cl.duoc.envios_service.dto.EnvioDTO;
import cl.duoc.envios_service.model.Envio;
import org.springframework.stereotype.Component;

@Component
public class EnvioMapper {
    public EnvioDTO toDto(Envio e) {
        return EnvioDTO.builder()
                .id(e.getId()).pedidoId(e.getPedidoId()).direccion(e.getDireccion())
                .estado(e.getEstado()).fechaEnvio(e.getFechaEnvio())
                .build();
    }
    public Envio toEntity(EnvioDTO dto) {
        return Envio.builder()
                .id(dto.getId()).pedidoId(dto.getPedidoId()).direccion(dto.getDireccion())
                .estado(dto.getEstado()).fechaEnvio(dto.getFechaEnvio())
                .build();
    }
}
