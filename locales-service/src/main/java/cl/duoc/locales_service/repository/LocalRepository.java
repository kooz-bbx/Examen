package cl.duoc.locales_service.repository;

import cl.duoc.locales_service.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
    List<Local> findByComuna(String comuna);
    List<Local> findByActivo(Boolean activo);
    List<Local> findByNombreContainingIgnoreCase(String nombre);
}
