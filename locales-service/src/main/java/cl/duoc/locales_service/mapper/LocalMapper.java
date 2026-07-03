package cl.duoc.locales_service.mapper;

import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.model.Local;

import org.springframework.stereotype.Component;

@Component
public class LocalMapper {

    public LocalDTO toDTO(Local l) {

        return LocalDTO.builder()
                .id(l.getId())
                .nombre(l.getNombre())
                .direccion(l.getDireccion())
                .comuna(l.getComuna())
                .horario(l.getHorario())
                .activo(l.getActivo())
                .build();
    }

    public Local toEntity(LocalDTO dto) {

        return Local.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .comuna(dto.getComuna())
                .horario(dto.getHorario())
                .activo(dto.getActivo())
                .build();
    }
}