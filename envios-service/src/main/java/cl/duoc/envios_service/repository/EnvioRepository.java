package cl.duoc.envios_service.repository;

import cl.duoc.envios_service.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByEstado(String estado);
    List<Envio> findByPedidoId(Long pedidoId);
    List<Envio> findByDireccionContainingIgnoreCase(String direccion);
}
