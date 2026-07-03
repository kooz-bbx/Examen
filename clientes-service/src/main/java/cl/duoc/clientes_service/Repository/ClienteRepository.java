package cl.duoc.clientes_service.Repository;

import cl.duoc.clientes_service.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByCiudad(String ciudad);

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}
