package cl.duoc.locales_service.controller;

import cl.duoc.locales_service.dto.LocalDTO;
import cl.duoc.locales_service.service.LocalService;
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
 * Pruebas de la capa @RestController (LocalController).
 * Autor: Integrante 2.
 */
@WebMvcTest(LocalController.class)
@DisplayName("LocalController - capa Controller")
class LocalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LocalService localService;

    @Test
    @DisplayName("GET /api/v1/locales responde 200 con el listado")
    void listar_retorna200() throws Exception {
        LocalDTO dto = LocalDTO.builder().id(1L).nombre("Enzo Providencia")
                .direccion("Av. Providencia 123").comuna("Providencia")
                .horario("12:00-23:00").activo(true).build();
        when(localService.listar()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/locales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comuna").value("Providencia"));
    }

    @Test
    @DisplayName("GET /api/v1/locales/{id} responde 200 con el local")
    void buscarPorId_retorna200() throws Exception {
        LocalDTO dto = LocalDTO.builder().id(1L).nombre("Enzo Providencia")
                .direccion("Av. Providencia 123").comuna("Providencia")
                .horario("12:00-23:00").activo(true).build();
        when(localService.buscarPorId(eq(1L))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/locales/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Enzo Providencia"));
    }

    @Test
    @DisplayName("POST /api/v1/locales con datos validos responde 200")
    void guardar_datosValidos_retorna200() throws Exception {
        LocalDTO nuevo = LocalDTO.builder().nombre("Enzo Ñuñoa")
                .direccion("Irarrazaval 4500").comuna("Ñuñoa")
                .horario("12:00-23:00").activo(true).build();
        LocalDTO creado = LocalDTO.builder().id(2L).nombre("Enzo Ñuñoa")
                .direccion("Irarrazaval 4500").comuna("Ñuñoa")
                .horario("12:00-23:00").activo(true).build();
        when(localService.guardar(any(LocalDTO.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/locales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    @DisplayName("POST /api/v1/locales sin nombre responde 400")
    void guardar_sinNombre_retorna400() throws Exception {
        LocalDTO invalido = LocalDTO.builder().nombre("")
                .direccion("Irarrazaval 4500").comuna("Ñuñoa")
                .horario("12:00-23:00").activo(true).build();

        mockMvc.perform(post("/api/v1/locales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/v1/locales/{id} responde 204")
    void eliminar_retorna204() throws Exception {
        mockMvc.perform(delete("/api/v1/locales/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
