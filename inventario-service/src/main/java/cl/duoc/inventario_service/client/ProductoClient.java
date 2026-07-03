package cl.duoc.inventario_service.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productos-service", path = "/api/v1/productos")
public interface ProductoClient {

    @GetMapping("/{id}")
    ProductoDTO buscarPorId(@PathVariable Long id);

    @GetMapping("/{id}")
    ProductoDTO obtenerProducto(
            @PathVariable("id")
            @NotNull(message = "El ID del producto no puede ser nulo.")
            Long productoId
    );
}
