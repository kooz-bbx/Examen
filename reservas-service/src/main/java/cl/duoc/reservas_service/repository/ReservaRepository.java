package cl.duoc.reservas_service.repository;

import cl.duoc.reservas_service.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByClienteId(Long clienteId);
    List<Reserva> findByLocalId(Long localId);
    List<Reserva> findByFecha(String fecha);
    List<Reserva> findByCantidadPersonasGreaterThanEqual(Integer cantidad);
}
