package cl.duoc.productos_service.Repository;

import cl.duoc.productos_service.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByDisponible(Boolean disponible);
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :min AND :max")
    List<Producto> findByPrecioEntre(Double min, Double max);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
