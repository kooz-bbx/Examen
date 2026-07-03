package cl.duoc.autenticacion_service.controller;

import cl.duoc.autenticacion_service.dto.UsuarioDTO;
import cl.duoc.autenticacion_service.service.UsuarioService;
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
 * Pruebas de la capa @RestController (UsuarioController).
 * Autor: Integrante 1.
 *
 * Idea simple: @WebMvcTest levanta SOLO la capa web (sin base de datos,
 * sin Eureka). El UsuarioService se reemplaza por un mock, asi probamos
 * que el controller devuelve el codigo HTTP y el JSON correctos.
 */
@WebMvcTest(UsuarioController.class)
@DisplayName("UsuarioController - capa Controller")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName("GET /api/v1/usuarios responde 200 con la lista de usuarios")
    void listar_retorna200ConListado() throws Exception {
        UsuarioDTO dto = UsuarioDTO.builder().id(1L).username("jperez").password("clave123").rol("ADMIN").build();
        when(usuarioService.listar()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("jperez"))
                .andExpect(jsonPath("$[0].rol").value("ADMIN"));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} responde 200 con el usuario solicitado")
    void buscarPorId_retorna200ConUsuario() throws Exception {
        UsuarioDTO dto = UsuarioDTO.builder().id(1L).username("jperez").password("clave123").rol("ADMIN").build();
        when(usuarioService.buscarPorId(eq(1L))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/usuarios/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("jperez"));
    }

    @Test
    @DisplayName("POST /api/v1/usuarios con datos validos responde 200 y crea el usuario")
    void guardar_datosValidos_retorna200() throws Exception {
        UsuarioDTO nuevo = UsuarioDTO.builder().username("mlopez").password("clave456").rol("MESERO").build();
        UsuarioDTO creado = UsuarioDTO.builder().id(2L).username("mlopez").password("clave456").rol("MESERO").build();
        when(usuarioService.guardar(any(UsuarioDTO.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.username").value("mlopez"));
    }

    @Test
    @DisplayName("POST /api/v1/usuarios sin username responde 400 (validacion)")
    void guardar_sinUsername_retorna400() throws Exception {
        UsuarioDTO invalido = UsuarioDTO.builder().username("").password("clave456").rol("MESERO").build();

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/v1/usuarios/{id} responde 204")
    void eliminar_retorna204() throws Exception {
        mockMvc.perform(delete("/api/v1/usuarios/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
