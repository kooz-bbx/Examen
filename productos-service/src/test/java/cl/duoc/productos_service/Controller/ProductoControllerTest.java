package cl.duoc.productos_service.Controller;

import cl.duoc.productos_service.Dto.ProductoDto;
import cl.duoc.productos_service.Service.ProductoService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal; // IMPORTACIÓN AÑADIDA
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de la capa @RestController (ProductoController).
 * Autor: Integrante 3.
 */
@WebMvcTest(ProductoController.class)
@DisplayName("ProductoController - capa Controller")
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoService productoService;

    @Test
    @DisplayName("GET /api/v1/productos responde 200 con el listado")
    void obtenerTodos_retorna200() throws Exception {
        // CORREGIDO: .precio(8990d) cambiado por BigDecimal.valueOf(8990.0)
        ProductoDto dto = ProductoDto.builder().id(1L).nombre("Pizza Margarita")
                .precio(BigDecimal.valueOf(8990.0)).stock(20).categoria("Pizzas").disponible(true).build();
        when(productoService.obtenerTodos()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Pizza Margarita"));
    }

    @Test
    @DisplayName("GET /api/v1/productos/{id} responde 200 con el producto")
    void buscarPorId_retorna200() throws Exception {
        // CORREGIDO: .precio(8990d) cambiado por BigDecimal.valueOf(8990.0)
        ProductoDto dto = ProductoDto.builder().id(1L).nombre("Pizza Margarita")
                .precio(BigDecimal.valueOf(8990.0)).stock(20).categoria("Pizzas").disponible(true).build();
        when(productoService.buscarPorId(eq(1L))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/productos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoria").value("Pizzas"));
    }

    @Test
    @DisplayName("POST /api/v1/productos con datos validos responde 200")
    void guardar_datosValidos_retorna200() throws Exception {
        // CORREGIDO: .precio(2200d) cambiado por BigDecimal.valueOf(2200.0)
        ProductoDto nuevo = ProductoDto.builder().nombre("Empanada de pino")
                .precio(BigDecimal.valueOf(2200.0)).stock(50).categoria("Entradas").disponible(true).build();

        // CORREGIDO: .precio(2200d) cambiado por BigDecimal.valueOf(2200.0)
        ProductoDto creado = ProductoDto.builder().id(2L).nombre("Empanada de pino")
                .precio(BigDecimal.valueOf(2200.0)).stock(50).categoria("Entradas").disponible(true).build();
        when(productoService.guardar(any(ProductoDto.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    @DisplayName("POST /api/v1/productos con precio negativo responde 400")
    void guardar_precioInvalido_retorna400() throws Exception {
        // CORREGIDO: .precio(-100d) cambiado por BigDecimal.valueOf(-100.0)
        ProductoDto invalido = ProductoDto.builder().nombre("Empanada de pino")
                .precio(BigDecimal.valueOf(-100.0)).stock(50).categoria("Entradas").disponible(true).build();

        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/v1/productos/{id} responde 204")
    void eliminar_retorna204() throws Exception {
        mockMvc.perform(delete("/api/v1/productos/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}