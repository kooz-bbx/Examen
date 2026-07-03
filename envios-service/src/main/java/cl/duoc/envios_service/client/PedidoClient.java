package cl.duoc.envios_service.client;

import jakarta.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pedidos-service", path = "/api/v1/pedidos")
public interface PedidoClient {

    @GetMapping("/{id}")
    PedidoDTO buscarPorId(@PathVariable("id") Long id);

    @GetMapping("/{id}")
    PedidoDTO obtenerPedido(
            @PathVariable("id")
            @NotNull(message = "El ID del pedido no puede ser nulo.")
            Long pedidoId
    );
}
