package cl.duoc.inventario_service.repository;

import cl.duoc.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findByLocalId(Long localId);
    List<Inventario> findByProductoId(Long productoId);
    @Query("SELECT i FROM Inventario i WHERE i.stock < :umbral")
    List<Inventario> findStockBajoUmbral(@Param("umbral") Integer umbral);
    @Query("SELECT i FROM Inventario i WHERE i.stock = 0")
    List<Inventario> findProductosSinStock();
}
