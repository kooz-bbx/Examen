package cl.duoc.pagos_service.repository;

import cl.duoc.pagos_service.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByEstado(String estado);
    List<Pago> findByMetodoPago(String metodoPago);
    List<Pago> findByPedidoId(Long pedidoId);
    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.estado = 'APROBADO'")
    Double totalPagosAprobados();
}
