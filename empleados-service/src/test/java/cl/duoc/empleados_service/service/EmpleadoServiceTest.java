package cl.duoc.empleados_service.service;

import cl.duoc.empleados_service.dto.EmpleadoDTO;
import cl.duoc.empleados_service.exception.ResourceNotFoundException;
import cl.duoc.empleados_service.mapper.EmpleadoMapper;
import cl.duoc.empleados_service.model.Empleado;
import cl.duoc.empleados_service.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de la capa @Service (EmpleadoService).
 * Autor: Integrante 2.
 *
 * Se mockean EmpleadoRepository y EmpleadoMapper para probar solo la
 * logica de negocio, sin conexion real a base de datos.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("EmpleadoService - capa Service")
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository repository;

    @Mock
    private EmpleadoMapper mapper;

    @InjectMocks
    private EmpleadoService empleadoService;

    private Empleado empleado;
    private EmpleadoDTO empleadoDTO;

    @BeforeEach
    void setUp() {
        empleado = Empleado.builder()
                .id(1L).nombre("Pedro Pizarro").cargo("Mesero").salario(650000d)
                .correo("pedro@enzo.cl").turno("Tarde")
                .fechaIngreso(LocalDate.of(2023, 3, 1)).localId(10L)
                .build();

        empleadoDTO = EmpleadoDTO.builder()
                .id(1L).nombre("Pedro Pizarro").cargo("Mesero").salario(650000d)
                .correo("pedro@enzo.cl").turno("Tarde")
                .fechaIngreso(LocalDate.of(2023, 3, 1)).localId(10L)
                .build();
    }

    @Test
    @DisplayName("listar() retorna todos los empleados mapeados")
    void listar_retornaListado() {
        when(repository.findAll()).thenReturn(List.of(empleado));
        when(mapper.toDto(empleado)).thenReturn(empleadoDTO);

        List<EmpleadoDTO> resultado = empleadoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Mesero", resultado.get(0).getCargo());
    }

    @Test
    @DisplayName("buscarPorId() retorna el empleado cuando existe")
    void buscarPorId_empleadoExiste_retornaDTO() {
        when(repository.findById(1L)).thenReturn(Optional.of(empleado));
        when(mapper.toDto(empleado)).thenReturn(empleadoDTO);

        EmpleadoDTO resultado = empleadoService.buscarPorId(1L);

        assertEquals("Pedro Pizarro", resultado.getNombre());
    }

    @Test
    @DisplayName("buscarPorId() lanza excepcion cuando el empleado no existe")
    void buscarPorId_empleadoNoExiste_lanzaExcepcion() {
        when(repository.findById(77L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> empleadoService.buscarPorId(77L));
    }

    @Test
    @DisplayName("guardar() persiste y retorna el empleado creado")
    void guardar_creaEmpleadoCorrectamente() {
        when(mapper.toEntity(empleadoDTO)).thenReturn(empleado);
        when(repository.save(empleado)).thenReturn(empleado);
        when(mapper.toDto(empleado)).thenReturn(empleadoDTO);

        EmpleadoDTO resultado = empleadoService.guardar(empleadoDTO);

        assertEquals("pedro@enzo.cl", resultado.getCorreo());
        verify(repository, times(1)).save(empleado);
    }

    @Test
    @DisplayName("buscarPorLocal() filtra correctamente por local")
    void buscarPorLocal_retornaEmpleadosDelLocal() {
        when(repository.findByLocalId(10L)).thenReturn(List.of(empleado));
        when(mapper.toDto(empleado)).thenReturn(empleadoDTO);

        List<EmpleadoDTO> resultado = empleadoService.buscarPorLocal(10L);

        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByLocalId(10L);
    }

    @Test
    @DisplayName("eliminar() lanza excepcion cuando el empleado no existe")
    void eliminar_empleadoNoExiste_lanzaExcepcion() {
        when(repository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> empleadoService.eliminar(5L));
        verify(repository, never()).deleteById(any());
    }
}
