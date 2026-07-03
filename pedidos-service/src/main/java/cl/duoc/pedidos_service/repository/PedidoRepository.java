package cl.duoc.pedidos_service.repository;

import cl.duoc.pedidos_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstado(String estado);
    List<Pedido> findByClienteId(Long clienteId);
    @Query("SELECT p FROM Pedido p WHERE p.total > :minimo")
    List<Pedido> findByTotalMayorA(Double minimo);
    @Query("SELECT p FROM Pedido p ORDER BY p.total DESC")
    List<Pedido> findAllOrderByTotalDesc();
}
