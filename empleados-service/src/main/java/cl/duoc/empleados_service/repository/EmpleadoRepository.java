package cl.duoc.empleados_service.repository;

import cl.duoc.empleados_service.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByCorreo(String correo);

    List<Empleado> findByCargo(String cargo);

    List<Empleado> findByLocalId(Long localId);

    @Query("SELECT e FROM Empleado e WHERE e.salario BETWEEN :min AND :max")
    List<Empleado> findBySalarioEntre(@Param("min") Double min, @Param("max") Double max);

    List<Empleado> findByTurno(String turno);
}
