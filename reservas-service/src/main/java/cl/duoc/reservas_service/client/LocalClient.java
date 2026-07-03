package cl.duoc.reservas_service.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "locales-service", path = "/api/v1/locales")
public interface LocalClient {

    @GetMapping("/{id}")
    LocalDTO buscarPorId(@PathVariable Long id);

    @GetMapping("/{id}")
    LocalDTO obtenerLocal(
            @PathVariable("id")
            @NotNull(message = "El ID del local no puede ser nulo.")
            Long localId
    );
}
