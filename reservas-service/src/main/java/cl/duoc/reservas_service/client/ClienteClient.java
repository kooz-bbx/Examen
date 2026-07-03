package cl.duoc.reservas_service.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes-service", path = "/api/v1/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    ClienteDTO buscarPorId(@PathVariable Long id);

    @GetMapping("/{id}")
    ClienteDTO obtenerCliente(
            @PathVariable("id")
            @NotNull(message = "El ID del cliente no puede ser nulo.")
            Long clienteId
    );
}
