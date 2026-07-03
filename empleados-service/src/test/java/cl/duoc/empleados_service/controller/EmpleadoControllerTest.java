package cl.duoc.empleados_service.controller;

import cl.duoc.empleados_service.dto.EmpleadoDTO;
import cl.duoc.empleados_service.service.EmpleadoService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de la capa @RestController (EmpleadoController).
 * Autor: Integrante 2.
 */
@WebMvcTest(EmpleadoController.class)
@DisplayName("EmpleadoController - capa Controller")
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmpleadoService empleadoService;

    @Test
    @DisplayName("GET /api/v1/empleados responde 200 con el listado")
    void listar_retorna200() throws Exception {
        EmpleadoDTO dto = EmpleadoDTO.builder().id(1L).nombre("Pedro Pizarro").cargo("Mesero")
                .salario(650000d).correo("pedro@enzo.cl").turno("Tarde")
                .fechaIngreso(LocalDate.of(2023, 3, 1)).localId(10L).build();
        when(empleadoService.listar()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cargo").value("Mesero"));
    }

    @Test
    @DisplayName("GET /api/v1/empleados/{id} responde 200 con el empleado")
    void buscarPorId_retorna200() throws Exception {
        EmpleadoDTO dto = EmpleadoDTO.builder().id(1L).nombre("Pedro Pizarro").cargo("Mesero")
                .salario(650000d).correo("pedro@enzo.cl").turno("Tarde")
                .fechaIngreso(LocalDate.of(2023, 3, 1)).localId(10L).build();
        when(empleadoService.buscarPorId(eq(1L))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/empleados/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("pedro@enzo.cl"));
    }

    @Test
    @DisplayName("POST /api/v1/empleados con datos validos responde 200")
    void guardar_datosValidos_retorna200() throws Exception {
        EmpleadoDTO nuevo = EmpleadoDTO.builder().nombre("Carla Reyes").cargo("Cocinera")
                .salario(720000d).correo("carla@enzo.cl").turno("Mañana")
                .fechaIngreso(LocalDate.of(2024, 1, 15)).localId(10L).build();
        EmpleadoDTO creado = EmpleadoDTO.builder().id(2L).nombre("Carla Reyes").cargo("Cocinera")
                .salario(720000d).correo("carla@enzo.cl").turno("Mañana")
                .fechaIngreso(LocalDate.of(2024, 1, 15)).localId(10L).build();
        when(empleadoService.guardar(any(EmpleadoDTO.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    @DisplayName("POST /api/v1/empleados con salario negativo responde 400")
    void guardar_salarioInvalido_retorna400() throws Exception {
        EmpleadoDTO invalido = EmpleadoDTO.builder().nombre("Carla Reyes").cargo("Cocinera")
                .salario(-100d).correo("carla@enzo.cl").turno("Mañana")
                .fechaIngreso(LocalDate.of(2024, 1, 15)).localId(10L).build();

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/v1/empleados/{id} responde 204")
    void eliminar_retorna204() throws Exception {
        mockMvc.perform(delete("/api/v1/empleados/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
