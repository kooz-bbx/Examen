package cl.duoc.empleados_service.mapper;

import cl.duoc.empleados_service.dto.EmpleadoDTO;
import cl.duoc.empleados_service.model.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {
    public EmpleadoDTO toDto(Empleado e) {
        return EmpleadoDTO.builder()
                .id(e.getId()).nombre(e.getNombre()).cargo(e.getCargo())
                .salario(e.getSalario()).correo(e.getCorreo()).turno(e.getTurno())
                .fechaIngreso(e.getFechaIngreso()).localId(e.getLocalId())
                .build();
    }
    public Empleado toEntity(EmpleadoDTO dto) {
        return Empleado.builder()
                .id(dto.getId()).nombre(dto.getNombre()).cargo(dto.getCargo())
                .salario(dto.getSalario()).correo(dto.getCorreo()).turno(dto.getTurno())
                .fechaIngreso(dto.getFechaIngreso()).localId(dto.getLocalId())
                .build();
    }
}
