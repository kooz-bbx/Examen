package cl.duoc.clientes_service.Controller;

import cl.duoc.clientes_service.Dto.ClienteDTO;
import cl.duoc.clientes_service.Service.ClienteService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de la capa @RestController (ClienteController).
 * Autor: Integrante 1.
 *
 * @WebMvcTest levanta solo la capa web; ClienteService se reemplaza con
 * un mock (@MockitoBean) para no depender de base de datos.
 */
@WebMvcTest(ClienteController.class)
@DisplayName("ClienteController - capa Controller")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService service;

    @Test
    @DisplayName("GET /api/v1/clientes responde 200 con el listado")
    void listar_retorna200() throws Exception {
        ClienteDTO dto = ClienteDTO.builder().id(1L).nombre("Ana Soto").email("ana@correo.com").ciudad("Santiago").build();
        when(service.listar()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Ana Soto"));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/{id} responde 200 con el cliente")
    void buscar_retorna200() throws Exception {
        ClienteDTO dto = ClienteDTO.builder().id(1L).nombre("Ana Soto").email("ana@correo.com").ciudad("Santiago").build();
        when(service.buscar(eq(1L))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/clientes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ana@correo.com"));
    }

    @Test
    @DisplayName("POST /api/v1/clientes con datos validos responde 200")
    void guardar_datosValidos_retorna200() throws Exception {
        ClienteDTO nuevo = ClienteDTO.builder().nombre("Luis Diaz").email("luis@correo.com").ciudad("Valparaiso").build();
        ClienteDTO creado = ClienteDTO.builder().id(2L).nombre("Luis Diaz").email("luis@correo.com").ciudad("Valparaiso").build();
        when(service.guardar(any(ClienteDTO.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    @DisplayName("POST /api/v1/clientes con email invalido responde 400")
    void guardar_emailInvalido_retorna400() throws Exception {
        ClienteDTO invalido = ClienteDTO.builder().nombre("Luis Diaz").email("no-es-un-email").build();

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/v1/clientes/{id} responde 204")
    void eliminar_retorna204() throws Exception {
        mockMvc.perform(delete("/api/v1/clientes/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
