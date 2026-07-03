package cl.duoc.pedidos_service.controller;

import cl.duoc.pedidos_service.client.ClienteDTO;
import cl.duoc.pedidos_service.dto.PedidoConClienteDTO;
import cl.duoc.pedidos_service.dto.PedidoDto;
import cl.duoc.pedidos_service.service.PedidoService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de la capa @RestController (PedidoController).
 * Autor: Integrante 3.
 *
 * @WebMvcTest solo levanta la capa web; PedidoService (y por lo tanto
 * el llamado Feign que hace internamente) se reemplaza por un mock.
 */
@WebMvcTest(PedidoController.class)
@DisplayName("PedidoController - capa Controller")
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PedidoService pedidoService;

    @Test
    @DisplayName("GET /api/v1/pedidos responde 200 con el listado")
    void listar_retorna200() throws Exception {
        // CORREGIDO: Se cambió .total(15990d) por BigDecimal.valueOf(15990.0)
        PedidoDto dto = PedidoDto.builder()
                .id(1L)
                .clienteId(5L)
                .detalle("2 pizzas")
                .total(BigDecimal.valueOf(15990.0))
                .estado("PENDIENTE")
                .build();
        when(pedidoService.listar()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/v1/pedidos/{id} responde 200 con el pedido y su cliente")
    void buscarPorId_retorna200ConCliente() throws Exception {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setId(5L);
        cliente.setNombre("Ana Soto");

        PedidoConClienteDTO dto = new PedidoConClienteDTO(1L, "2 pizzas", BigDecimal.valueOf(15990.0), "PENDIENTE", cliente);
        when(pedidoService.buscarConCliente(eq(1L))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/pedidos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente.nombre").value("Ana Soto"));
    }

    @Test
    @DisplayName("POST /api/v1/pedidos con datos validos responde 200")
    void guardar_datosValidos_retorna200() throws Exception {
        // CORREGIDO: Se cambió .total(7990d) por BigDecimal.valueOf(7990.0)
        PedidoDto nuevo = PedidoDto.builder()
                .clienteId(5L)
                .detalle("1 hamburguesa")
                .total(BigDecimal.valueOf(7990.0))
                .estado("PENDIENTE")
                .build();

        // CORREGIDO: Se cambió .total(7990d) por BigDecimal.valueOf(7990.0)
        PedidoDto creado = PedidoDto.builder()
                .id(2L)
                .clienteId(5L)
                .detalle("1 hamburguesa")
                .total(BigDecimal.valueOf(7990.0))
                .estado("PENDIENTE")
                .build();
        when(pedidoService.guardar(any(PedidoDto.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    @DisplayName("POST /api/v1/pedidos con total negativo responde 400")
    void guardar_totalInvalido_retorna400() throws Exception {
        // CORREGIDO: Se cambió .total(-10d) por BigDecimal.valueOf(-10.0)
        PedidoDto invalido = PedidoDto.builder()
                .clienteId(5L)
                .detalle("1 hamburguesa")
                .total(BigDecimal.valueOf(-10.0))
                .estado("PENDIENTE")
                .build();

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/v1/pedidos/{id} responde 204")
    void eliminar_retorna204() throws Exception {
        mockMvc.perform(delete("/api/v1/pedidos/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}